package com.manu.core.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;
import com.manu.core.http.api.CommonApi;
import com.manu.core.http.bean.ResultBean;
import com.manu.core.http.bean.UploadPart;
import com.manu.core.http.exception.ErrorCode;
import com.manu.core.http.exception.MException;
import com.manu.core.http.exception.MExceptionFactory;
import com.manu.core.http.listener.ResponseListener;
import com.manu.core.http.progress.ProgressRequestBody;
import com.manu.core.utils.LogUtil;
import com.manu.core.utils.Util;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by jzman
 * Powered by 2018/6/5 0005.
 */
public class RetrofitClient {

    private CommonApi mCommonApi;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public RetrofitClient() {
    }

    public static OkHttpClient getOkHttpClient(Context context, String baseUrl) {
        return new Builder(context)
                .setBaseUrl(baseUrl)
                .build().getOkHttpClient();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    <T> Call get(String path, Map<String, Object> params, ResponseListener<T> listener) {
        Call<ResponseBody> call;
        if (params == null || params.size() == 0) {
            call = mCommonApi.get(path);
        } else {
            call = mCommonApi.get(path, params);
        }
        performRequest(call, listener);
        return call;
    }

    <T> Call get(String url, ResponseListener<T> listener) {
        Call<ResponseBody> call = mCommonApi.getWithUrl(url);
        performRequest(call, listener);
        return call;
    }

    <T> Call post(String path, Map<String, Object> params, ResponseListener<T> listener) {
        Call<ResponseBody> call = mCommonApi.post(path, params);
        performRequest(call, listener);
        return call;
    }

    <T> Call uploadFile(String path, Map<String, Object> params, ResponseListener<T> listener) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        for (String key : params.keySet()) {
            Object value = params.get(key) == null ? "" : params.get(key);
            if (value instanceof UploadPart) {
                UploadPart part = (UploadPart) value;
                part.addToBuilder(key, builder);
            } else {
                builder.addFormDataPart(key, String.valueOf(value));
            }
        }

        ProgressRequestBody progressRequestBody = new ProgressRequestBody(builder.build(), listener);
        Call<ResponseBody> call = mCommonApi.uploadFile(path, progressRequestBody);
        performRequest(call, listener);
        return call;
    }

    <T> Call upload(String path, Map<String, Object> params, List<File> fileList, ResponseListener<T> listener) {
        Call<ResponseBody> call = null;
        if (fileList != null) {
            if (fileList.size() > 0) {
                MultipartBody.Part[] part = new MultipartBody.Part[fileList.size()];
                for (int i = 0; i < fileList.size(); i++) {
                    RequestBody requestBody = RequestBody.create(MultipartBody.FORM, fileList.get(i));
                    ProgressRequestBody progressRequestBody = new ProgressRequestBody(requestBody, listener);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("file", fileList.get(i).getName(), progressRequestBody);
                    part[i] = body;
                }
                call = mCommonApi.upload(path, params, part);
            }
        }else{
            call = mCommonApi.post(path,params);
        }
        performRequest(call,listener);
        return  call;
    }

    <T> Call downLoadFile(String url, ResponseListener<T> responseListener) {
        Call<ResponseBody> call = mCommonApi.downLoadFile(url);
        performRequest(call, responseListener, true);
        return call;
    }

    private <T> void performRequest(Call<ResponseBody> call, final ResponseListener<T> listener) {
        performRequest(call, listener, false);
    }

