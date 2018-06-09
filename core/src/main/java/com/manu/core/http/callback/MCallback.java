package com.manu.core.http.callback;

import com.google.gson.JsonSyntaxException;
import com.manu.core.http.bean.ResultBean;
import org.json.JSONException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import okhttp3.internal.connection.RouteException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jzman
 * Powered by 2018/6/9 0009.
 */

public abstract class MCallback<T extends ResultBean> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.raw().code() == 200 ) {
            T t = response.body();
            if (t!=null){
                if (t.isError()) {
                    onFail(t.getMessage());
                }else{
                    onSuccess(response);
                }
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        String message = "请求失败";
        if (t instanceof ConnectException) {
            message = "网络连接失败";
        } else if (t instanceof SocketTimeoutException) {
            message = "网络连接超时";
        } else if (t instanceof SocketException) {
            message = "网络连接错误";
        } else if (t instanceof IOException) {
            message = "数据读写错误";
        }else if (t instanceof RouteException) {
            message = "连接错误";
        } else if (t instanceof JSONException) {
            message = "数据解析错误";
        } else if (t instanceof JsonSyntaxException) {
            message = "JSON数据解析错误";
        }

        onFail(message);
    }

    public abstract void onSuccess(Response response);

    public abstract void onFail(String message);
}
