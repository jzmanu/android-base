package com.manu.baselibrary.http.util;

import com.google.gson.JsonSyntaxException;
import org.json.JSONException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import okhttp3.internal.connection.RouteException;

public class ExceptionUtil {
    public static String getExceptionComment(Exception exception) {
        System.out.println("getExceptionComment---"+ exception.toString());
        String comment = "未知错误";
        if (exception instanceof ConnectException) {
            return "网络连接失败";
        } else if (exception instanceof SocketTimeoutException) {
            return "网络连接超时";
        } else if (exception instanceof SocketException) {
            return "网络连接错误";
        } else if (exception instanceof IOException) {
            return "数据读写错误";
        }else if (exception instanceof RouteException) {
            return "连接错误";
        } else if (exception instanceof JSONException) {
            return "数据解析错误";
        } else if (exception instanceof JsonSyntaxException) {
            return "JSON数据解析错误";
        }
        return comment;
    }

    public static String getHttpCodeComment(int stateCode) {
        String comment = "未知错误";
        if (stateCode >= 400 && stateCode < 500) {
            if (stateCode == 401) {
                return "登陆过期";
            } else if (stateCode == 403) {
                return "没有权限";
            } else if (stateCode == 404) {
                return "未知请求";
            } else {
                return "请求错误";
            }
        } else if (stateCode > 499) {
            if (stateCode == 500) {
                return "服务器内部错误";
            } else if (stateCode == 501) {
                return "无法识别请求";
            } else if (stateCode == 502) {
                return "错误网关";
            } else if (stateCode == 503) {
                return "服务不可用";
            } else if (stateCode == 504) {
                return "网关超时";
            } else {
                return "服务器异常";
            }
        }
        return comment;
    }
}
