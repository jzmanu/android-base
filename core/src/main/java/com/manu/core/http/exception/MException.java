package com.manu.core.http.exception;

import com.manu.core.http.RetrofitClient;
import com.manu.core.http.callback.MCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jzman
 * Powered by 2018/6/8 0008.
 */

public class MException extends RuntimeException {


    private int errorCode = -1;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public MException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return super.toString()+",MException{" +
                "errorCode=" + errorCode +
                '}';
    }


}
