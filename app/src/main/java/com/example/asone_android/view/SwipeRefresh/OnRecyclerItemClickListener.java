package com.example.asone_android.view.SwipeRefresh;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ningpeng on 16/11/29.
 * 使用方法  recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView)
 */

public abstract class OnRecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "OnRecyclerItemClickList";
    private GestureDetectorCompat mGestureDetector;
    private RecyclerView recyclerView;

    public OnRecyclerItemClickListener(final RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null) {
                    RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(childView);
                    onItemClick(vh);
                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null) {
                    RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(childView);
                    onItemLongClick(vh);
                }
            }
        });

    }




//    点击事件交给mGestureDetector处理
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        super.onTouchEvent(rv, e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.onRequestDisallowInterceptTouchEvent(disallowIntercept);
    }



    //点击回掉
    public abstract void onItemClick(RecyclerView.ViewHolder vh);
    //长按监听
    public abstract void onItemLongClick(RecyclerView.ViewHolder vh);
}
