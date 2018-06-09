package com.manu.core.utils;

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
}
