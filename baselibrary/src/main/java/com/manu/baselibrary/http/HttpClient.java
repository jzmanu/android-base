package com.manu.baselibrary.http;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.manu.baselibrary.http.callback.BaseCallback;
import com.manu.baselibrary.http.cookie.CookiesManager;
import com.manu.baselibrary.http.cookie.PersistentCookieStore;
import com.manu.baselibrary.http.progress.ProgressInterceptor;
import com.manu.baselibrary.http.progress.ProgressRequestBody;
import com.manu.baselibrary.http.ssl.CertificatesManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.manu.baselibrary.http.util.ParamsUtil.attachHttpGetParams;

public class HttpClient {
    private static HttpClient mInstance;
    private static Builder builder;
    private OkHttpClient okHttpClient;
    private String userAgent;
    private String rootUrl;
    private String appPath;

    private static String csrfHeaderName;
    private static String csrfToken;

    private HttpClient(OkHttpClient okHttpClient, String userAgent, String rootUrl, String appPath) {
        this.okHttpClient = okHttpClient;
        this.userAgent = userAgent;
        this.rootUrl = rootUrl;
        this.appPath = appPath;
        mInstance = this;
    }

    public static void setCsrfHeader(String key, String value) {
        csrfHeaderName = key;
        csrfToken = value;
    }

    public static OkHttpClient getOkHttpClient() {
        return getInstance().okHttpClient;
    }

