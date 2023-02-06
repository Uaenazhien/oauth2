package com.bjpowernode.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

@Document(indexName = "product")
@Data
public class ProductDoc {
    @Id
    private Long id;//商品ID

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String keyword;//关键字

    @Field(type = FieldType.Text, index = false)
    private String img;//图片地址

    @Field(type = FieldType.Text, analyzer = "ik_smart", copyTo = "keyword")
    private String title;//商品标题

    @Field(type = FieldType.Text, analyzer = "ik_smart", copyTo = "keyword")
    private String intro;//商品简介

    @Field(type = FieldType.Double)
    private BigDecimal price;//商品价格

    @Field(type = FieldType.Integer)
    private Integer sales;//商品销量


    @Field(type = FieldType.Long)
    private Long categoryIdLevel1;//一级分类ID

    @Field(type = FieldType.Long)
    private Long categoryIdLevel2;//二级分类ID

    @Field(type = FieldType.Long)
    private Long categoryIdLevel3;//三级分类ID


    @Field(type = FieldType.Keyword, copyTo = "keyword")
    private String categoryNameLevel1;//一级分类名称

    @Field(type = FieldType.Keyword, copyTo = "keyword")
    private String categoryNameLevel2;//二级分类名称

    @Field(type = FieldType.Keyword, copyTo = "keyword")
    private String categoryNameLevel3;//三级分类名称
}
