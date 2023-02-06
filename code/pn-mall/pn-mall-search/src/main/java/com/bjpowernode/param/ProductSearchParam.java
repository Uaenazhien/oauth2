package com.bjpowernode.param;

import com.bjpowernode.dto.PageParam;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductSearchParam {

    // 搜索关键字
    private String keyword;

    //一级分类ID
    private Long categoryIdLevel1;

    //二级分类ID
    private Long categoryIdLevel2;

    //三级分类ID
    private Long categoryIdLevel3;

    // 价格区间
    private BigDecimal lowPrice;

    // 价格区间
    private BigDecimal higthPrice;

    // 排序字段
    private String orderBy;

    // 排序方向
    private String direction;

    // 当前页码
    private Integer current;

    // 一页显示多少条
    private Integer size;

}