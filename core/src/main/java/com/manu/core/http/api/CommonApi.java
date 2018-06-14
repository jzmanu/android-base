package com.manu.core.http.api;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by jzman
 * Powered by 2018/6/8 0008.
 */

public interface CommonApi {
    /**
     * 没有参数的Get请求
     * @param path
     * @return
     */
    @GET("{path}")
    Call<ResponseBody> get(@Path(value = "path") String path);

    /**
     * 有参数的Get请求
     * @param path
     * @param params
     * @return
     */
    @GET("{path}")
    Call<ResponseBody>  get(@Path(value = "path") String path, @QueryMap Map<String, Object> params);

}