    private static synchronized HttpClient getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("please init the httpclient");
        }
        return mInstance;
    }

    public static String getUrl(String url) {
        HttpClient client = getInstance();
        if (TextUtils.isEmpty(url)) return url;
        //如果传入完整地址,则不做处理
        if (url.startsWith("http")) return url;
        //如果未配置根地址，则不做处理
        if (TextUtils.isEmpty(client.rootUrl)) return url;
        //如果传入URL以"/"根地址开始 ，则和根地址拼接
        if (url.startsWith("/")) {
            if (client.rootUrl.endsWith("/")) {
                return client.rootUrl.substring(0, client.rootUrl.length() - 1) + url;
            } else {
                return client.rootUrl + url;
            }
        } else if (TextUtils.isEmpty(client.appPath)) {// 如果没有配置App地址
            if (client.rootUrl.endsWith("/")) {
                return client.rootUrl.substring(0, client.rootUrl.length() - 1) + url;
            } else {
                return client.rootUrl + "/" + url;
            }
        } else {// 否则和相对地址拼接
            if (client.appPath.endsWith("/")) {
                return client.rootUrl + client.appPath + url;
            } else {
                return client.rootUrl + client.appPath + "/" + url;
            }
        }
    }

    private static RequestHandle enqueue(Request request, BaseCallback callback) {
        Request newRequest = request.newBuilder().tag(callback).build();
        Call call = getInstance().okHttpClient.newCall(newRequest);
        call.enqueue(callback);
        return new DefaultRequestHandle(call, callback);
    }

    //方便接受原始的Response,使用了OkHttp的Callback
    private static void enqueue(Request request, Callback callback) {
        Request newRequest = request.newBuilder().tag(callback).build();
        Call call = getInstance().okHttpClient.newCall(newRequest);
        call.enqueue(callback);
    }

    private static Request.Builder getRequestBuilder() {
        Request.Builder builder = new Request.Builder().addHeader("User-Agent", getInstance().userAgent);
        if (!TextUtils.isEmpty(csrfHeaderName)) {
            builder.addHeader(csrfHeaderName, csrfToken);
        }
        return builder;
    }

    public static void clearCookie(Context mContext) {
        PersistentCookieStore cookieStore = new PersistentCookieStore(mContext);
        cookieStore.removeAll();
    }

    /**
     * Get Request
     *
     * @param url
     * @param callback
     * @return
     */
    public static RequestHandle get(String url, BaseCallback callback) {
        Request request = getRequestBuilder().get().url(getUrl(url)).build();
        return enqueue(request, callback);
    }

    public static void get(String url, Callback callback) {
        Request request = getRequestBuilder().get()
                .url(getUrl(url))
                .addHeader("Accept-Encoding", "identity")
                .build();
        enqueue(request, callback);
    }

    public static RequestHandle get(String url, Map<String, ? extends Object> requestParams,
                                    BaseCallback requestCallBack) {
        String paramsUrl = attachHttpGetParams(url, requestParams);
        String strUrl = getUrl(paramsUrl);
        Request request = getRequestBuilder().get().url(strUrl).build();
        return enqueue(request, requestCallBack);
    }

    public static RequestHandle get(String url, BaseCallback requestCallBack, Pagination pagination) {
        String _url = getUrl(url);
        if (pagination != null) {
            _url = pagination.appendToUrl(_url);
        }
        Request request = getRequestBuilder().get().url(_url).build();
        return enqueue(request, requestCallBack);
    }

    public static RequestHandle get(String url, Map<String, ? extends Object> requestParams,
                                    BaseCallback requestCallBack, Pagination pagination) {
        String _url = attachHttpGetParams(url, requestParams);
        _url = getUrl(_url);
        if (pagination != null) {
            _url = pagination.appendToUrl(_url);
        }
        Request request = getRequestBuilder().get().url(_url).build();
        return enqueue(request, requestCallBack);
    }

    /**
     * Post Request
     *
     * @param url
     * @param requestParams
     * @param requestCallBack
     * @return
     */
    @NonNull
    public static RequestHandle post(String url, Map<String, ? extends Object> requestParams,
                                     BaseCallback requestCallBack) {
        if (hasMultipart(requestParams)) {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            for (String key : requestParams.keySet()) {
                Object value = requestParams.get(key) == null ? "" : requestParams.get(key);
                if (value instanceof UploadPart) {
                    UploadPart part = (UploadPart) value;
                    part.addToBuilder(key, builder);
                } else {
                    builder.addFormDataPart(key, String.valueOf(value));
                }
            }
            ProgressRequestBody requestBody = new ProgressRequestBody(builder.build(), requestCallBack);
            return enqueue(getRequestBuilder().url(getUrl(url)).post(requestBody).build(), requestCallBack);
        } else {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : requestParams.keySet()) {
                Object value = requestParams.get(key) == null ? "" : requestParams.get(key);
                builder.add(key, String.valueOf(value));
            }
            String url1 = getUrl(url);
            return enqueue(getRequestBuilder().url(url1).post(builder.build()).build(), requestCallBack);
        }
    }

    private static boolean hasMultipart(Map<String, ? extends Object> requestParams) {
        boolean has = false;
        for (Object obj : requestParams.values()) {
            if (obj instanceof UploadPart) {
                has = true;
                break;
            }
        }
        return has;
    }

    /**
     * Put Request
     *
     * @param url
     * @param requestParams
     * @param requestCallBack
     * @return
     */
    public static RequestHandle put(String url, Map<String, ? extends Object> requestParams, BaseCallback requestCallBack) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key) == null ? "" : requestParams.get(key);
            builder.add(key, String.valueOf(value));
        }
        Request request = getRequestBuilder().url(getUrl(url)).put(builder.build()).build();
        return enqueue(request, requestCallBack);
    }

    /**
     * Delete Request
     *
     * @param url
     * @param requestCallBack
     * @return
     */
    public static RequestHandle delete(String url, BaseCallback requestCallBack) {
        Request request = getRequestBuilder().delete().url(getUrl(url)).build();
        return enqueue(request, requestCallBack);
    }

    public static RequestHandle delete(String url, Map<String, ? extends Object> requestParams,
                                       BaseCallback requestCallBack) {
        String paramsUrl = attachHttpGetParams(url, requestParams);
        Request request = getRequestBuilder().delete().url(getUrl(paramsUrl)).build();
        return enqueue(request, requestCallBack);
    }

    static class DefaultRequestHandle implements RequestHandle {

        BaseCallback callback;
        Call call;

        public DefaultRequestHandle(Call call, BaseCallback callback) {
            this.call = call;
            this.callback = callback;
        }

        @Override
        public void cancel() {
            call.cancel();
            callback.sendCancelMessage();
        }
    }

    public static Builder newBuilder(Context context) {
        if (builder == null) {
            builder = new Builder(context);
        }
        return builder;
    }

    public static void clearCookies() {
        if (builder != null) {
            builder.clearCookies();
        }
    }

    public static class Builder {
        private Context mContext;
        private int cacheSize ; // 10 M
        private int connectTimeout ;
        private String userAgent;
        private String rootUrl;
        private String appPath;
        private CookieJar cookieJar;
        private String cacheDir;
        private boolean debug ;
        private HttpLoggingInterceptor.Level level;
        private List<Interceptor> interceptors;
        private List<String> certificatePaths ;

        public Builder(Context context) {
            this.mContext = context;
            debug = false;
            cacheSize = 10 * 1024 * 1024;
            connectTimeout = 30;
            level = HttpLoggingInterceptor.Level.BASIC;
            interceptors = new ArrayList<>();
            certificatePaths = new ArrayList<>();
        }

        public Builder addCertificatePaths(String path) {
            this.certificatePaths.add(path);
            return this;
        }

        public Builder setCacheDir(String cacheDir) {
            this.cacheDir = cacheDir;
            return this;
        }

        public Builder setLevel(HttpLoggingInterceptor.Level level) {
            this.level = level;
            return this;
        }

        public Builder setCacheSize(int cacheSize) {
            this.cacheSize = cacheSize;
            return this;
        }

        public Builder setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setAppPath(String appPath) {
            this.appPath = appPath;
            return this;
        }

        public Builder setDebug(boolean debug) {
            this.debug = debug;
            return this;
        }

        public Builder setRootUrl(String rootUrl) {
            this.rootUrl = rootUrl;
            return this;
        }

        public Builder setUserAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            this.interceptors.add(interceptor);
            return this;
        }

        public Builder setCookieJar(CookieJar cookieJar) {
            this.cookieJar = cookieJar;
            return this;
        }

        public Builder clearCookies() {
            if (cookieJar != null) {
                CookiesManager manager = (CookiesManager) cookieJar;
                manager.clearCookies();
            }
            return this;
        }

        public HttpClient build() {
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
            okHttpBuilder.readTimeout(connectTimeout, TimeUnit.SECONDS);
            Cache cache = null;
            if (TextUtils.isEmpty(cacheDir)) {
                cache = new Cache(mContext.getCacheDir(), cacheSize);
            } else {
                cache = new Cache(new File(cacheDir), cacheSize);
            }
            okHttpBuilder.cache(cache);

            if (cookieJar == null) {
                cookieJar = new CookiesManager(new PersistentCookieStore(mContext));
            }
            okHttpBuilder.cookieJar(cookieJar);

            for (Interceptor interceptor : interceptors) {
                okHttpBuilder.addInterceptor(interceptor);
            }

            if (debug) {
                //添加日志拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(level);
                okHttpBuilder.addInterceptor(loggingInterceptor);
            }
            //默认添加进度连拦截器
            okHttpBuilder.addInterceptor(new ProgressInterceptor());

            if (!certificatePaths.isEmpty()) {
                List<InputStream> certStreams = new ArrayList<>();
                for (String path : certificatePaths) {
                    InputStream inputStream = null;
                    try {
                        inputStream = mContext.getAssets().open(path, Context.MODE_PRIVATE);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    certStreams.add(inputStream);
                }
                CertificatesManager certificatesManager = new CertificatesManager(certStreams);
                certificatesManager.setCertificates(okHttpBuilder);
            }
            return new HttpClient(okHttpBuilder.build(), this.userAgent, this.rootUrl, this.appPath);
        }
    }
}
