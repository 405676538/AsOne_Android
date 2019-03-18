package com.example.asone_android.app;

import android.app.Application;
import android.content.Context;

import com.example.asone_android.net.ApiClient;

public class BaseApplication extends Application {
    private static Context sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        initNet();
        sAppContext = this;
    }

    public static Context getAppContext() {
        return sAppContext;
    }


    private void initNet() {
        ApiClient.init();
    }
}
