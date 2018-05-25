package com.manu.baselibrary.http.callback;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.manu.baselibrary.http.progress.ProgressListener;

import okhttp3.Callback;

/**
 * BaseCallback.java
 *
 * @author jzman
 * create at 2018/5/21 0021 11:37
 */
public abstract class BaseCallback implements Callback, ProgressListener {

    public static final int SUCCESS_MESSAGE  = 0;
    public static final int FAILURE_MESSAGE  = 1;
    public static final int FINISH_MESSAGE   = 2;
    public static final int PROGRESS_MESSAGE = 3;
    public static final int CANCEL_MESSAGE   = 4;
    public static final int UNAUTHORIZED_MESSAGE = 99;

    public abstract void handSuccess(Message message);

    public abstract void handFailure(Message message);

    public abstract void handFinish(Message message);

    public abstract void handProgress(Message message);

    public abstract void handCancel(Message message);

    public abstract void handUnauthorized(Message message);

    public void onProgressDownload(long bytesRead, long contentLength) {
    }

    public void onProgressUpload(long bytesWrite, long contentLength) {
    }

    public void onFinish() {
    }

    public void onCancel() {
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {

        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS_MESSAGE:
                    handSuccess(msg);
                    break;
                case FAILURE_MESSAGE:
                    handFailure(msg);
                    break;
                case FINISH_MESSAGE:
                    handFinish(msg);
                    break;
                case CANCEL_MESSAGE:
                    handCancel(msg);
                    break;
                case PROGRESS_MESSAGE:
                    handProgress(msg);
                    break;
                case UNAUTHORIZED_MESSAGE:
                    handUnauthorized(msg);
                    break;
            }
        }
    };

    public void sendMessage(int what, Object obj) {
        Message message = handler.obtainMessage(what);
        message.obj = obj;
        message.sendToTarget();
    }

    public void sendCancelMessage() {
        Message message = handler.obtainMessage(CANCEL_MESSAGE);
        message.sendToTarget();
    }
}
