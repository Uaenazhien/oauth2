package com.bjpowernode.service.impl;

import com.bjpowernode.entity.Category;
import com.bjpowernode.mapper.CategoryMapper;
import com.bjpowernode.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
