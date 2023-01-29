package com.bjpowernode.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.bjpowernode.validation.UpdateGroup;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 商品
 * </p>
 *
 * @author jack
 * @since 2023-01-12
 */
@Getter
@Setter
@TableName(value = "pms_product", autoResultMap = true)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "商品ID不能为空",groups = UpdateGroup.class)
    private Long id;

    /**
     * 分类ID
     * @NotBlank 简单对象
     */
    @NotNull(message = "商品分类ID不能为空")
    private Long categoryId;

    /**
     * 商品标题
     * @NotBlank 字符串用@NotBlank
     */
    @NotBlank(message = "商品标题不能为空")
    private String title;

    /**
     * 商品简介
     */
    private String intro;

    /**
     * 商品主图
     */
    //@NotBlank(message = "商品主图片不能为空")
    private String img;

    /**
     * 商品详情图片
     * @NotEmpty 集合
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    //@NotEmpty(message = "商品详情图片不能为空")
    private List<String> imgList;

    /**
     * 商品销售价格
     */
    @NotNull(message = "商品销售价格不能为空")
    private BigDecimal price;

    /**
     * 商品原价
     */

    private BigDecimal originalPrice;
    /**
     * 商品详情
     */
    //@NotBlank(message = "商品详情不能为空")
    private String detail;

    /**
     * 商品状态：0-未上架，1-上架，2-下架，3-删除
     */
    private Integer status;

    /**
     * 库存
     */
    @NotNull(message = "商品库存不能为空")
    private Integer stock;

    /**
     * 销量
     */
    private Integer sales;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
