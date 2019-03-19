package com.example.asone_android.adapter

import android.content.Context
import android.widget.ImageView
import com.example.asone_android.R
import com.example.asone_android.bean.Artist
import com.example.asone_android.bean.Country
import com.example.asone_android.utils.AppUtils
import com.example.asone_android.utils.ImageUtil
import com.example.asone_android.view.SwipeRefresh.BaseRecyAdapter

class CountryAdapter(context: Context, layout:Int, private val systemList:MutableList<Country>): BaseRecyAdapter(context,layout) {

    override fun onBindViewHolder(holder: MYViewholder, position: Int) {
        var ivImg = holder.getView<ImageView>(R.id.iv_img)
        ImageUtil.GlidegetRoundImage(AppUtils.getDownLoadFileUrl(systemList[position].banner),ivImg)
        holder.setText(R.id.tv_name,systemList[position].name)


    }

    override fun getItemCount(): Int {
        return systemList.size
    }

    override fun setRecyclable(): Boolean {
        return false
    }
}