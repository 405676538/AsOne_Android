package com.example.asone_android.activity.fragment

import android.support.v7.widget.LinearLayoutManager
import com.example.asone_android.Base.BaseFragment
import com.example.asone_android.R
import com.example.asone_android.adapter.HouseAdapter
import com.example.asone_android.bean.MusicAlbum
import com.example.asone_android.bean.MusicAlbumInfo
import com.example.asone_android.net.MusicPresenter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: BaseFragment(), MusicPresenter.GetMusicAlbumView {


    lateinit var houseAdapter:HouseAdapter
    var houseList = mutableListOf<MusicAlbum>()
    var presenter = MusicPresenter()

    override fun getLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
    }

    override fun initView() {
        recyclerview.layoutManager = LinearLayoutManager(mContext)
        houseAdapter = HouseAdapter(mContext,R.layout.item_house,houseList)
        recyclerview.adapter = houseAdapter
        presenter.getMusicAlbum(this)
    }

    override fun getAlbumSuccess(infos: MutableList<MusicAlbumInfo>?) {
        houseList.clear()
        if (infos != null) {
            for (musicAlbun in infos){
                houseList.add(musicAlbun.fields)
            }
        }
        houseAdapter.notifyDataSetChanged()
    }
}