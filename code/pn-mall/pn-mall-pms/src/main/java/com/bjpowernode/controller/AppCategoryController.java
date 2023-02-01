package com.bjpowernode.controller;

import com.bjpowernode.dto.Result;
import com.bjpowernode.entity.Category;
import com.bjpowernode.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pms/app/categorys")
public class AppCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/listByLevel")
    Result<List<Category>> listByProductIds(@RequestParam(value = "level") Integer level){
        return Result.success("请求成功",categoryService.listCategoryByLevel(level));
    }
}