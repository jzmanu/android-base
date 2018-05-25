package com.manu.baselibrary.http.util;

import android.text.TextUtils;

import java.util.Map;

/**
 * 参数格式化
 * Created by jzman
 * Powered by 2018/5/22 0022 14:14
 */
public class ParamsUtil {

    public static String attachHttpGetParams(String url, Map<String, ? extends Object> params) {
        if (url.contains("?")) {
            return url + "&" + formatParams(params);
        } else {
            return url + "?" + formatParams(params);
        }
    }

    /**
     * format params.
     * @param params
     * @return  return name=tom&age=10
     */
    private static String formatParams(Map<String, ? extends Object> params) {
        String urlParams = "";
        for (String key : params.keySet()) {
            urlParams += "&" + key + "=" + params.get(key);
        }
        if (!TextUtils.isEmpty(urlParams) && urlParams.charAt(0) == '&') {
            urlParams = urlParams.replaceFirst("&", "");
        }
        return urlParams;
    }
}
