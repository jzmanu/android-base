package com.manu.core.http.progress;

public  class ProgressListener {

    public void onProgress(long bytesRead, long contentLength, boolean done){}

    public String getDownLoadPath(){
        return "";
    }

    public void onDownLoadComplete(){

    }
}
