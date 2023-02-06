package com.bjpowernode.controller;

import com.bjpowernode.document.ProductDoc;
import com.bjpowernode.dto.PageResult;
import com.bjpowernode.dto.Result;
import com.bjpowernode.param.ProductSearchParam;
import com.bjpowernode.service.ProductDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search/app/products")
public class ProductSearchController{
    @Autowired
    private ProductDocService productDocService;

    /**
     * 商品搜索
     * @param param
     * @return
     */
    @GetMapping
    public Result<PageResult<ProductDoc>> search(ProductSearchParam param){
        return Result.success("请求成功",productDocService.search(param));
    }
}