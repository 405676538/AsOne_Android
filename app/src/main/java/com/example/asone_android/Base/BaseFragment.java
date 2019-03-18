package com.example.asone_android.Base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public abstract class BaseFragment extends Fragment {

    private Toast ShortToast;
    public Context mContext;


    protected abstract int getLayout();

    protected abstract void initData();

    protected abstract void initView();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        return LayoutInflater.from(mContext).inflate(getLayout(),container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    protected void showShortToast(int messageId) {
        showShortToast(getString(messageId));
    }

    protected void showShortToast(final String message) {
        if (message.equals("`")) {
            return;
        }

        new Handler(Looper.getMainLooper()).post(() -> {
            if (ShortToast == null) {
                ShortToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
            } else {
                ShortToast.setText(message);
            }
            ShortToast.show();
        });


    }
}
