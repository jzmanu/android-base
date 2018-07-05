package com.manu.core.image.transformation;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.squareup.picasso.Transformation;

/**
 * RoundTransformation.java
 * Created by jzman
 * Powered by 2018/5/24 0024 14:59
 */

public class RoundTransformation implements Transformation {

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
        RectF rectF = new RectF(x,y,x + size, size);
        canvas.drawRoundRect(rectF,50,50,paint);
        rectBitmap.recycle();
        source.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "circle";
    }
}
