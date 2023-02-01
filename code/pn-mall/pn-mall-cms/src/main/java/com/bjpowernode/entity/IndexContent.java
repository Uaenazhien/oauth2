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
 * 首页内容
 * </p>
 *
 * @author jack
 * @since 2023-01-30
 */
@Getter
@Setter
@TableName("cms_index_content")
public class IndexContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "内容ID不能为空",groups = UpdateGroup.class)
    private Long id;

    /**
     * 内容名称
     */
    @NotEmpty(message = "内容名称不能为空")
    private String contentName;

    /**
     * 1-热销商品 2-新品上线 3-为你推荐
     */
    @NotNull(message = "内容类型不能为空")
    private Integer contentType;

    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能为空")
    private Long productId;

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
