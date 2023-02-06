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
 * app端控制器
 */
@RestController
@RequestMapping("/pms/app")
public class AppPmsController {
    @Autowired
    private ProductService productService;

    /**
     * 根据商品id查询商品信息
     *
     * @param productId
     * @return
     */
    @GetMapping("/products/{productId}")
    public Result<Product> getProductById(@PathVariable Long productId) {
        return Result.success("获取商品信息成功",productService.getProductById(productId));
    }
}