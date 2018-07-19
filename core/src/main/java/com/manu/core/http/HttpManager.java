package com.manu.core.http;

import android.content.Context;

import com.manu.core.http.bean.ResultBean;
import com.manu.core.http.intercept.ProgressResponseIntercept;
import com.manu.core.http.listener.ResponseListener;

import java.io.File;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by jzman
 * Powered by 2018/6/12 0012.
 */

public class HttpManager {

    private static Context mContext;
    public static String mBaseUrl;
    private static HttpManager mHttpManager;

    public static void init(Context context, String baseUrl) {
        mContext = context;
        mBaseUrl = baseUrl;
        RetrofitClient.getOkHttpClient(context, baseUrl);
    }

    public static HttpManager getInstance() {
        if (mHttpManager == null) {
            synchronized (HttpManager.class) {
                if (mHttpManager == null) {
                    mHttpManager = new HttpManager();
                }
            }
        }
        return mHttpManager;
    }

    public <T> Call get(String path, Map<String, Object> params, ResponseListener<T> responseListener) {
        return new RetrofitClient.Builder(mContext)
                .setBaseUrl(mBaseUrl)
                .build()
                .get(path, params, responseListener);
    }

    public <T> Call get(String url, ResponseListener<T> responseListener) {
        return new RetrofitClient.Builder(mContext)
                .setBaseUrl(mBaseUrl)
                .build()
                .get(url, responseListener);
    }

    public <T> Call post(String path, Map<String, Object> params, ResponseListener<T> responseListener) {
        return new RetrofitClient.Builder(mContext)
                .setBaseUrl(mBaseUrl)
                .build()
                .post(path, params, responseListener);
    }

    public <T> Call uploadFile(String path, Map<String, Object> params, ResponseListener<T> responseListener) {
        return new RetrofitClient.Builder(mContext)
                .setBaseUrl(mBaseUrl)
                .build()
                .uploadFile(path, params, responseListener);
    }

    public <T> Call upload(String path, Map<String, Object> params, List<File> fileList, ResponseListener<T> responseListener) {
        return new RetrofitClient.Builder(mContext)
                .setBaseUrl(mBaseUrl)
                .build()
                .upload(path, params, fileList, responseListener);
    }

    public <T> Call downLoadFile(String url, ResponseListener<T> responseListener) {
        return new RetrofitClient.Builder(mContext)
                .setBaseUrl(mBaseUrl)
                .setInterceptor(new ProgressResponseIntercept(responseListener))
                .build()
                .downLoadFile(url, responseListener);
    }
}
