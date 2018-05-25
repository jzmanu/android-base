package com.manu.baselibrary.http.progress;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ProgressInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        ProgressListener listener = null;
        if (request.tag() != null && request.tag() instanceof ProgressListener) {
            listener = (ProgressListener) request.tag();
        }
        Response originalResponse = chain.proceed(request);
        if (listener != null) {
            return originalResponse.newBuilder().body(new ProgressResponseBody(originalResponse.body(), listener)).build();
        } else {
            return originalResponse;
        }
    }
}