package com.manu.baselibrary.http.util;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * NetworkUtil.java
 * Created by jzman
 * Powered by 2018/5/22 0022 14:13
 */
public class NetworkUtil {

    private static Context mContext;

    public static void init(Context context){
        mContext = context.getApplicationContext();
    }

    /**
     * 检查网络是否可用
     * @return
     */
    public static boolean isNetworkConnected() {
        if (mContext != null) {
		    //获取网络管理器（系统服务）
            ConnectivityManager mConnectivityManager = (ConnectivityManager)
                    mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		    //获取当前激活的网络信息
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