    private <T> void performRequest(Call<ResponseBody> call, final ResponseListener<T> listener, final boolean isDownload) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        if (isDownload) {
                            new Thread(new DownLoadThread(response, listener)).start();
                        } else {
                            Gson gson = new Gson();
                            String json = response.body().string();
                            if (listener.getType() == String.class){
                                callSuccess(json,listener);
                                return;
                            }
                            JsonObject resultBean = new JsonParser().parse(json).getAsJsonObject();
                            ResultBean result = gson.fromJson(resultBean, ResultBean.class);
                            JsonElement jsonElement = resultBean.get("results");
                            String data;
                            if (jsonElement.isJsonArray()) {
                                data = resultBean.getAsJsonArray("results").toString();
                            } else if (jsonElement.isJsonObject()) {
                                data = resultBean.getAsJsonObject("results").toString();
                            } else {
                                onFailure(call, MExceptionFactory.createMException(new MException(100, "please return JsonObject or JsonArray...")));
                                return;
                            }

                            T t = gson.fromJson(data, listener.getType());
                            if (result.isError()) {
                                onFailure(call, MExceptionFactory.createMException(new MException(result.getErrorCode(), result.getMessage())));
                            } else {
                                callSuccess(t, listener);
                            }
                        }
                    } catch (JsonParseException | IOException e) {
                        onFailure(call, MExceptionFactory.createMException(e));
                    }
                } else {
                    onFailure(call, MExceptionFactory.createHttpCodeMException(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callFailure(t, listener);
            }
        });
    }

    private Type getType(ResultBean listener) {
        try {
            ParameterizedType parameterizedType = (ParameterizedType) listener.getClass().getGenericSuperclass();
            Type type = parameterizedType.getActualTypeArguments()[0];
            return type;
        } catch (Exception e) {
            return null;
        }
    }

    private void callSuccess(final Object obj, final ResponseListener listener) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(obj);
            }
        });
    }

    private void callFailure(final Throwable t, final ResponseListener listener) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (t instanceof IOException) {
                    if (!t.getMessage().contains("Canceled") && !t.getMessage().contains("closed")) {
                        listener.onFailure(MExceptionFactory.createMException(t).getErrorMessage());
                    }
                } else {
                    listener.onFailure(MExceptionFactory.createMException(t).getErrorMessage());
                }
            }
        });
    }

    private void callComplete(final ResponseListener listener) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onDownLoadComplete();
            }
        });
    }

    class DownLoadThread implements Runnable {
        private Response<ResponseBody> response;
        private String downloadFilePath;
        private ResponseListener listener;
        private File file;

        public DownLoadThread(Response<ResponseBody> response, ResponseListener listener) {
            this.response = response;
            this.listener = listener;
            this.downloadFilePath = listener.getDownLoadPath();
            file = new File(downloadFilePath);
        }

        @Override
        public void run() {
            InputStream inputStream = null;
            FileOutputStream outputStream = null;
            try {
                inputStream = response.body().byteStream();
                outputStream = new FileOutputStream(file);
                ByteStreams.copy(inputStream, outputStream);
                callSuccess(new ResultBean<File>(), listener);
            } catch (IOException e) {
                callFailure(e, listener);
            } finally {
                okhttp3.internal.Util.closeQuietly(inputStream);
                okhttp3.internal.Util.closeQuietly(outputStream);
            }
            callComplete(listener);
        }
    }

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
        private Interceptor interceptor;
        private List<Interceptor> interceptors;

        public Builder(Context context) {
            this.context = context;
            this.userAgent = "userAgent";
            this.connectTimeout = 80;
            this.writeTimeout = 80;
            this.readTimeout = 80;
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

        public Builder setInterceptor(Interceptor interceptor) {
            this.interceptor = interceptor;
            return this;
        }

        public Builder setInterceptors(List<Interceptor> interceptors) {
            this.interceptors = interceptors;
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

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            //添加拦截器
            this.okHttpBuilder
                    .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                    .readTimeout(readTimeout, TimeUnit.SECONDS)
                    .writeTimeout(writeTimeout, TimeUnit.SECONDS);

            if (interceptor != null) this.okHttpBuilder.addInterceptor(interceptor);
            if (interceptors != null && interceptors.size() != 0) {
                for (Interceptor interceptor : interceptors) {
                    this.okHttpBuilder.addInterceptor(interceptor);
                }
            }

//            this.okHttpBuilder.addInterceptor(loggingInterceptor);
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(this.baseUrl)
                    .client(this.okHttpBuilder.build())
//                    .addConverterFactory(CustomGsonConverterFactory.create())
                    .build();

            this.commonApi = retrofit.create(CommonApi.class);
            manager.mCommonApi = this.commonApi;
            manager.mOkHttpClient = okHttpBuilder.build();
        }
    }
}
