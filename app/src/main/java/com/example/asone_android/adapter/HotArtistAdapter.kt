package com.example.asone_android.adapter

import android.content.Context
import com.example.asone_android.R
import com.example.asone_android.bean.Artist
import com.example.asone_android.bean.MusicAlbum
import com.example.asone_android.view.SwipeRefresh.BaseRecyAdapter
import kotlinx.android.synthetic.main.activity_hot.view.*

class HotArtistAdapter(context: Context, layout:Int, private val systemList:MutableList<Artist>): BaseRecyAdapter(context,layout){
    override fun onBindViewHolder(holder: MYViewholder, position: Int) {
        holder.setText(R.id.tv_name,systemList[position].name)
        holder.setText(R.id.tv_hot,systemList[position].hotNum)

    }

    override fun getItemCount(): Int {
        return systemList.size
    }

    override fun setRecyclable(): Boolean {
        return false
    }
}