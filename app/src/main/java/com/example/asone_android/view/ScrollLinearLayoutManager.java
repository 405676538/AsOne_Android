package com.example.asone_android.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

public class ScrollLinearLayoutManager extends LinearLayoutManager {
    private boolean mCanVerticalScroll = true;

    public ScrollLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public boolean canScrollVertically() {
        if (!mCanVerticalScroll){
            return false;
        }else {
            return super.canScrollVertically();
        }
    }

    public void setmCanVerticalScroll(boolean b){
        mCanVerticalScroll = b;
    }


}
