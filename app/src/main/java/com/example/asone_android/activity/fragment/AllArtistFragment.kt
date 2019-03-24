package com.example.asone_android.activity.fragment

import android.support.v7.widget.LinearLayoutManager
import com.example.asone_android.Base.BaseFragment
import com.example.asone_android.R
import com.example.asone_android.adapter.AllArtistAdapter
import com.example.asone_android.bean.Artist
import com.example.asone_android.net.MusicPresenter
import kotlinx.android.synthetic.main.fragment_all_artist.*
import kotlinx.android.synthetic.main.include_top_bar_all.*

class AllArtistFragment:BaseFragment(), MusicPresenter.GetArtistView {

    lateinit var allArtistAdapter: AllArtistAdapter
    var artList = mutableListOf<Artist>()
    var presenter = MusicPresenter()

    override fun getLayout(): Int {
        return R.layout.fragment_all_artist
    }

    override fun initData() {
        tv_left.text = "所有艺术家"
    }

    override fun initView() {
        recyclerview.layoutManager = LinearLayoutManager(mContext)
        allArtistAdapter = AllArtistAdapter(mContext,R.layout.item_all_artist,artList)
        recyclerview.adapter = allArtistAdapter
        presenter.getArtistList(this)
    }

    override fun getArtistSuccess(artists: MutableList<Artist>?,collects: MutableList<Artist>?) {
        artists?.let {
            artList.clear()
            artList.addAll(artists)
            allArtistAdapter.notifyDataSetChanged()
        }
    }

}