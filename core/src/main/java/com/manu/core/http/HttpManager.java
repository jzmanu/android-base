package com.manu.core.http;

import android.content.Context;

import com.manu.core.http.bean.ResultBean;
import com.manu.core.http.listener.ResponseListener;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by jzman
 * Powered by 2018/6/12 0012.
 */

public class HttpManager {

    private static Context mContext;
    private static String mBaseUrl;
    private static HttpManager mHttpManager;

    public static void init(Context context,String baseUrl) {
        mContext = context;
        mBaseUrl = baseUrl;
    }

    public static HttpManager getInstance(){
        if (mHttpManager == null){
            synchronized (HttpManager.class){
                if (mHttpManager == null){
                    mHttpManager = new HttpManager();
                }
            }
        }
        return mHttpManager;
    }

    public  <T extends ResultBean> Call get(String path, Map<String,Object> params, ResponseListener<T> responseListener) {
        return new RetrofitClient.Builder(mContext)
                .setBaseUrl(mBaseUrl)
                .build()
                .get(path,params,responseListener);
    }
}
