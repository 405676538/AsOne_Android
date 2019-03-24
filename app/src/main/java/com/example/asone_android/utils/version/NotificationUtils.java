package com.example.asone_android.utils.version;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.example.asone_android.R;
import com.example.asone_android.utils.PhoneUtil;


/** 兼容8.0 */
public class NotificationUtils extends ContextWrapper {
    private NotificationManager manager;
    public static final int NOTIFICATION_ID = 1;
    public static final int NOTIFICATION_ID_MAIN = 2;
    public static final int NOTIFICATION_ID_UPDATE = 1000;
    public static final String id = "peoplesafely";
    public static final String name = "channel_name_1";
    private static NotificationUtils sNotificationUtils;

    public static NotificationUtils getInstance(Context context){
        if (sNotificationUtils == null){
            synchronized (NotificationUtils.class){
                if (sNotificationUtils == null){
                    sNotificationUtils = new NotificationUtils(context);
                }
            }
        }
        return sNotificationUtils;
    }

    private NotificationUtils(Context context){
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel(){
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    private NotificationManager getManager(){
        if (manager == null){
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String content){
        return new Notification.Builder(getApplicationContext(),id)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setSound(null,null)
                .setAutoCancel(true);
    }

    public NotificationCompat.Builder getNotification_25(String title, String content){
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }

    public void sendNotification(String ticker, String title, String content, PendingIntent contentIntent, int icon){
        if (Build.VERSION.SDK_INT>=26){
            createNotificationChannel();
            Notification notification = getChannelNotification
                    (title, content)
                    .setContentIntent(contentIntent)
                    .setSmallIcon(icon)
                    .setTicker(ticker)
                    .build();
            getManager().notify(222,notification);
        }else{
            PhoneUtil.mobileShake(this, 1000);
            Notification notification = getNotification_25
                    (title, content)
                    .setContentIntent(contentIntent)
                    .setSmallIcon(icon)
                    .setTicker(ticker)
                    .build();
            getManager().notify(222,notification);
        }
    }





    /** 主通知单独一套  （区分通知优先级 以及是否震动响铃）*/



    private NotificationManager mainManager;
    public static final String main_id = "peoplesafely_main";
    public static final String main_name = "channel_name_main";

    public NotificationManager getMainManager(){
        if (mainManager == null){
            mainManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return mainManager;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createMainNotificationChannel(){
        NotificationChannel channel = new NotificationChannel(main_id, main_name, NotificationManager.IMPORTANCE_LOW);
        channel.setSound(null,null);
        getMainManager().createNotificationChannel(channel);
    }





    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getMainChannelNotification(String title, String content){
        return new Notification.Builder(getApplicationContext(), main_id)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setSound(null,null)
                .setAutoCancel(true);
    }
    public NotificationCompat.Builder getMainNotification_25(String title, String content){
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }

    NotificationCompat.Builder cBuilder;
    public void setProgressView(String url) {
        initChannelId("版本更新", false);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(),
                1, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
        cBuilder = new NotificationCompat.Builder(getApplicationContext(), id + NOTIFICATION_ID_UPDATE);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.view_update_version);
        remoteViews.setViewVisibility(R.id.update_version_pb, View.VISIBLE);
        remoteViews.setViewVisibility(R.id.update_version_cancel_tv, View.VISIBLE);
        cBuilder.setWhen(System.currentTimeMillis());
        cBuilder.setAutoCancel(true);
        cBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        cBuilder.setCustomContentView(remoteViews);
        Intent cancelIntent = new Intent( NOTIFICATION_ID_UPDATE+"");
        cancelIntent.putExtra("cancel", url);
        PendingIntent closePendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 2, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.update_version_cancel_tv, closePendingIntent);
        remoteViews.setTextViewText(R.id.update_version_tv, "正在下载……");
        remoteViews.setProgressBar(R.id.update_version_pb, 0, 0, false);
        cBuilder.setOngoing(true);
        cBuilder.setContentIntent(pIntent);
        cBuilder.setSmallIcon(R.mipmap.ic_launcher);
        getMainManager().notify(NOTIFICATION_ID_UPDATE, cBuilder.build());
    }

    private void initChannelId(String channelName, boolean hasSound) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(id + NOTIFICATION_ID_UPDATE, channelName,
                    NotificationManager.IMPORTANCE_DEFAULT);
            //是否绕过请勿打扰模式
            channel.canBypassDnd();
            if (!hasSound) {
                channel.setSound(null, null);
            }
            channel.enableVibration(hasSound);
            getMainManager().createNotificationChannel(channel);
        }
    }

    public void cancel(int id) {
        getMainManager().cancel(id);
    }

    @SuppressLint("RestrictedApi")
    public void downloadSuccess(Context context, String path){
        RemoteViews remoteViews = cBuilder.getContentView();
        remoteViews.setViewVisibility(R.id.update_version_pb, View.GONE);
        remoteViews.setViewVisibility(R.id.update_version_cancel_tv, View.GONE);
        remoteViews.setTextViewText(R.id.update_version_tv,"下载完成，点击安装");
        PendingIntent pIntent = PendingIntent.getActivity(context,
                111, InstallAppUtils.Companion.openFile(context, path), PendingIntent.FLAG_UPDATE_CURRENT);
        cBuilder.setContentIntent(pIntent);
        cBuilder.setCustomContentView(remoteViews);
        getMainManager().notify(NOTIFICATION_ID_UPDATE, cBuilder.build());
    }

    @SuppressLint("RestrictedApi")
    public void notifyProgress(String contentText, int max, int progress){
        RemoteViews remoteViews = cBuilder.getContentView();
        remoteViews.setViewVisibility(R.id.update_version_pb, View.VISIBLE);
        remoteViews.setViewVisibility(R.id.update_version_cancel_tv, View.VISIBLE);
        remoteViews.setProgressBar(R.id.update_version_pb,max, progress, false);
        remoteViews.setTextViewText(R.id.update_version_tv,contentText);
        cBuilder.setCustomContentView(remoteViews);
        getMainManager().notify(NOTIFICATION_ID_UPDATE, cBuilder.build());
    }

    @SuppressLint("RestrictedApi")
    public void downloadFail(){
        RemoteViews remoteViews = cBuilder.getContentView();
        remoteViews.setViewVisibility(R.id.update_version_pb, View.GONE);
        remoteViews.setViewVisibility(R.id.update_version_cancel_tv, View.GONE);
        remoteViews.setTextViewText(R.id.update_version_tv,"下载失败，请检查网络");
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(),
                111, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
        cBuilder.setContentIntent(pIntent);
        cBuilder.setCustomContentView(remoteViews);
        getMainManager().notify(NOTIFICATION_ID_UPDATE, cBuilder.build());
    }
}
