package com.bjpowernode.dto;

import lombok.Data;

@Data
public class Result<T> {
    // 相应消息
    private String msg;
    // 响应状态码 200 表示成功，其它代码是错误
    private Integer code;
    // 响应结果
    private T data;


    public static Result success() {
        Result result = new Result();
        result.code = 200;
        result.msg = "请求成功";
        return result;
    }

    public static Result success(String msg) {
        Result result = new Result();
        result.code = 200;
        result.msg = msg;
        return result;
    }

    public static <T> Result<T> success(String msg, T data) {
        Result<T> result = new Result<>();
        result.code = 200;
        result.msg = msg;
        result.data = data;
        return result;
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.code = code;
        result.msg = msg;
        return result;
    }
}
