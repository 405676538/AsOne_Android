package com.example.asone_android.adapter

import android.content.Context
import android.widget.ImageView
import com.example.asone_android.R
import com.example.asone_android.bean.Sound
import com.example.asone_android.utils.AppUtils
import com.example.asone_android.utils.ImageUtil
import com.example.asone_android.view.SwipeRefresh.BaseRecyAdapter

class SoundAdapter(context: Context, layout:Int, private val systemList:MutableList<Sound>): BaseRecyAdapter(context,layout) {

    override fun onBindViewHolder(holder: MYViewholder, position: Int) {
        var ivImg = holder.getView<ImageView>(R.id.iv_img)
        ImageUtil.GlidegetRoundFourImage(AppUtils.getDownLoadFileUrl(systemList[position].imgUrl),ivImg,30)
        holder.setText(R.id.tv_title,systemList[position].name)


    }

    override fun getItemCount(): Int {
        return systemList.size
    }

    override fun setRecyclable(): Boolean {
        return false
    }
}