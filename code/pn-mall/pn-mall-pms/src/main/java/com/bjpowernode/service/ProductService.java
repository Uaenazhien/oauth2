package com.bjpowernode.service;

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
}
