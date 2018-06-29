package com.manu.core.http.listener;

/**
 * 响应体进度回调接口，用于文件下载进度回调
 */
public interface ProgressResponseListener {
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}