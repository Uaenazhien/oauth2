package com.bjpowernode.exception;

import com.bjpowernode.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 捕获Controller层方法调用过程中出现的异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 参数校验异常
     */
    @ExceptionHandler(value = BindException.class)
    public Result validationExceptionHandler(BindException e) {
        StringBuffer sb = new StringBuffer();
        e.getFieldErrors().forEach(error -> 
                sb.append(error.getField() + "：" + error.getDefaultMessage() + " "));
        log.error("错误代码：{}：{}", HttpStatus.BAD_REQUEST.value(), sb);
        return Result.error(HttpStatus.BAD_REQUEST.value(), sb.toString());
    }

    /**
     * 自定义业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    public Result bizExceptionHandler(BizException e) {
        log.error("错误代码：{}：{}", e.getCode(), e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 其它业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Throwable.class)
    public Result exceptionHandler(Throwable e) {
        log.error("错误代码：{}：{}", HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return Result.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}