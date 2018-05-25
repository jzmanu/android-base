package com.manu.android_base.app;

import android.app.Application;

import com.manu.android_base.Config;
import com.manu.baselibrary.http.HttpClient;
import com.manu.baselibrary.http.util.OtherUtils;
import com.manu.baselibrary.image.PicassoUtils;

import okhttp3.logging.HttpLoggingInterceptor;


/**
 * MyApplication.java
 * Created by jzman
 * Powered by 2018/5/24 0024 9:59
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        initConfig();
    }

    private void initConfig() {
        HttpClient httpClient = HttpClient.newBuilder(this)
                .setUserAgent(OtherUtils.getUserAgent(this) + "dah")
                .setRootUrl(Config.SERVICEURL)
                .setDebug(true)
                .setLevel(HttpLoggingInterceptor.Level.BODY)
                .build();

        PicassoUtils.initPicasso(this,httpClient);
    }

}
