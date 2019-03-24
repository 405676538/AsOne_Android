package com.example.asone_android.adapter

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.example.asone_android.Base.BaseJson
import com.example.asone_android.R
import com.example.asone_android.app.BaseApplication
import com.example.asone_android.bean.Artist
import com.example.asone_android.bean.Country
import com.example.asone_android.net.MusicPresenter
import com.example.asone_android.utils.ACache
import com.example.asone_android.utils.AppUtils
import com.example.asone_android.utils.ImageUtil
import com.example.asone_android.view.SwipeRefresh.BaseRecyAdapter

class AllArtistAdapter(context: Context, layout:Int, private val systemList:MutableList<Artist>): BaseRecyAdapter(context,layout), MusicPresenter.AddCollectView, MusicPresenter.DeleteCollectView {
    override fun addCollectSuccess(baseJson: BaseJson?) {
        showToast("收藏")
    }

    override fun deleteCollectSccess(baseJson: BaseJson?) {
        showToast("取消收藏")
    }

    var presenter = MusicPresenter()

    init {

    }

    override fun onBindViewHolder(holder: MYViewholder, position: Int) {
        var ivImg = holder.getView<ImageView>(R.id.iv_img)
        var ivConlection = holder.getView<ImageView>(R.id.iv_conlection)
        ImageUtil.GlidegetRoundImage(AppUtils.getDownLoadFileUrl(systemList[position].head),ivImg)
        holder.setText(R.id.tv_name,systemList[position].name)
        ivConlection.setImageResource(R.mipmap.collection_none)
        if (systemList[position].collect){
            ivConlection.setImageResource(R.mipmap.conlection_y)
        }
        ivConlection.setOnClickListener {
            val uid = ACache.get().getAsString(ACache.TAG_USER_ID)
            if (uid.isNullOrEmpty()){
                AppUtils.goLogin()
            }else{
                if (systemList[position].collect){
                    systemList[position].collect = false
                    ivConlection.setImageResource(R.mipmap.collection_none)
                    presenter.deleteCollect(systemList[position].upId,this)
                }else{
                    systemList[position].collect = true
                    ivConlection.setImageResource(R.mipmap.conlection_y)
                    presenter.addCollect(systemList[position].upId,this)
                }
            }
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return systemList.size
    }

    override fun setRecyclable(): Boolean {
        return false
    }
}