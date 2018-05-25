package com.manu.baselibrary.http.interceptor;

import android.support.annotation.NonNull;

import com.manu.baselibrary.http.exception.ExceptionConfig;
import com.manu.baselibrary.http.exception.NoNetworkException;
import com.manu.baselibrary.http.util.NetworkUtil;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 无网络拦截器
 * Created by jzman
 * Powered by 2018/5/22 0022 17:22
 */

public class NoNetworkPrepareInterceptor implements Interceptor{
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if(NetworkUtil.isNetworkConnected()){
            return chain.proceed(chain.request());
        }else{
            throw new NoNetworkException(ExceptionConfig.ERROR_CODE_NO_NET,"请连接网络后重试...");
        }
    }
}
