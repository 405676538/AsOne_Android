package com.example.asone_android.adapter

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.asone_android.R
import com.example.asone_android.bean.Music
import com.example.asone_android.bean.MusicAlbum
import com.example.asone_android.utils.AppUtils
import com.example.asone_android.utils.ImageUtil
import com.example.asone_android.view.SwipeRefresh.BaseRecyAdapter
import kotlinx.android.synthetic.main.item_house.view.*

class HouseAdapter(context: Context, layout:Int, private val systemList:MutableList<MusicAlbum>): BaseRecyAdapter(context,layout) {
    override fun onBindViewHolder(holder: MYViewholder, position: Int) {
        holder.setText(R.id.tv_title,systemList[position].title)
        var ivImg = holder.getView<ImageView>(R.id.iv_img)
        ImageUtil.GlidegetRoundFourImage(AppUtils.getDownLoadFileUrl(systemList[position].imgUrl),ivImg)
    }

    override fun getItemCount(): Int {
        return systemList.size
    }

    override fun setRecyclable(): Boolean {
        return false
    }
}