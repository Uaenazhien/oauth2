package com.bjpowernode.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjpowernode.config.RabbitMQConfig;
import com.bjpowernode.constant.RabbitMQConstant;
import com.bjpowernode.constant.RedisConstant;
import com.bjpowernode.dto.PageResult;
import com.bjpowernode.dto.ProductQueryParam;
import com.bjpowernode.entity.Category;
import com.bjpowernode.entity.Product;
import com.bjpowernode.enums.ProductStatus;
import com.bjpowernode.exception.BizException;
import com.bjpowernode.mapper.CategoryMapper;
import com.bjpowernode.mapper.ProductMapper;
import com.bjpowernode.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjpowernode.util.MinioUtil;
import com.google.common.collect.Lists;
import org.apache.http.conn.routing.HttpRoute;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author jack
 * @since 2023-01-12
 */
@Service
// 缓存配置
@CacheConfig(cacheNames = RedisConstant.KEY_PRODUCT)
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private MinioUtil minioUtil;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    // 查询缓存
    @Cacheable(sync = true)  //produce:1
    public  Product getProductById(Long productId) {
        Product product = super.getById(productId);
  /*      try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.println("访问了数据库");
        return product;
    }


    @Override
    public PageResult<Product> pageQuery(ProductQueryParam queryParam) {
        //1.构造分页条件
        Page<Product> page = new Page<>(queryParam.getCurrent(), queryParam.getSize());
        //2.构造查询条件 和结果集对象
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        //2.1商品标题(模糊查询)
        queryWrapper.like(StrUtil.isNotBlank(queryParam.getTitle()), Product::getTitle, queryParam.getTitle());
        //2.2商品分类
        queryWrapper.eq(ObjUtil.isNotNull(queryParam.getCategoryId()), Product::getCategoryId, queryParam.getCategoryId());
        //2.3是否上架
        queryWrapper.eq(ObjUtil.isNotNull(queryParam.getStatus()), Product::getStatus, queryParam.getStatus());

        super.page(page, queryWrapper);
        //返回分页结果对象
        return new PageResult<>(page.getRecords(), page.getTotal());

    }

    @Override
    // 更新缓存
    @CachePut(key = "#product.id")
    public Product saveProduct(Product product) {
        //1.判断商品分类id不能为空
       /* LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getId,product.getCategoryId());*/

        Category category = categoryMapper.selectById(product.getCategoryId());
        if (ObjUtil.isNotNull(category)) {
            //该分类不是三级分类
            if (category.getLevel() != 3) {
                throw new BizException(HttpStatus.BAD_REQUEST.value(), "商品所属分类必须是三级分类");
            }
        } else {
            throw new BizException(HttpStatus.BAD_REQUEST.value(), "该商品分类不存在或已经被删除");
        }
        //2.保存商品数据
        super.save(product);
        return product;
    }

    @Override
    // 更新缓存
    @CachePut(key = "#product.id")
    public Product updateProduct(Product product) {
        boolean result = super.updateById(product);
        if (!result) {
            throw new BizException(HttpStatus.BAD_REQUEST.value(), "商品ID不存在");
        }
        // TODO 后续缓存用到该结果
        product = super.getById(product.getId());
        // // 上架状态的商品发生更新，同步信息到搜索引擎库
        if (product.getStatus().equals(ProductStatus.ON.getStatus())){
            rabbitTemplate.convertAndSend(
                    RabbitMQConstant.EXCHANGE_PRODUCT,RabbitMQConstant.ROUTING_KEY_PRODUCT_SAVE,product);
        }
        return product;
    }

    @Override
    // 删除缓存
    @CacheEvict
    public boolean removeProductById(Long productId) {
        //1.检查商品状态 ：上架中的商品不能删除
        Product product = super.getById(productId);
        if (product == null) {
            throw new BizException(HttpStatus.BAD_REQUEST.value(), "商品不存在");
        }
        if (product.getStatus().equals(ProductStatus.ON.getStatus())) {
            throw new BizException(HttpStatus.BAD_REQUEST.value(), "已经上架商品不允许删除");
        }
        //2.删除商品相关的信息
        boolean result = super.removeById(productId);
        //3.删除商品相关的图片
        String img = product.getImg();
        List<String> imgs = product.getImgList();
        imgs.add(img);


        //循环删除 因上一个工具批量删除没做
        imgs.forEach(item -> {
            if (StrUtil.isNotBlank(item)) {
                minioUtil.removeFile(item.substring(item.lastIndexOf("/") + 1));
            }
        });
        //批量删除文件

        return result;
    }

    @Override
    // 更新缓存
    @CachePut(key = "#productId")
    public Product updateProductStatus(Long productId, Integer status) {
        Product product = super.getById(productId);
        if (product == null) {
            throw new BizException(HttpStatus.BAD_REQUEST.value(), "商品不存在");
        }
        // 检查商品状态是否正确
        if (ProductStatus.ON.getStatus() != status
                && ProductStatus.OFF.getStatus() != status) {
            throw new BizException(HttpStatus.BAD_REQUEST.value(), "商品状态参数错误：0-未上架，1-上架");
        }
        // 检查商品数据是否完整 ======
        if (StrUtil.isBlank(product.getImg())) {
            throw new BizException(HttpStatus.BAD_REQUEST.value(), "请上传商品主图再上架");
        }

        if (ObjUtil.isEmpty(product.getImgList())) {
            throw new BizException(HttpStatus.BAD_REQUEST.value(), "请先完善商品详情图片再上架");
        }

        if (StrUtil.isBlank(product.getDetail())) {
            throw new BizException(HttpStatus.BAD_REQUEST.value(), "请完善商品详情再上架");
        }
        // 检查商品数据是否完整  ======
        product.setStatus(status);
        // 更新商品状态
        super.updateById(product);
        return product;
    }

    @Override
    public List<Product> listProductByIds(List<Long> productIds) {
        return productMapper.listProductByIds(productIds);
    }
}
