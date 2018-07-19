package com.manu.core.http.api;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by jzman
 * Powered by 2018/6/8 0008.
 */

public interface CommonApi {

    /**
     * Get请求（路径）
     * @param path
     * @return
     */
    @GET("{path}")
    Call<ResponseBody> get(@Path(value = "path") String path);

    /**
     * Get请求（查询参数）
     * @param path
     * @param params
     * @return
     */
    @GET("{path}")
    Call<ResponseBody>  get(@Path(value = "path") String path, @QueryMap Map<String, Object> params);

    /**
     * Get请求（完整Url）
     * @param url
     * @return
     */
    @GET
    Call<ResponseBody> getWithUrl(@Url String url);

    /**
     * Post请求
     * @param path
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("{path}")
    Call<ResponseBody> post(@Path(value = "path") String path, @FieldMap Map<String,Object> params);

    /**
     * 上传文件
     * @param path
     * @param params
     * @return
     */

    @POST("{path}")
    Call<ResponseBody> uploadFile(@Path(value = "path") String path, @Body RequestBody requestBody);

    @Multipart
    @POST("{path}")
    Call<ResponseBody> upload(@Path(value = "path") String path, @FieldMap Map<String, Object> params, @Part MultipartBody.Part[] partMap);

    /**
     * 下载文件
     * @param url
     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> downLoadFile(@Url String url);

}
