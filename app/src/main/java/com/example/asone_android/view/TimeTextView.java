package com.example.asone_android.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TimeTextView extends AppCompatTextView {

    int secondTime = 0;
    Handler handler;

    public TimeTextView(Context context) {
        super(context);
        init();
    }

    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        handler = new Handler(Looper.getMainLooper());
    }

    public  void startRun() {
        handler.post(runnable);
    }

    void setTimeText() {
        int hour = secondTime/3600;
        int minite = secondTime / 60%60;
        int second = secondTime % 60;
        String h;
        String min;
        String sec;
        if (hour < 10) {
            h = "0"+hour;
        } else {
            h = hour+"";
        }
        if (minite < 10) {
            min = "0" + minite;
        } else {
            min = minite + "";
        }

        if (second < 10) {
            sec = "0" + second;
        } else {
            sec = second + "";
        }
        setText(h +":"+min + ":" + sec);
    }

    Runnable runnable = () -> {
        secondTime++;
        setTimeText();
        handler.postDelayed(this::startRun, 1000);
    };

    public void stopRun() {
        handler.removeCallbacks(runnable);
        handler.removeCallbacksAndMessages(null);
    }

    public void stopRunAndInit() {
        handler.removeCallbacks(runnable);
        handler.removeCallbacksAndMessages(null);
        setSecondTime(0);
    }

    public int getSecondTime() {
        return secondTime;
    }

    public void setSecondTime(int secondTime) {
        this.secondTime = secondTime;
    }
}
