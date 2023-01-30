package com.bjpowernode.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bjpowernode.entity.Category;
import com.bjpowernode.entity.Product;
import com.bjpowernode.exception.BizException;
import com.bjpowernode.mapper.CategoryMapper;
import com.bjpowernode.mapper.ProductMapper;
import com.bjpowernode.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjpowernode.util.MinioUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品分类 服务实现类
 * </p>
 *
 * @author jack
 * @since 2023-01-12
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private MinioUtil minioUtil;

    @Override
    public List<Tree<Long>> tree() {
        List<Category> list = this.list();
        List<TreeNode<Long>> nodeList = Lists.newArrayList();
        for (Category item : list) {
            TreeNode<Long> treeNode = new TreeNode<>(item.getId(), item.getParentId(),
                    item.getName(), item.getSort());
            Map<String, Object> extra = new HashMap<>();
            extra.put("icon", item.getIcon());
            extra.put("sort", item.getSort());
            extra.put("level", item.getLevel());
            treeNode.setExtra(extra);
            nodeList.add(treeNode);
        }
        return TreeUtil.build(nodeList, 0L);
    }

    /**
     * 通过商品分类ID获取商品分类详情
     *
     * @param categoryId
     * @return
     */
    @Override
    public Category getCategoryById(Long categoryId) {
        return super.getById(categoryId);
    }

    /**
     * 新增商品分类
     *
     * @param category
     * @return
     */
    @Override
    public Category saveCategory(Category category) {
        super.save(category);
        return category;
    }

    /**
     * 商品分类修改
     *
     * @param category
     * @return
     */
    @Override
    public Category updateCategoryById(Category category) {
        //1.检查用户是否修改了父分类和层级，入如果是，不让修改
    /*    Category oldCategory = super.getById(category.getId());
        if (!oldCategory.getParentId().equals(category.getParentId()) || !oldCategory.getLevel().equals(category.getLevel())) {
            throw new BizException(HttpStatus.BAD_REQUEST, "父分类和层级不允许修改");
        }
        super.updateById(category);*/
        //2.更新商品分类数据
        boolean result = super.updateById(category);
        if (!result) {
            throw new BizException(HttpStatus.BAD_REQUEST.value(), "商品分类ID不存在");
        }
        //TODO 返回的不一定是完整的商品分类数据
        return category;
    }

    /**
     * 根据商品分类id 删除分类
     * 检查商品分类是否有子分类，如果有，不允许删除
     * 判断该分类下是否已经有商品 有的话 不允许删除
     *
     * @param categoryId
     * @return
     */

    @Override
    public boolean removeCategoryById(Long categoryId) {
    /*    //1.检查商品分类是否有子分类，如果有，不允许删除
    select * from pms_category where parent_id = categoryId
        List<Category> list = this.list();
        List<Long> childIds = getChildIds(list, categoryId);
        if (childIds.size() > 0) {
            throw new BizException(HttpStatus.BAD_REQUEST, "该商品分类下有子分类，不允许删除");
        }*/
        LambdaQueryWrapper<Category> categoryWrapper = new LambdaQueryWrapper<>();
        categoryWrapper.eq(Category::getParentId, categoryId);  //parent_id = categoryId

        if (categoryMapper.exists(categoryWrapper)) {
            throw new BizException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "该分类下存在子分类不能删除");
        }
        //2.判断该分类下是否已经有商品 有的话 不允许删除
        LambdaQueryWrapper<Product> productWrapper = new LambdaQueryWrapper<>();
        productWrapper.eq(Product::getCategoryId, categoryId);
        if (productMapper.exists(productWrapper)) {
            throw new BizException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "该分类下已经存在商品，不能删除");
        }
        Category category = super.getById(categoryId);
        //3.删除商品分类信息
        boolean result = super.removeById(categoryId);

        //4.删除商品分类的图片
        String icon = category.getIcon();
        if (StrUtil.isNotBlank(icon)){
            String objectName = icon.substring(icon.lastIndexOf("/") +1);
            minioUtil.removeFile(objectName);
        }
        return result;
    }
}
