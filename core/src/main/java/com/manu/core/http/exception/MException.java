package com.manu.core.http.exception;

/**
 * Created by jzman
 * Powered by 2018/6/8 0008.
 */

public class MException extends RuntimeException {

    private int errorCode = -1;
    private String errorMessage;


    public MException(Throwable e) {
        super(e);
    }

    public MException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "MException{" +
                "errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
