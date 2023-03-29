package com.jxy.my_blog.exception;

public class CommException extends RuntimeException{
    /**
     * 错误代码
     * 0表示成功
     */
    private final int errCode;
    /**
     * 错误文本
     */
    private final String errMsg;

    public CommException(int errCode, String errMsg) {
        this.errMsg = errMsg;
        this.errCode = errCode;
    }

    public CommException(String errMsg) {
        this.errCode = 500;
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
