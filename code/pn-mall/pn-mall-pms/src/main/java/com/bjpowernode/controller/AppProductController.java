package com.bjpowernode.controller;

import com.bjpowernode.dto.Result;
import com.bjpowernode.entity.Category;
import com.bjpowernode.entity.Product;
import com.bjpowernode.service.CategoryService;
import com.bjpowernode.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pms/app/products")
public class AppProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listByIds")
    public Result<List<Product>> listProductByProductIds(@RequestParam("productIds") List<Long> productIds) {
        return Result.success("请求成功", productService.listProductByIds(productIds));
    }
}