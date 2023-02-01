package com.bjpowernode.service;

import cn.hutool.core.lang.tree.Tree;
import com.bjpowernode.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品分类 服务类
 * </p>
 *
 * @author jack
 * @since 2023-01-12
 */
public interface CategoryService extends IService<Category> {
    /**
     * 获取商品分类树
     * @return
     */
    List<Tree<Long>> tree();

    /**
     * 通过商品分类ID获取商品分类详情
     * @param categoryId
     * @return
     */
    Category getCategoryById(Long categoryId);

    /**
     * 商品分类新增
     * @param category
     * @return
     */
    Category saveCategory(Category category);

    /**
     * 更新商品分类
     * @param category
     * @return
     */
    Category updateCategoryById(Category category);
    /**
        * 删除商品分类
        * @param categoryId
        * @return
        */
    boolean removeCategoryById(Long categoryId);
    /**
     * 根据分类层级获取分类
     * @param level
     * @return
     */
    List<Category> listCategoryByLevel(Integer level);
}
