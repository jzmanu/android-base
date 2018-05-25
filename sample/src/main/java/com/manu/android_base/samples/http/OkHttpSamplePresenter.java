package com.manu.android_base.samples.http;

import android.os.Environment;

import com.manu.baselibrary.http.HttpClient;
import com.manu.baselibrary.http.callback.OkHttpCallback;
import com.manu.baselibrary.http.util.LogUtil;

import java.io.File;
import java.util.Map;

/**
 * OkHttpSamplePresenter.java
 * Created by jzman
 * Powered by 2018/5/24 0024 15:27
 */

public class OkHttpSamplePresenter {

    private OnGetRequestListener onGetRequestListener;
    private OnPostRequestListener onPostRequestListener;
    private OnDownLoadListener onDownLoadListener;

    public OkHttpSamplePresenter() {
    }

    public void setOnGetRequestListener(OnGetRequestListener onGetRequestListener) {
        this.onGetRequestListener = onGetRequestListener;
    }

    public void setOnPostRequestListener(OnPostRequestListener onPostRequestListener) {
        this.onPostRequestListener = onPostRequestListener;
    }

    public void setOnDownLoadListener(OnDownLoadListener onDownLoadListener) {
        this.onDownLoadListener = onDownLoadListener;
    }

    //Get请求
    public void get(String url) {
        HttpClient.get(url, new OkHttpCallback<Object>() {
            @Override
            public void onFailure(String error) {
                if (onGetRequestListener != null) {
                    onGetRequestListener.onGetRequestFailure(error);
                }
            }

            @Override
            public void onSuccess(Object result) {
                if (onGetRequestListener != null) {
                    onGetRequestListener.onGetRequestSuccess(result);
                }
            }
        });
    }

    //Post请求
    public void post(String url, Map<String, String> params) {
        HttpClient.post(url, params, new OkHttpCallback<Object>() {
            @Override
            public void onFailure(String error) {
                if (onPostRequestListener != null) {
                    onPostRequestListener.onPostRequestFailure(error);
                }
            }

            @Override
            public void onSuccess(Object result) {
                if (onPostRequestListener != null) {
                    onPostRequestListener.onPostRequestSuccess(result);
                }
            }
        });
    }

    //下载文件
    public void downLoadFile(String url){
        HttpClient.get(url, new OkHttpCallback<File>() {

            @Override
            public void onFailure(String error) {
                if (onDownLoadListener!=null){
                    onDownLoadListener.onDownLoadFailure(error);
                }
            }

            @Override
            public void onSuccess(File result) {
                if (onDownLoadListener!=null){
                    onDownLoadListener.onDownLoadSuccess();
                }
            }

            @Override
            public void onProgressDownload(long bytesRead, long contentLength) {
                LogUtil.log("bytesRead",bytesRead+"");
                LogUtil.log("contentLength",contentLength+"");
                if (onDownLoadListener!=null){
                    onDownLoadListener.onDownLoadProgress(bytesRead, contentLength);
                }
            }

            @Override
            public String getDownloadDest() {
                File file = new File(Environment.getExternalStorageDirectory() + File.separator  + "sun.apk");
                return file.getPath();
            }
        });
    }

    public interface OnGetRequestListener {
        void onGetRequestSuccess(Object obj);
        void onGetRequestFailure(String error);
    }

    public interface OnPostRequestListener {
        void onPostRequestSuccess(Object obj);
        void onPostRequestFailure(String error);
    }

    public interface OnDownLoadListener {
        void onDownLoadSuccess();
        void onDownLoadFailure(String error);
        void onDownLoadProgress(long currentProgress, long contentLength);
    }

}
