package com.bjpowernode.dto;

import lombok.Data;

@Data
public class ProductQueryParam extends PageParam {
    //商品标题
    private String title;
    //商品分类id
    private Long categoryId;
    //商品状态
    private Integer status;
}
