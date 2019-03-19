package com.example.asone_android.activity.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.example.asone_android.Base.BaseFragment
import com.example.asone_android.R
import com.example.asone_android.adapter.HouseAdapter
import com.example.asone_android.bean.MusicAlbum
import com.example.asone_android.bean.MusicAlbumInfo
import com.example.asone_android.net.MusicPresenter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_top_bar_all.*

class HomeFragment: BaseFragment(), MusicPresenter.GetMusicAlbumView, SwipeRefreshLayout.OnRefreshListener {



    lateinit var houseAdapter:HouseAdapter
    var houseList = mutableListOf<MusicAlbum>()
    var presenter = MusicPresenter()

    override fun getLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
    }

    override fun initView() {
        tv_left.text = "主页"

        recyclerview.layoutManager = LinearLayoutManager(mContext)
        houseAdapter = HouseAdapter(mContext,R.layout.item_house,houseList)
        recyclerview.adapter = houseAdapter
        onRefresh()
        srl_main.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        presenter.getMusicAlbum(this)
    }

    override fun getAlbumSuccess(infos: MutableList<MusicAlbumInfo>?) {
        houseList.clear()
        if (infos != null) {
            for (musicAlbun in infos){
                houseList.add(musicAlbun.fields)
            }
        }
        srl_main.isRefreshing = false
        houseAdapter.notifyDataSetChanged()
    }
}