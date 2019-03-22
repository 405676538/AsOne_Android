package com.example.asone_android.adapter

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.example.asone_android.R
import com.example.asone_android.app.BaseApplication
import com.example.asone_android.bean.Artist
import com.example.asone_android.bean.Country
import com.example.asone_android.net.MusicPresenter
import com.example.asone_android.utils.ACache
import com.example.asone_android.utils.AppUtils
import com.example.asone_android.utils.ImageUtil
import com.example.asone_android.view.SwipeRefresh.BaseRecyAdapter

class AllArtistAdapter(context: Context, layout:Int, private val systemList:MutableList<Artist>): BaseRecyAdapter(context,layout) {

    var presenter = MusicPresenter()

    init {

    }

    override fun onBindViewHolder(holder: MYViewholder, position: Int) {
        var ivImg = holder.getView<ImageView>(R.id.iv_img)
        var ivConlection = holder.getView<ImageView>(R.id.iv_conlection)
        ImageUtil.GlidegetRoundImage(AppUtils.getDownLoadFileUrl(systemList[position].head),ivImg)
        holder.setText(R.id.tv_name,systemList[position].name)
        ivConlection.setOnClickListener {
            var uid = ACache.get().getAsString(ACache.TAG_USER_ID)
            if (uid.isNullOrEmpty()){
                AppUtils.goLogin()
            }else{
                showToast("删除或增加")
            }
        }

    }

    override fun getItemCount(): Int {
        return systemList.size
    }

    override fun setRecyclable(): Boolean {
        return false
    }
}