package com.manu.baselibrary.http.callback;

import android.os.Message;

import com.google.common.io.ByteStreams;
import com.google.gson.reflect.TypeToken;
import com.manu.baselibrary.http.util.ExceptionUtil;
import com.manu.baselibrary.http.util.GenericsUtils;
import com.manu.baselibrary.http.util.GsonUtil;
import com.manu.baselibrary.http.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.internal.Util;

/**
 * OkHttpCallback.java
 *
 * @author jzman
 *         create at 2018/5/21 0021 17:30
 */
public abstract class OkHttpCallback<T> extends BaseCallback {

    public abstract void onFailure(String error);

    public abstract void onSuccess(T result);

    @Override
    public void handSuccess(Message message) {
        Object[] objs = (Object[]) message.obj;
        onSuccess((T) objs[1]);
    }

    @Override
    public void handFailure(Message message) {
        Object[] objs = (Object[]) message.obj;
        onFailure((String) objs[1]);
    }

    @Override
    public void handProgress(Message message) {
        Object[] objs = (Object[]) message.obj;
        if ((Boolean) objs[2]) {
            onProgressDownload((Long) objs[0], (Long) objs[1]);
        } else {
            onProgressUpload((Long) objs[0], (Long) objs[1]);
        }
    }

    @Override
    public void handFinish(Message message) {
        onFinish();
    }

    @Override
    public void handCancel(Message message) {
        onCancel();
    }

    @Override
    public void handUnauthorized(Message message) {

    }

    @Override
    public void onProgress(long bytesRead, long contentLength, boolean isDownload) {
        sendMessage(PROGRESS_MESSAGE, new Object[]{bytesRead, contentLength, isDownload});
    }

    @Override
    public void onFailure(Call call, IOException e) {
        sendMessage(FAILURE_MESSAGE, new Object[]{call.request(), ExceptionUtil.getExceptionComment(e)});
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (!response.isSuccessful()) {
            if (response.code() == 401) {
                sendMessage(UNAUTHORIZED_MESSAGE,
                        new Object[]{response.request(), ExceptionUtil.getHttpCodeComment(response.code())});
            } else {
                sendMessage(FAILURE_MESSAGE,
                        new Object[]{response.request(), ExceptionUtil.getHttpCodeComment(response.code())});
            }
        } else {
            if (isDownload(this)) {
                new Thread(new DownLoadThread(response)).start();
            } else {
                try {
                    String body = response.body().string();
                    LogUtil.log("onResponse", body);
                    sendMessage(SUCCESS_MESSAGE, new Object[]{response, convert(body, this)});
                } catch (Exception e) {
                    sendMessage(FAILURE_MESSAGE, new Object[]{response.request(), ExceptionUtil.getExceptionComment(e)});
                }
                sendMessage(FINISH_MESSAGE, null);
            }
        }
    }

    public String getDownloadDest() {
        return "";
    }

    private boolean isDownload(OkHttpCallback<T> callBack) {
        Type type = GenericsUtils.getSuperClassGenricType(callBack.getClass());
        return type.equals(new TypeToken<File>() {
        }.getType());
    }

    private T convert(String obj, OkHttpCallback<T> callBack) {
        Type type = GenericsUtils.getSuperClassGenricType(callBack.getClass());
        if (type.equals(new com.google.common.reflect.TypeToken<String>() {}.getType())) {
            return (T) obj;
        }
        return GsonUtil.getGson().fromJson(obj, type);
    }

    class DownLoadThread implements Runnable {
        private Response response;
        private String downloadFilePath;
        private File file;

        public DownLoadThread(Response response) {
            this.response = response;
            this.downloadFilePath = getDownloadDest();
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
                sendMessage(SUCCESS_MESSAGE, new Object[]{response, file});
            } catch (IOException ex) {
                sendMessage(FAILURE_MESSAGE, new Object[]{response.request(), ExceptionUtil.getExceptionComment(ex)});
            } finally {
                Util.closeQuietly(inputStream);
                Util.closeQuietly(outputStream);
            }
            sendMessage(FINISH_MESSAGE, null);
        }
    }
}
