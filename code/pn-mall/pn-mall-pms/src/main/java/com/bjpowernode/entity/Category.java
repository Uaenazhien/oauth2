package com.bjpowernode.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.bjpowernode.validation.AddGroup;
import com.bjpowernode.validation.UpdateGroup;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 商品分类
 * </p>
 *
 * @author jack
 * @since 2023-01-12
 */
@Getter
@Setter
@TableName("pms_category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "商品分类id不能为空", groups = UpdateGroup.class)
    private Long id;

    /**
     * 父ID
     */
    @NotNull(message = "父分类id不能为空",groups = AddGroup.class)
    private Long parentId;

    /**
     * 分类层级 1.一级分类 2.二级分类  3.三级分类
     */
    @NotNull(message = "商品分层级不能为空",groups = AddGroup.class)
    private Integer level;

    /**
     * 分类名称
     */
    @NotBlank(message = "商品分类名称不能为空")
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
     */
    @NotNull(message = "商品分类层级不能为控")
    private Double sort;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
