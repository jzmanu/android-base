package com.manu.baselibrary.http.exception;

/**
 * 无网络异常类
 * @author jzman
 * create at 2018/5/22 0022 14:02
 */

public class NoNetworkException extends RuntimeException {

    private String errorCode = "-1";
    public NoNetworkException(String code, String msg){
        super(msg);
        this.errorCode = code;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return super.toString()+",NoNetworkException{" +
                "errorCode='" + errorCode + '\'' +
                '}';
    }
}
