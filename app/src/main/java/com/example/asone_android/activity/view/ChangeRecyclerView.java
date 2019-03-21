package com.example.asone_android.activity.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class ChangeRecyclerView extends RecyclerView {
    private static final String TAG = "ChangeRecyclerView";
    private int mLastX = 0;
    private int mLastY = 0;

    public ChangeRecyclerView(@NonNull Context context) {
        super(context);
    }

    public ChangeRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChangeRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }


}
