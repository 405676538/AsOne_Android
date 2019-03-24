package com.example.asone_android.Base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asone_android.R;
import com.example.asone_android.app.Constant;
import com.example.asone_android.bean.EventBusMessage;
import com.example.asone_android.net.MusicPresenter;
import com.example.asone_android.utils.ACache;
import com.example.asone_android.utils.AppUtils;
import com.example.asone_android.view.LoadingDialog;
import com.example.asone_android.view.PopupWindowFactory;
import com.umeng.message.PushAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private Toast ShortToast;
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        EasyPermissions.requestPermissions(this,"q", 7, Constant.sPermissionsArray);
        mContext = this;
        EventBus.getDefault().register(this);
        PushAgent.getInstance(mContext).onAppStart();
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
        if (TextUtils.isEmpty(message)) {
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
        switch (eventBusMessage.getCode()){
            case EventBusMessage.SHOW_NO_LOGIN:
                Log.i(TAG, "onEventMainThread: 登录");
                showNoLoginDialog();
                break;
                default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoaddialog();
        EventBus.getDefault().unregister(this);
    }

    public void showNoLoginDialog(){
        View view = LayoutInflater.from(this).inflate(R.layout.popup_select_login, null);
        PopupWindowFactory mWindowFactory = new PopupWindowFactory(this, view);
        ImageView mIvqq = view.findViewById(R.id.iv_qq);
        ImageView mIvwx = view.findViewById(R.id.iv_weixin);
        mIvqq.setOnClickListener(v -> {
            login(SHARE_MEDIA.QQ);
            showNoLoginDialog();
            mWindowFactory.dismiss();
        });
        mIvwx.setOnClickListener(v -> {
            login(SHARE_MEDIA.WEIXIN);
            showLoadDialog();
            mWindowFactory.dismiss();
        });
        try {
            FrameLayout decorView = (FrameLayout) this.getWindow().getDecorView();
            mWindowFactory.showAtLocation(decorView, Gravity.CENTER, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean checkPermission(String... permissions) {
        if (permissions.length == 0) {
            return EasyPermissions.hasPermissions(this, Constant.sPermissionsArray);
        } else {
            return EasyPermissions.hasPermissions(this, permissions);
        }
    }

    public void login(SHARE_MEDIA share_media){
        UMShareAPI api = UMShareAPI.get(this);
        api.getPlatformInfo(this,share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                String uid = map.get("uid");
                String name = map.get("name");
                String head = map.get("iconurl");
                ACache.get().put(ACache.TAG_USER_ID,uid);
                ACache.get().put(ACache.TAG_USER_NAME,name);
                ACache.get().put(ACache.TAG_USER_HEAD,head);

                showShortToast("欢迎你"+name);
                dismissLoaddialog();
                new MusicPresenter().addUser(uid, name, head, baseJson -> {
                    Log.i(TAG, "onComplete: 添加用户成功");
                });
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                dismissLoaddialog();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                dismissLoaddialog();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    LoadingDialog loadDialog;
    public void showLoadDialog() {
        runOnUiThread(() -> {
            if (loadDialog == null) {
                loadDialog = AppUtils.getCricleDialog(this);
            }
            if (!loadDialog.isShowing())
                loadDialog.show();
        });

    }

    public void dismissLoaddialog() {
        runOnUiThread(() -> {
            if (loadDialog != null && loadDialog.isShowing())
                loadDialog.dismiss();
        });
    }
}
