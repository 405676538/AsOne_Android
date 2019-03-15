package com.example.asone_android.Base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.asone_android.app.Constant;
import com.example.asone_android.bean.EventBusMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import pub.devrel.easypermissions.EasyPermissions;

public abstract class BaseActivity extends AppCompatActivity {
    private Toast ShortToast;
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        EasyPermissions.requestPermissions(this,"q", 7, Constant.sPermissionsArray);
        mContext = this;
        EventBus.getDefault().register(this);
        initView();
        initData();
    }
    protected abstract int getLayout();

    protected abstract void initData();

    protected abstract void initView();


    protected void showShortToast(int messageId) {
        showShortToast(getString(messageId));
    }

    protected void showShortToast(final String message) {
        if (message.equals("`")) {
            return;
        }
        runOnUiThread(() -> {
            if (ShortToast == null) {
                ShortToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            } else {
                ShortToast.setText(message);
            }
            ShortToast.show();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Subscribe
    public void onEventMainThread(EventBusMessage eventBusMessage) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
