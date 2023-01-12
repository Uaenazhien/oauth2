package com.bjpowernode.exception;
// new BizException(code,msg);
public class BizException extends RuntimeException {
    private int code;

    public int getCode() {
        return code;
    }

    public BizException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
