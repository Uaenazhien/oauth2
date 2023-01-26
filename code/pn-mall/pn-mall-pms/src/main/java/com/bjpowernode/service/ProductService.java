package com.bjpowernode.service;

import com.bjpowernode.dto.PageResult;
import com.bjpowernode.dto.ProductQueryParam;
import com.bjpowernode.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author jack
 * @since 2023-01-12
 */
public interface ProductService extends IService<Product> {

    /**
     *根据商品id获取商品详情
     * @param productId
     * @return
     */
    Product getProductById(Long productId);
    /**
     * 商品分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    PageResult<Product> pageQuery(ProductQueryParam queryParam);
}
