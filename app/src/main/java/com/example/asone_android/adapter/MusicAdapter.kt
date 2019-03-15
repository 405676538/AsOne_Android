package com.example.asone_android.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.asone_android.R
import com.example.asone_android.app.Constant
import com.example.asone_android.bean.EventBusMessage
import com.example.asone_android.bean.Music
import com.example.asone_android.utils.AppUtils
import com.example.asone_android.utils.ExoUtils
import com.example.asone_android.view.SwipeRefresh.BaseRecyAdapter
import com.google.android.exoplayer2.ExoPlayer
import org.greenrobot.eventbus.EventBus

class MusicAdapter(context: Context, layout:Int, private val systemList:MutableList<Music>): BaseRecyAdapter(context,layout) {

    val TAG = "MusicAdapter"
    var player: ExoPlayer = ExoUtils.initPlayer(context)

    init {
        player.playWhenReady = true
    }

    override fun onBindViewHolder(holder: MYViewholder, position: Int) {
        var music = systemList[position]
        var img = holder.getView<ImageView>(R.id.iv_img)
        var title = holder.getView<TextView>(R.id.tv_title)
        var lable = holder.getView<TextView>(R.id.tv_label)
        var select = holder.getView<ImageView>(R.id.iv_select)
        var play = holder.getView<ImageView>(R.id.iv_play)
        select.visibility = if (music.selectVisible) View.VISIBLE else View.GONE
        play.visibility = if (music.playVisible) View.VISIBLE else View.GONE

        if (music.play){
            play.setImageResource(R.mipmap.play_in)
        }else{
            play.setImageResource(R.mipmap.play_none)
        }

        if (music.select){
            select.setImageResource(R.mipmap.select_in)
        }else{
            select.setImageResource(R.mipmap.select_none)
        }
//        http://192.168.100.64:8000/downLoadFile/1552642904.4751825fileId.jpg
        Glide.with(mContext).load(AppUtils.getDownLoadFileUrl(music.imgId)).into(img)
        title.text = music.title
        lable.text = music.musicLabel


        play.setOnClickListener {
            if (music.play){
                //切换到暂停音乐
                play.setImageResource(R.mipmap.play_none)
                music.play = false
                player.stop()
            }else{
                //播放音乐
                play.setImageResource(R.mipmap.play_in)
                music.play = true
                Log.d(TAG,AppUtils.getDownLoadFileUrl(music.audioId))
                player.prepare(ExoUtils.getMediaSourse(mContext, AppUtils.getDownLoadFileUrl(music.audioId)), false, true)
            }
        }

        select.setOnClickListener {
            if (music.select){
                //切换到未选择状态
                select.setImageResource(R.mipmap.select_none)
                music.select = false
            }else{
                select.setImageResource(R.mipmap.select_in)
                music.select = true
            }
            EventBus.getDefault().post(EventBusMessage(Constant.E_UPDATA_SYSTEM_MUSI))
        }

    }

    override fun getItemCount(): Int {
      return  systemList.size
    }

    override fun setRecyclable(): Boolean {
        return false
    }

    fun distory(){
        if (player != null){
            ExoUtils.releasePlayer(player)
        }
    }

}