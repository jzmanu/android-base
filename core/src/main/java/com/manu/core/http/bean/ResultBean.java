package com.manu.core.http.bean;

/**
 * Created by jzman
 * Powered by 2018/6/9 0009.
 */

public class ResultBean<T> {
    private boolean error;
    private String message = "服务器给的提示消息...";
    private int errorCode = -1;
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", results=" + results +
                '}';
    }
}
