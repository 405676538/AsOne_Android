package com.example.asone_android.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.asone_android.net.ApiClient;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;

public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    private static Context sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        initNet();
        sAppContext = this;
        initPush();
    }

    private void initPush() {
        UMConfigure.init(sAppContext,"5c9367583fc195073500125a","AsOne",UMConfigure.DEVICE_TYPE_PHONE,"2c29f3e1093cc022a85fd91d407c7df9");
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String s) {
                Log.i(TAG,"注册成功：deviceToken：-------->  " + s);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.i(TAG,"注册失败：deviceToken：-------->  " + s + "======"+s1);
            }
        });
        PlatformConfig.setQQZone("1108079942", "Inf15WX3KP20XVoV");
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
    }

    public static Context getAppContext() {
        return sAppContext;
    }


    private void initNet() {
        ApiClient.init();
    }
}
