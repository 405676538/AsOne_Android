package com.example.asone_android.activity.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.example.asone_android.Base.BaseFragment
import com.example.asone_android.R
import com.example.asone_android.adapter.SoundAdapter
import com.example.asone_android.bean.EventBusMessage
import com.example.asone_android.bean.Sound
import com.example.asone_android.net.MusicPresenter
import kotlinx.android.synthetic.main.fragment_voice.*
import kotlinx.android.synthetic.main.include_top_bar_all.*
import org.greenrobot.eventbus.EventBus

class VoiceFragment: BaseFragment(), SwipeRefreshLayout.OnRefreshListener, MusicPresenter.GetSoundTypeView {



    lateinit var soundAdapter:SoundAdapter
    var musicPresenter = MusicPresenter()
    var soundlist = mutableListOf<Sound>()

    override fun getLayout(): Int {
        return R.layout.fragment_voice
    }

    override fun initData() {
        recyclerview.layoutManager = GridLayoutManager(mContext,2)
        soundAdapter = SoundAdapter(mContext,R.layout.item_sound,soundlist)
        recyclerview.adapter = soundAdapter
        srl_main.setOnRefreshListener(this)
        onRefresh()
        soundAdapter.setOnItemClickListener { view, position ->
            val buddle = Bundle()
            buddle.putInt(MusicListFragment.type,2)
            buddle.putString(MusicListFragment.query,soundlist[position].name)
            buddle.putString(MusicListFragment.mainTitle,soundlist[position].name)
            buddle.putString(MusicListFragment.img,soundlist[position].imgUrl)
            EventBus.getDefault().post(
                    EventBusMessage(EventBusMessage.ADD_MUSIC_LIST,buddle))
        }
    }

    override fun initView() {
        tv_left.text = "标签分类"
    }

    override fun onRefresh() {
        musicPresenter.getSoundTypeList(this)
    }

    override fun getSoundTypoeSuccess(sounds: MutableList<Sound>?) {
        sounds?.let {
            soundlist.clear()
            soundlist.addAll(sounds)
            soundAdapter.notifyDataSetChanged()
        }
        srl_main.isRefreshing = false
    }
}