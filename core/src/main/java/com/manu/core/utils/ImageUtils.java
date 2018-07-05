package com.manu.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.manu.core.BuildConfig;
import com.manu.core.http.OkHttp3Downloader;
import com.manu.core.http.RetrofitClient;
import com.squareup.picasso.Picasso;


public class ImageUtils {

    private static Picasso picasso;

    public static void buildPicasso(Context context) {
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.loggingEnabled(true);
        builder.indicatorsEnabled(false);
//        builder.downloader(new OkHttp3Downloader(RetrofitClient.getOkHttpClient(context)));
        builder.defaultBitmapConfig(Bitmap.Config.ARGB_8888);
        if (BuildConfig.DEBUG) {
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    System.out.println(exception.getLocalizedMessage());
                    System.out.println(exception.getMessage());
                    Log.i("ImageUtils",exception.toString());
                }
            });
        }
        picasso = builder.build();
    }

    public static Picasso getPicasso() {
        return picasso;
    }
}
