package com.manu.core.http;

import android.content.Context;

import com.google.gson.Gson;
import com.manu.core.http.api.CommonApi;
import com.manu.core.http.bean.ResultBean;
import com.manu.core.http.callback.MCallback;
import com.manu.core.http.exception.ErrorCode;
import com.manu.core.http.exception.MException;
import com.manu.core.http.gson.CustomGsonConverterFactory;
import com.manu.core.http.listener.ResponseListener;
import com.manu.core.utils.Util;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by jzman
 * Powered by 2018/6/5 0005.
 */

public class RetrofitClient {

    private String mUserAgent;
    private String mBaseUrl;
    private int mConnectTimeout;
    private int mReadTimeout;
    private int mWriteTimeout;
    private CommonApi mCommonApi;


    static class Builder{
        private Context context;

        private OkHttpClient.Builder okHttpBuilder;
        private Retrofit retrofit;

        private String userAgent;
        private String baseUrl;
        private int connectTimeout;
        private int readTimeout;
        private int writeTimeout;
        private CommonApi commonApi;

        public Builder(Context context) {
            this.context = context;
            this.userAgent = "userAgent";
            this.connectTimeout = 60;
            this.readTimeout = 60;
            this.writeTimeout = 60;
            this.okHttpBuilder = new OkHttpClient.Builder();
        }

        public Builder setUserAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder setWriteTimeout(int writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public RetrofitClient build(){
            RetrofitClient client = new RetrofitClient();
            setRetrofitClientConfig(client);
            return client;
        }

        private void setRetrofitClientConfig(RetrofitClient manager){
            if (!Util.isNotEmpty(baseUrl)){
                throw new MException(ErrorCode.M_ERROR_CODE_BASEURL_EMPTY, "baseUrl can't be empty!");
            }
            manager.mUserAgent = this.userAgent;
            manager.mConnectTimeout = this.connectTimeout;
            manager.mWriteTimeout = this.writeTimeout;
            manager.mReadTimeout = this.readTimeout;
            manager.mCommonApi = this.commonApi;

            this.okHttpBuilder
                    .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                    .readTimeout(readTimeout,TimeUnit.SECONDS)
                    .writeTimeout(writeTimeout,TimeUnit.SECONDS);

            this.retrofit = new Retrofit.Builder()
                    .baseUrl(this.baseUrl)
                    //添加自定义的Gson解析工厂
                    .addConverterFactory(CustomGsonConverterFactory.create())
                    .build();

            this.commonApi = retrofit.create(CommonApi.class);

        }
    }


    /**
     * Get请求
     * @param context
     * @param path
     * @param params
     * @param callback
     */
    public <T extends ResultBean> Call get(Context context, String path, Map<String, Object> params,
                                           Callback callback, ResponseListener<T> listener){
        Call<ResponseBody> call;
        if (params == null || params.size() == 0){
            call = mCommonApi.get(path);
        }else{
            call = mCommonApi.get(path,params);
        }
        call.enqueue(callback);
        performRequest(call,listener);
        return call;
    }

    private <T extends ResultBean> void performRequest(Call<ResponseBody> call, final ResponseListener<T> listener){
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.raw().code() == 200){
                    try {
                        String json = response.body().string();
                        Gson gson = new Gson();
                        T t = gson.fromJson(json,listener.getType());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    listener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
