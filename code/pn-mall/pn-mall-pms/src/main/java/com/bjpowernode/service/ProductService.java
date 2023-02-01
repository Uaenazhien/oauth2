package com.bjpowernode.service;

import com.bjpowernode.dto.PageResult;
import com.bjpowernode.dto.ProductQueryParam;
import com.bjpowernode.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    /**
     * 新增商品
     *
     * @param product
     * @return
     */
    Product saveProduct(Product product);
    /**
     * 修改商品
     *
     * @param product
     * @return
     */
    Product updateProduct(Product product);
    /**
     * 删除商品
     *
     * @param productId 商品ID
     * @return
     */
    boolean removeProductById(Long productId);
    /**
     * 更新商品状态
     *
     * @param productId 商品ID
     * @param status 商品状态
     * @return
     */
    Product updateProductStatus(Long productId, Integer status);

    /**
     * 根据商品id集合获取商品数据，并按productIds集合的顺序排序
     * @param productIds
     * @return
     */
    List<Product> listProductByIds(List<Long> productIds);
}
