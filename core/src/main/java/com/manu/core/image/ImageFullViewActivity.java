package com.manu.core.image;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.manu.core.R;
import com.manu.core.utils.BitmapUtil;
import com.manu.core.utils.ImageUtils;
import com.manu.core.utils.ScreenUtils;
import com.manu.core.widget.TouchImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.io.File;
/**
 *
 * @author: jzman
 * @time: 2018/7/3 0003 14:41
 */
public class ImageFullViewActivity extends Activity {
    public static final String IMAGE_URL = ":image_url";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        relativeLayout.setBackgroundColor(Color.parseColor("#CC000000"));

        final ImageView imageView = new TouchImageView(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageView.setLayoutParams(layoutParams);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.CENTER);

        final String url = getIntent().getStringExtra(IMAGE_URL);
        loadImage(url, imageView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadImage(url, imageView);
            }
        }, 100);

        relativeLayout.addView(imageView);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(-1, -1);
            }
        });
        setContentView(relativeLayout);
    }

    private void loadImage(String url, final ImageView imageView) {
        RequestCreator creator = null;
        if (url.startsWith("http")) {
            creator = ImageUtils.getPicasso().load(url);
        } else {
            creator = ImageUtils.getPicasso().load(new File(url));
        }
        creator.placeholder(R.drawable.common_loading).error(R.drawable.ic_image_default);
        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                int screenWidth = ScreenUtils.getScreenWidth(ImageFullViewActivity.this);
                int screenHeight = ScreenUtils.getScreenHeight(ImageFullViewActivity.this);
                if (bitmap.getWidth() <= screenWidth && bitmap.getHeight() <= screenHeight) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    int width, height;
                    if (bitmap.getWidth() > screenWidth && bitmap.getHeight() <= screenHeight) {
                        width = screenWidth;
                        height = (int) (((float) bitmap.getHeight() / (float) bitmap.getWidth()) * screenWidth);
                    } else if (bitmap.getWidth() <= screenWidth && bitmap.getHeight() > screenHeight) {
                        width = bitmap.getWidth() / bitmap.getHeight() * screenHeight;
                        height = screenHeight;
                    } else {
                        if ((double) bitmap.getHeight() / (double) screenHeight >= (double) bitmap.getWidth() / (double) screenWidth) {
                            width = (int) ((double) bitmap.getWidth() / (double) bitmap.getHeight() * (double) screenHeight);
                            height = screenHeight;
                        } else {
                            width = screenWidth;
                            height = (int) (((float) bitmap.getHeight() / (float) bitmap.getWidth()) * screenWidth);
                        }
                    }
                    if (width > 0 && height > 0) {
                        imageView.setImageBitmap(BitmapUtil.getThumbnailBitmap(bitmap, width, height));
                    }
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                imageView.setImageDrawable(errorDrawable);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                imageView.setImageDrawable(placeHolderDrawable);
            }
        };
        creator.into(target);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(-1, -1);
    }
}
