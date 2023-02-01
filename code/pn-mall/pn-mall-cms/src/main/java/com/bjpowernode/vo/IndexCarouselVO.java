package com.bjpowernode.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bjpowernode.validation.UpdateGroup;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 首页商品轮播图
 * </p>
 *
 * @author jack
 * @since 2023-01-30
 */
@Data
public class IndexCarouselVO implements Serializable {

    private Long id;

    /**
     * 轮播图标题
     */

    private String title;

    /**
     * 图片地址
     */

    private String img;

    /**
     * 点击跳转的链接
     */

    private String redirectUrl;

    /**
     * 排序
     */

    private Integer sort;

}
