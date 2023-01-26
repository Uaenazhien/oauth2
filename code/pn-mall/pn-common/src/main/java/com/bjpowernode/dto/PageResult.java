package com.bjpowernode.dto;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 分页查询结果集对象
 */
@Data
public class PageResult<T> {

    public PageResult() {
        this.total = 0L;
        this.records = Lists.newArrayList();
    }

    public PageResult(List<T> records, Long total) {
        this.records = records;
        this.total = total;
    }


    // 当前页的记录
    private List<T> records;

    // 满足条件的总记录数
    private Long total;
}