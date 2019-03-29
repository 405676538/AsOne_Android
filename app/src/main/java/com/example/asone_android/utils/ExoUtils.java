package com.example.asone_android.utils;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.io.File;

/**
 * 所有音视频播放
 *
 * implementation 'com.google.android.exoplayer:exoplayer:2.9.6'
 *
 * 测试网站 http://media.w3.org/2010/05/sintel/trailer.mp4
 *
 * 使用方法
 *   val player = ExoUtils.initPlayer(this)
 *         video_view.player = player
 *
 *         player.playWhenReady = true
 *         player.prepare(ExoUtils.getMediaSourse(this, filePath), false, true)
 *
 */
public class ExoUtils {

    public static ExoPlayer initPlayer(Context context) {
        return ExoPlayerFactory.newSimpleInstance(context, new DefaultRenderersFactory(context),
                new DefaultTrackSelector(), new DefaultLoadControl());
    }

    public static MediaSource getMediaSourse(Context context, String filePath) {
        Uri uri;
        if (filePath.contains("http")) {
            uri = Uri.parse(filePath);

            return buildHttpMediaSource(uri);

        } else {
            uri = geturi(filePath,context);
            return buildMediaSource(uri,context);

        }
    }

    public static Uri geturi(String filePath,Context context){
        return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName()
                + ".FileProvider", new File(filePath));
    }

    /** 获取本地 或者 http资源 **/
    private static MediaSource buildMediaSource(Uri uri,Context context) {
        return new ExtractorMediaSource.Factory(
                new DefaultDataSourceFactory(context,"exoplayer-codelab")).
                createMediaSource(uri);
    }

    private static MediaSource buildHttpMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    /**
     * 播放本地视频
     */
    public static void openVideo(Context context, String filePath) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName()
                    + ".FileProvider", new File(filePath));
            intent.setDataAndType(uri, "video/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.i("openVideo", e.getMessage());
        }

    }

    /**
     * 释放播放器
     */
    public static void releasePlayer(ExoPlayer player) {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }
}
