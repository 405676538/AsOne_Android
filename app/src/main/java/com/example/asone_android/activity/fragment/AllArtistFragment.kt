package com.example.asone_android.activity.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.asone_android.Base.BaseFragment
import com.example.asone_android.R
import com.example.asone_android.adapter.AllArtistAdapter
import com.example.asone_android.bean.Artist
import com.example.asone_android.bean.EventBusMessage
import com.example.asone_android.net.MusicPresenter
import kotlinx.android.synthetic.main.fragment_all_artist.*
import kotlinx.android.synthetic.main.include_top_bar_all.*
import org.greenrobot.eventbus.EventBus

class AllArtistFragment:BaseFragment(), MusicPresenter.GetArtistView {

    companion object {
        val filter = "typeContent"
        val type = "type"
    }

    lateinit var allArtistAdapter: AllArtistAdapter
    var artList = mutableListOf<Artist>()
    var presenter = MusicPresenter()
    var type = -1
    var typeContent = ""

    override fun getLayout(): Int {
        return R.layout.fragment_all_artist
    }


    override fun initData() {
        recyclerview.layoutManager = LinearLayoutManager(mContext)
        allArtistAdapter = AllArtistAdapter(mContext,R.layout.item_all_artist,artList)
        recyclerview.adapter = allArtistAdapter
        presenter.getArtistList(this,type,typeContent)

        allArtistAdapter.setOnItemClickListener { view, position ->
            val buddle = Bundle()
            buddle.putInt(MusicListFragment.type,3)
            buddle.putString(MusicListFragment.query,artList[position].name)
            buddle.putString(MusicListFragment.mainTitle,artList[position].brief)
            buddle.putString(MusicListFragment.img,artList[position].head)
            EventBus.getDefault().post(
                    EventBusMessage(EventBusMessage.ADD_MUSIC_LIST,buddle))
        }
    }

    override fun initView() {
        tv_left.text = "所有艺术家"
        type = arguments?.getInt(AllArtistFragment.type)!!
        typeContent = arguments?.getString(AllArtistFragment.filter)!!
        if (!typeContent.isNullOrEmpty()){
            tv_left.text = typeContent+"艺术家"
        }
    }

    override fun getArtistSuccess(artists: MutableList<Artist>?) {
        artists?.let {
            artList.clear()
            artList.addAll(artists)
            allArtistAdapter.notifyDataSetChanged()
        }
    }

}