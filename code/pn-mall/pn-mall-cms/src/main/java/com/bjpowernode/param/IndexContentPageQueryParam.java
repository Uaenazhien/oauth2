package com.bjpowernode.param;

import com.bjpowernode.dto.PageParam;
import lombok.Data;

@Data
public class IndexContentPageQueryParam extends PageParam{

    private String contentName; //内容名称 模糊查询

    private Integer contentType; //内容类型
}