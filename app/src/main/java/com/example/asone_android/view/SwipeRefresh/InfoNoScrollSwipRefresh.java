package com.example.asone_android.view.SwipeRefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by liuxiaowei on 2016/8/5.
 */
public class InfoNoScrollSwipRefresh extends SwipeRefreshLayout{

    float lastx = 0;
    float lasty = 0;
    boolean isMovePic = false;
    public InfoNoScrollSwipRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            lastx = ev.getX();
            lasty = ev.getY();
            isMovePic = false;
            return super.onInterceptTouchEvent(ev);
        }

        int x2 = (int) Math.abs(ev.getX() - lastx);
        int y2 = (int) Math.abs(ev.getY() - lasty);

        //滑动图片最小距离检查:如果横向滑动大于竖直滑动，不许刷新（返回false不反拦截子view的滑动事件）
        //-------------------或者横滑大于100像素，不许刷新
        if (x2>y2){
            if (x2>=100) isMovePic = true;
            return false;
        }

        //是否移动图片(下拉刷新不处理)
        if (isMovePic){
            return false;
        }

        return super.onInterceptTouchEvent(ev);
    }
}
