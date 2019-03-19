package com.example.asone_android.adapter

import android.content.Context
import android.widget.ImageView
import com.example.asone_android.R
import com.example.asone_android.bean.Artist
import com.example.asone_android.utils.AppUtils
import com.example.asone_android.utils.ImageUtil
import com.example.asone_android.view.SwipeRefresh.BaseRecyAdapter

class ArtistAdapter(context: Context, layout:Int, private val systemList:MutableList<Artist>): BaseRecyAdapter(context,layout) {

    override fun onBindViewHolder(holder: MYViewholder, position: Int) {
        var ivimg = holder.getView<ImageView>(R.id.iv_img)
        holder.setText(R.id.tv_name,systemList[position].name)
        ImageUtil.GlidegetRoundImage(AppUtils.getDownLoadFileUrl(systemList[position].head),ivimg)

    }

    override fun getItemCount(): Int {
       return systemList.size
    }

    override fun setRecyclable(): Boolean {
        return false
    }

}