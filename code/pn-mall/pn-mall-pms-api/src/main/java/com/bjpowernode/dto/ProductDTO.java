package com.bjpowernode.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductDTO{
    /**
     * 商品ID
     */
    private Long id;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品简介
     */
    private String intro;

    /**
     * 商品主图
     */
    private String img;

    /**
     * 商品销售价格
     */
    private BigDecimal price;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 销量
     */
    private Integer sales;
}