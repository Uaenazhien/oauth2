package com.bjpowernode.dto;

import lombok.Data;

@Data
public class CategoryDTO {

    private Long id;
    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 分类层级 1.一级分类 2.二级分类  3.三级分类
     */
    private Integer level;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

}