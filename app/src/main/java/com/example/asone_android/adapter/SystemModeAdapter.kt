package com.example.asone_android.adapter

import android.content.Context
import com.example.asone_android.R
import com.example.asone_android.view.SwipeRefresh.BaseRecyAdapter

class SystemModeAdapter (context: Context, layout:Int, val systemList:MutableList<String>): BaseRecyAdapter(context,layout){



    override fun onBindViewHolder(holder: MYViewholder, position: Int) {
       holder.setText(R.id.tv_name,systemList[position])
    }

    override fun getItemCount(): Int {
        return systemList.size
    }

    override fun setRecyclable(): Boolean {
        return false
    }
}