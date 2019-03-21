package com.example.asone_android.app;

import android.app.Application;
import android.content.Context;

import com.example.asone_android.net.ApiClient;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;

public class BaseApplication extends Application {
    private static Context sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        initNet();
        sAppContext = this;
        initPush();
    }

    private void initPush() {
        UMConfigure.init(sAppContext, UMConfigure.DEVICE_TYPE_PHONE,"" );
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFailure(String s, String s1) {
               //注册成功会返回deviceToken
            }
        });
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
    }

    public static Context getAppContext() {
        return sAppContext;
    }


    private void initNet() {
        ApiClient.init();
    }
}
