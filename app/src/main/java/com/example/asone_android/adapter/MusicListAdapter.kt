package com.example.asone_android.adapter

import android.content.Context
import android.widget.TextView
import com.example.asone_android.R
import com.example.asone_android.bean.Music
import com.example.asone_android.view.SwipeRefresh.BaseRecyAdapter
import kotlinx.android.synthetic.main.item_music_list.view.*

class MusicListAdapter(context: Context, layout:Int, private val systemList:MutableList<Music>): BaseRecyAdapter(context,layout)  {
    override fun onBindViewHolder(holder: MYViewholder, position: Int) {
        var tvnum = holder.getView<TextView>(R.id.tv_num)
        var tvTitle = holder.getView<TextView>(R.id.tv_title)
        var tvContent = holder.getView<TextView>(R.id.tv_content)
        tvnum.text = (position+1).toString()
        tvTitle.text = systemList[position].title
        tvContent.text = systemList[position].musicLabel

    }

    override fun getItemCount(): Int {
        return systemList.size
    }

    override fun setRecyclable(): Boolean {
        return false
    }
}