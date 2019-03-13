package com.example.asone_android.Base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

public abstract class BaseActivity extends Activity {
    private Toast ShortToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
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
}
