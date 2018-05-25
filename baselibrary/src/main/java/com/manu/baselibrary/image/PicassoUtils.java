package com.manu.baselibrary.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.manu.baselibrary.R;
import com.manu.baselibrary.http.HttpClient;
import com.manu.baselibrary.http.OkHttp3Downloader;
import com.manu.baselibrary.http.util.LogUtil;
import com.manu.baselibrary.image.transformation.CircleTransformation;
import com.manu.baselibrary.image.transformation.RoundTransformation;
import com.squareup.picasso.Picasso;
import java.io.File;

public class PicassoUtils {

    private static Picasso picasso;

    private static Picasso getPicasso() {
        if (picasso == null) {
            LogUtil.log("Picasso", "please initPicasso in the application!");
        } else {
            return picasso;
        }
        return null;
    }

    public static void initPicasso(Context context, HttpClient httpClient) {
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.loggingEnabled(true);
        builder.indicatorsEnabled(false);
        builder.downloader(new OkHttp3Downloader(httpClient.getOkHttpClient()));
        builder.defaultBitmapConfig(Bitmap.Config.ARGB_8888);
        if (LogUtil.isDebug) {
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    LogUtil.log("buildPicasso", uri.toString());
                    LogUtil.log("buildPicasso", exception.getMessage());
                }
            });
        }
        picasso = builder.build();
    }

    /**
     * 加载网络图片
     *
     * @param url
     * @param imageView
     */
    public static void loadNetworkImage(String url, ImageView imageView) {
        getPicasso()
                .load(url)
                .placeholder(R.drawable.icon_placeholder)
                .error(R.drawable.icon_failure)
                .noFade()
                .fit()
                .centerCrop()
                .into(imageView);
    }

    /**
     * 加载本地图片
     *
     * @param path
     * @param imageView
     */
    public static void loadLocalImage(String path, ImageView imageView) {
        getPicasso()
                .load(new File(path))
                .placeholder(R.drawable.icon_placeholder)
                .error(R.drawable.icon_failure)
                .transform(new CircleTransformation())
                .noFade()
                .fit()
                .centerCrop()
                .into(imageView);
    }

    /**
     * 加载项目资源图片
     *
     * @param resId
     * @param imageView
     */
    public static void loadResourceImage(int resId, ImageView imageView) {
        getPicasso()
                .load(resId)
                .placeholder(R.drawable.icon_placeholder)
                .error(R.drawable.icon_failure)
                .transform(new RoundTransformation())
                .noFade()
                .fit()
                .centerCrop()
                .into(imageView);
    }

}
