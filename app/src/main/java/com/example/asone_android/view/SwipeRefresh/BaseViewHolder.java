package com.example.asone_android.view.SwipeRefresh;

/**
 * Created by ningpeng on 16/11/30.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashSet;
import java.util.LinkedHashSet;


class BaseViewHolder extends RecyclerView.ViewHolder {

    /**
     * 使用LinkedHashSet 代表可序列化的id 集合
     * SparseArray类似于HasMap的集合。
     */
    private final SparseArray<View> views;

    private final LinkedHashSet<Integer> childClickViewIds;
    private final LinkedHashSet<Integer> itemChildLongClickViewIds;


    public View convertView;

    /**
     * 提供一个Object类 用来保留关联对象的改变。虽然并没有什么卵用
     */
    Object associatedObject;


    public BaseViewHolder(View view) {
        super(view);
        this.views = new SparseArray<View>();
        this.childClickViewIds = new LinkedHashSet<>();
        this.itemChildLongClickViewIds = new LinkedHashSet<>();
        convertView = view;

    }

    public HashSet<Integer> getItemChildLongClickViewIds() {
        return itemChildLongClickViewIds;
    }

    public HashSet<Integer> getChildClickViewIds() {
        return  childClickViewIds;
    }

    public View getConvertView() {

        return convertView;
    }

    /**
     *在这儿给 Textview类型的控件赋值并找到id
     */
    public BaseViewHolder setText(int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);
        return this;
    }

    public BaseViewHolder setText(int viewId, @StringRes int strId) {
        TextView view = getView(viewId);
        view.setText(strId);
        return this;
    }

    /**
     *
     *在这儿给Imageview类型的控件设置背景并找到id
     */


    public BaseViewHolder setImageResource(int viewId,@DrawableRes int imageResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }


    /**
     * 在这儿给view类型的控件设置背景颜色并找到id
     */
    public BaseViewHolder setBackgroundColor(int viewId, int color, Context context) {
        View view = getView(viewId);
        view.setBackgroundResource(color);
        return this;
    }

    /**
     * 在这儿给view类型的控件设置背景颜色并找到id
     */
    public BaseViewHolder setBackgroundRes(int viewId, @DrawableRes int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    /**
     *  设置 TextView 颜色
     */
    public BaseViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }


    /**
     * 设置Imageview src属性 drawble
     */
    public BaseViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    /**
     * 设置Imageview src属性 bitmap
     */
    public BaseViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置view的 透明度  0 到 1
     */
    public BaseViewHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    /**
     *设置view 是否可见
     */
    public BaseViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     *设置view的 超链接属性
     */
    public BaseViewHolder linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    /**
     * 设置TextView 的字体为超链接
     */
    public BaseViewHolder setTypeface(int viewId, Typeface typeface) {
        TextView view = getView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    /**
     * 设置TextView 的字体为超链接
     */
    public BaseViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }


    /**
     * 控件点击事件
     */
    @Deprecated
    public BaseViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * 添加子view 的id 到点击事件id数组里面
     */
    public BaseViewHolder addOnClickListener(int viewId) {
        childClickViewIds.add(viewId);
        return this;
    }

    /**
     * 添加子view 的id 到长按事件id数组里面
     */
    public BaseViewHolder addOnLongClickListener(int viewId){
        itemChildLongClickViewIds.add(viewId);
        return this;
    }


    /**
     * view的触摸事件
     */
    public BaseViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    /**
     * view的长按事件
     */
    public BaseViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    /**
     *
     */
    public BaseViewHolder setOnItemClickListener(int viewId, AdapterView.OnItemClickListener listener) {
        AdapterView view = getView(viewId);
        view.setOnItemClickListener(listener);
        return this;
    }


    /**
     * checkBox点击事件
     */
    public BaseViewHolder setOnCheckedChangeListener(int viewId, CheckBox.OnCheckedChangeListener listener) {
        CheckBox view = getView(viewId);
        view.setOnCheckedChangeListener(listener);
        return this;
    }

    /**
     * set Tag 无标记
     */
    public BaseViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    /**
     * 设置tag有标记
     */
    public BaseViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    /**
     * checkBox的状态
     */
    public BaseViewHolder setChecked(int viewId, boolean checked) {
        View view = getView(viewId);
        // View unable cast to Checkable
        if (view instanceof CheckBox) {
            ((CheckBox) view).setChecked(checked);
        }
        return this;
    }



    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * Retrieves the last converted object on this view.
     */
    public Object getAssociatedObject() {
        return associatedObject;
    }

    /**
     * Should be called during convert
     */
    public void setAssociatedObject(Object associatedObject) {
        this.associatedObject = associatedObject;
    }



}