package com.manu.core.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Locale;

public class BitmapUtil {

    /**
     * 图片质量的压缩，一定程度会失真
     * @param bmp
     * @param file
     */
    public static void compressImage(Bitmap bmp, File file) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 80;
        bmp.compress(CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            options -= 10;
            bmp.compress(CompressFormat.JPEG, options, baos);
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据请求图片宽高，计算采样比并转化为Bitmap
     * @param srcPath
     * @param reqWidth 800
     * @param reqHeight 480
     * @return
     */
    public static Bitmap compressImageFromFile(String srcPath,int reqWidth,int reqHeight) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        //在不分配内存的情况下读取图片尺寸和大小
        option.inJustDecodeBounds = true;
        int width = option.outWidth;
        int height = option.outHeight;
        //计算采样率
        int inSampleSize = 1;
        if (width > reqWidth || height > reqHeight) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        //设置采样率
        option.inSampleSize = inSampleSize;
        //设置图片显示模式，不同模式所占内存不一样
        option.inPreferredConfig = Config.ARGB_8888;
        //正式解码之前设置inJustDecodeBounds为false
        option.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(srcPath, option);
    }

    public static InputStream getCompressImageStream(String path,int reqWidth,int reqHeight) {
        Bitmap bitMap = compressImageFromFile(path, reqWidth, reqHeight);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitMap.compress(CompressFormat.JPEG, 100, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public static byte[] getCompressByte(String path,int reqWidth,int reqHeight) {
        Bitmap bitMap = compressImageFromFile(path, reqWidth, reqHeight);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitMap.compress(CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static byte[] getBitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap getBytesToBimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    public static Bitmap getThumbnailBitmap(Bitmap bitmap, int width, int height) {
        return ThumbnailUtils.extractThumbnail(bitmap, width, height);
    }

    public static boolean isImage(String fileName) {
        if (fileName != null) {
            fileName = fileName.toLowerCase(Locale.getDefault());
            if (fileName.endsWith("jpg") || fileName.endsWith("png") || fileName.endsWith("bmp") || fileName.endsWith("jpeg") || fileName.endsWith("ico")) {
                return true;
            }
        }
        return false;
    }

}
