package com.manu.baselibrary.http.progress;

public interface ProgressListener {
    void onProgress(long bytesRead, long contentLength, boolean isDownload);
}
