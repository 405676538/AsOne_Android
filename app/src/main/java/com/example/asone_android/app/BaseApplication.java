package com.example.asone_android.app;

import android.app.Application;

import com.example.asone_android.net.ApiClient;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initNet();
    }

    private void initNet() {
        ApiClient.init();
    }
}
