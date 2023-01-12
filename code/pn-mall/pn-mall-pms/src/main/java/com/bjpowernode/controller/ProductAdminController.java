package com.bjpowernode.controller;

import com.bjpowernode.dto.Result;
import com.bjpowernode.entity.Product;
import com.bjpowernode.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品后台管理接口
 */

@RestController
@RequestMapping("/pms/admin/products")
public class ProductAdminController {
    @Autowired
    private ProductService productService;

    /**
     *根据商品id查询商品信息
     * @param productId
     * @return
     */
    @GetMapping("/{id}")
    public Result<Product> getProductById(@PathVariable("id") Long productId) {

        Product product = productService.getById(productId);
        return Result.success("获取商品信息成功",product);
    }
}
