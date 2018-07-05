package com.manu.core.http.intercept;

import com.manu.core.http.progress.ProgressListener;
import com.manu.core.http.progress.ProgressResponseBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Powered by jzman.
 * Created on 2018/6/27 0027.
 */
public class ProgressResponseIntercept implements Interceptor{

    private ProgressListener progressListener;

    public ProgressResponseIntercept(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originResponse = chain.proceed(chain.request());
        Response newResponse = originResponse.newBuilder()
                .body(new ProgressResponseBody(originResponse.body(), progressListener))
                .build();
        return newResponse;
    }
}
