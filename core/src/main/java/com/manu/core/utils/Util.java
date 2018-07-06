package com.manu.core.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by jzman
 * Powered by 2018/6/8 0008.
 */

public class Util {
    /**
     * 判断str null,"","null" 均视为空.
     * @param str 字符
     * @return 结果 boolean
     */
    public static boolean isNotEmpty(String str) {
        boolean bool;
        if (str == null || "null".equals(str) || "".equals(str)) {
            bool = false;
        } else {
            bool = true;
        }
        return bool;
    }

    public static String getOriginalFundData(Context context,String fileName) {
        InputStream input = null;
        try {
            input = context.getAssets().open(fileName);
            String json = convertStreamToString(input);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * input 流转换为字符串
     *
     * @param is
     * @return
     */
    private static String convertStreamToString(java.io.InputStream is) {
        String s = null;
        try {
            Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A");
            if (scanner.hasNext()) {
                s = scanner.next();
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
