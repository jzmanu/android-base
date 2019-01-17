package com.manu.core.image.glide;

import android.content.Context;
import android.widget.ImageView;

/**
 * 加载图片的方式
 * Powered by jzman.
 * Created on 2018/10/19 0019.
 */
public interface IImageLoader {
    /**
     * 图片链接
     * @param context
     * @param ivImage
     * @param url
     */
    void showImage(Context context, ImageView ivImage, String url);

    /**
     * 访问Ftp上的图片
     * @param context
     * @param ivImage
     * @param url
     */
    void showImageFromFtp(Context context, ImageView ivImage, String url);
}
