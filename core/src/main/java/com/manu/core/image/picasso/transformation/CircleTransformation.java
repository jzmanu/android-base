package com.manu.core.image.picasso.transformation;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

/**
 * CircleTransformation.java
 * Created by jzman
 * Powered by 2018/5/24 0024 9:26
 */

public class CircleTransformation implements Transformation {

    @Override
    public Bitmap transform(Bitmap source) {
        //根据原Bitmap获得最小边的正方形所创建的Bitmap
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap rectBitmap = Bitmap.createBitmap(source, x, y, size, size);
        //绘制
        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(rectBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        rectBitmap.recycle();
        source.recycle();
        return bitmap;
    }
    @Override
    public String key() {
        return "circle";
    }
}
