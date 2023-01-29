package com.bjpowernode.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import org.apache.http.conn.routing.HttpRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author jack
 * @since 2023-01-12
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Product getProductById(Long productId) {

        return super.getById(productId);
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
    public Product updateProduct(Product product) {
        boolean result = super.updateById(product);
        if (!result) {
            throw new BizException(HttpStatus.BAD_REQUEST.value(), "商品ID不存在");
        }
        // TODO 后续缓存用到该结果
        return super.getById(product.getId());
    }

    @Override
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
        //3.
        return super.removeById(productId);
    }

    @Override
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
}
