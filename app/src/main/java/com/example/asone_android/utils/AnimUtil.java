package com.example.asone_android.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asone_android.app.Constant;


public class AnimUtil {

    public static void setAphaTextView(View view){

    }

    /**
     * 图片文字平移动画
     */
    public static void scaleX(ImageView imageView, float nowSite, float nowAllNum) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, View.TRANSLATION_X, nowSite, nowAllNum);
        animator.setDuration(Constant.SPACE_TIME);
        animator.start();
    }

    public static void scaleX(TextView imageView, float nowSite, float nowAllNum) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, View.TRANSLATION_X, nowSite, nowAllNum);
        animator.setDuration(Constant.SPACE_TIME);
        animator.start();
    }

    /**
     * 图片文字透明动画
     */
    public static void setAlphaAnimText(TextView imageView,int Alltime) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, View.ALPHA, 0f, 1f);
        animator.setDuration(Alltime/4);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(imageView, View.ALPHA, 1f, 1f);
        animator2.setDuration(Alltime/2);

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(imageView, View.ALPHA, 1f, 0f);
        animator1.setDuration(Alltime/4);

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator,animator2,animator1);
        set.start();
    }
}
