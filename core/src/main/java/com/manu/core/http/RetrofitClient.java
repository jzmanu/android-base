package com.manu.core.http;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.manu.core.http.api.CommonApi;
import com.manu.core.http.bean.ResultBean;
import com.manu.core.http.callback.MCallback;
import com.manu.core.http.exception.ErrorCode;
import com.manu.core.http.exception.MException;
import com.manu.core.http.gson.CustomGsonConverterFactory;
import com.manu.core.http.listener.ResponseListener;
import com.manu.core.utils.Util;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.internal.connection.RouteException;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.manu.core.http.exception.ErrorCode.M_ERROR_CODE_NETWORK_ERROR;

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

    static class Builder {
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
            this.writeTimeout = 60;
            this.readTimeout = 60;
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

        public RetrofitClient build() {
            RetrofitClient client = new RetrofitClient();
            setRetrofitClientConfig(client);
            return client;
        }

        private void setRetrofitClientConfig(RetrofitClient manager) {
            if (!Util.isNotEmpty(baseUrl)) {
                throw new MException(ErrorCode.M_ERROR_CODE_BASEURL_EMPTY, "baseUrl can't be empty!");
            }
            manager.mUserAgent = this.userAgent;
            manager.mConnectTimeout = this.connectTimeout;
            manager.mWriteTimeout = this.writeTimeout;
            manager.mReadTimeout = this.readTimeout;

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            this.okHttpBuilder
                    .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                    .readTimeout(readTimeout, TimeUnit.SECONDS)
                    .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor);

            this.retrofit = new Retrofit.Builder()
                    .baseUrl(this.baseUrl)
                    //添加自定义的Gson解析工厂
                    .client(this.okHttpBuilder.build())
                    .addConverterFactory(CustomGsonConverterFactory.create())
                    .build();

            this.commonApi = retrofit.create(CommonApi.class);
            manager.mCommonApi = this.commonApi;
        }
    }

    /**
     * Get请求
     *
     * @param path
     * @param params
     * @param listener
     * @param <T>
     * @return
     */
    public <T extends ResultBean> Call get(String path, Map<String, Object> params, ResponseListener<T> listener) {
        Call<ResponseBody> call;
        if (params == null || params.size() == 0) {
            call = mCommonApi.get(path);
        } else {
            call = mCommonApi.get(path, params);
        }
        performRequest(call, listener);
        return call;
    }

    private <T extends ResultBean> void performRequest(Call<ResponseBody> call, final ResponseListener<T> listener) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.raw().code() == 200) {
                    try {
                        String json = response.body().string();
                        Gson gson = new Gson();
                        T t = gson.fromJson(json, listener.getType());
                        if (t.isError()) {
                            throw new MException(t.getErrorCode(), t.getMessage());
                        } else {
                            listener.onSuccess(t);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    onFailure(call, new MException(M_ERROR_CODE_NETWORK_ERROR, "网络连接出错"));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String message = "请求失败";
                if (t instanceof ConnectException) {
                    message = "网络连接失败";
                } else if (t instanceof SocketTimeoutException) {
                    message = "网络连接超时";
                } else if (t instanceof SocketException) {
                    message = "网络连接错误";
                } else if (t instanceof IOException) {
                    message = "数据读写错误";
                } else if (t instanceof RouteException) {
                    message = "连接错误";
                } else if (t instanceof JSONException) {
                    message = "数据解析错误";
                } else if (t instanceof JsonSyntaxException) {
                    message = "JSON数据解析错误";
                }
                listener.onFailure(message);
            }
        });
    }

}
