package com.manu.core.image.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Powered by jzman.
 * Created on 2018/10/19 0019.
 */
public class GlideImageLoader implements IImageLoader {

    private RequestOptions mRequestOptions;

    public GlideImageLoader() {
    }

    public GlideImageLoader(RequestOptions requestOptions) {
        this.mRequestOptions = requestOptions;
    }

    @Override
    public void showImage(Context context, ImageView ivImage, String url) {
    }

    @Override
    public void showImageFromFtp(Context context, ImageView ivImage, String url) {

    }
}
