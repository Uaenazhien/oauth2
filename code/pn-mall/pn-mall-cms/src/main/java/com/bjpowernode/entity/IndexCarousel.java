package com.bjpowernode.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.bjpowernode.validation.UpdateGroup;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 首页商品轮播图
 * </p>
 *
 * @author jack
 * @since 2023-01-30
 */
@Getter
@Setter
@TableName("cms_index_carousel")
public class IndexCarousel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "轮播图的ID不能为空",groups = UpdateGroup.class)
    private Long id;

    /**
     * 轮播图标题
     */
    @NotEmpty(message = "轮播图标题不能为空")
    private String title;

    /**
     * 图片地址
     */
    @NotEmpty(message = "轮播图的地址不能为空")
    private String img;

    /**
     * 点击跳转的链接
     */

    private String redirectUrl;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
