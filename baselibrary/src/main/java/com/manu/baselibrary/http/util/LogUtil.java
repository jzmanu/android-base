package com.manu.baselibrary.http.util;

import android.util.Log;

/**
 * LogUtil.java
 * @author jzman
 * create at 2018/5/22 0022 10:35
 */

public class LogUtil {
    public static boolean isDebug = true;
    public static void log(String tag, String message){
        if (isDebug) Log.i(tag, message);
    }
}
