package com.bjpowernode.controller.admin;

import cn.hutool.core.lang.tree.Tree;
import com.bjpowernode.dto.Result;
import com.bjpowernode.entity.Category;
import com.bjpowernode.service.CategoryService;
import com.bjpowernode.validation.AddGroup;
import com.bjpowernode.validation.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pms/admin/categorys")
public class CategoryAdminController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取商品分类树
     *
     * @return
     */
    @GetMapping("/tree")
    public Result<List<Tree<Long>>> tree() {
        return Result.success("请求成功", categoryService.tree());
    }

    /**
     * 获取商品分类详情
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/{categoryId}")
    public Result<Category> getCategoryById(@PathVariable("categoryId") Long categoryId) {
        return Result.success("请求成功", categoryService.getCategoryById(categoryId));

    }

    /**
     * 新增商品分类
     *
     * @param category
     * @return
     */
    @PostMapping
    public Result<Long> save(@Validated(AddGroup.class) @RequestBody Category category) {
        categoryService.saveCategory(category);
        return Result.success("商品分类新增成功", category.getId());
    }

    /**
     * 修改商品分类
     *
     * @param category
     * @return
     */
    @PutMapping
    public Result update(@Validated(UpdateGroup.class) @RequestBody Category category) {
        categoryService.updateCategoryById(category);
        return Result.success("商品分类成功");
    }

    /**
     * 根据商品分类得id删除商品分类
     * @param categroyId
     * @return
     */
    @DeleteMapping("/{categroyId}")
    public Result deleteByCategroyId(@PathVariable Long categroyId) {
        categoryService.removeCategoryById(categroyId);
        return Result.success("商品分类删除成功");
    }
}