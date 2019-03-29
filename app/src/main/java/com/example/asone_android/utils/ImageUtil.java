package com.example.asone_android.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.Util;
import com.example.asone_android.R;
import com.example.asone_android.app.BaseApplication;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by 唐浩 on 2018/3/14.
 */

public class ImageUtil {

    /****
     * 加载圆形图片
     * 加载普通网络图片
     ****/

    public static void GlidegetRoundImage(String url, final ImageView imageView) {
        if (Util.isOnMainThread()) {
            RequestOptions requestOptions = RequestOptions.circleCropTransform();
            Glide.with(BaseApplication.getAppContext()).load(url).apply(requestOptions).into(imageView);
        }
    }

    public static void GlidegetRoundFourImage(String url, final ImageView imageView,int roundRadius) {
        if (Util.isOnMainThread()) {
            RoundedCorners roundedCorners = new RoundedCorners(roundRadius);
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
            Glide.with(BaseApplication.getAppContext()).load(url).apply(options).into(imageView);
        }
    }

    public static void GlideImage(String url, final ImageView imageView) {
        Glide.with(BaseApplication.getAppContext()).setDefaultRequestOptions(getRequestOptions()).load(url).into(imageView);
    }

    public static void GlideImage(Bitmap url, final ImageView imageView) {
        Glide.with(BaseApplication.getAppContext()).setDefaultRequestOptions(getRequestOptions()).load(url).into(imageView);
    }


    @SuppressLint("CheckResult")
    private static RequestOptions getRequestOptions() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.color.black_red);
        requestOptions.error(R.color.black_red);
        return requestOptions;
    }

    //view 转bitmap

    public static Bitmap convertViewToBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap mCacheBitmap = view.getDrawingCache();
        Bitmap mBitmap = Bitmap.createBitmap(mCacheBitmap);
        return mBitmap;

    }

    public static Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        Bitmap bitmap;
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        System.out.println("Drawable转Bitmap");
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        bitmap = Bitmap.createBitmap(w, h, config);
//注意,下面三行代码要用到,否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    public static String getScreenImage(View currentView) {
        String filePath = "";
        currentView.setDrawingCacheEnabled(true);
        currentView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(currentView.getDrawingCache());
        if (bitmap != null) {
            try {
                String sdCardPath = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM + File.separator + "Camera" + File.separator;
//                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                filePath = sdCardPath + File.separator + System.currentTimeMillis() + "screenshot.png";
                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
            }
        }
        return filePath;
    }

}
