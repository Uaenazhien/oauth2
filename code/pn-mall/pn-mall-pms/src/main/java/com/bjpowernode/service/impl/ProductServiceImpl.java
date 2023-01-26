package com.bjpowernode.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjpowernode.dto.PageResult;
import com.bjpowernode.dto.ProductQueryParam;
import com.bjpowernode.entity.Product;
import com.bjpowernode.mapper.ProductMapper;
import com.bjpowernode.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Override
    public Product getProductById(Long productId) {

        return super.getById(productId);
    }

    @Override
    public PageResult<Product> pageQuery(ProductQueryParam queryParam) {
        //1.构造分页条件
        Page<Product> page = new Page<>(queryParam.getCurrent(),queryParam.getSize());
        //2.构造查询条件 和结果集对象
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        //2.1商品标题(模糊查询)
        queryWrapper.like(StrUtil.isNotBlank(queryParam.getTitle()),Product::getTitle,queryParam.getTitle());
        //2.2商品分类
        queryWrapper.eq(ObjUtil.isNotNull(queryParam.getCategoryId()),Product::getCategoryId,queryParam.getCategoryId());
        //2.3是否上架
        queryWrapper.eq(ObjUtil.isNotNull(queryParam.getStatus()),Product::getStatus,queryParam.getStatus());

        super.page(page, queryWrapper);
        //返回分页结果对象
        return new PageResult<>(page.getRecords(),page.getTotal());

    }
}
