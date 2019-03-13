package com.example.asone_android.view.SwipeRefresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * Created by ningpeng on 16/11/30.
 *
 */

public abstract  class BaseRecyAdapter extends RecyclerView.Adapter<BaseRecyAdapter.MYViewholder> implements View.OnClickListener {

    public Context mContext;
    public int layoutId;
    private OnItemClickListener mOnItemClickListener = null;

    public BaseRecyAdapter(Context context, int layoutId) {
        this.mContext = context;
        this.layoutId = layoutId;
    }

    public View onBaseCreateViewHolder(ViewGroup parent, Context context, int layoutId){
        View view = LayoutInflater.from(context).inflate(layoutId,parent,false);
        return view;
    }

    @Override
    public MYViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = onBaseCreateViewHolder(parent,mContext,layoutId);
        MYViewholder myViewholder = new MYViewholder(view);
        myViewholder.setIsRecyclable(setRecyclable());
        view.setOnClickListener(this);
        return myViewholder;
    }

    @Override
    public void onBindViewHolder(MYViewholder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        holder.itemView.setTag(position);
        onBindViewHolder(holder,position);
    }

    @Override
    public abstract void onBindViewHolder(MYViewholder holder, int position);

    @Override
    public abstract int getItemCount();

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }


    public class MYViewholder extends BaseViewHolder {


        public MYViewholder(View view) {
            super(view);
        }
    }


    /*****
     *
     * 必要的传入参数
     * ***/
    public abstract boolean setRecyclable();

    public  interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }



}
