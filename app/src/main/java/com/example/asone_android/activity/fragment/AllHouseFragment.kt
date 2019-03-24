package com.example.asone_android.activity.fragment

import android.support.v7.widget.LinearLayoutManager
import com.example.asone_android.Base.BaseFragment
import com.example.asone_android.R
import com.example.asone_android.adapter.AllHouseAdapter
import com.example.asone_android.bean.MusicAlbum
import com.example.asone_android.bean.MusicAlbumInfo
import com.example.asone_android.net.MusicPresenter
import kotlinx.android.synthetic.main.fragment_house_all.*
import kotlinx.android.synthetic.main.include_top_bar_all.*

class AllHouseFragment: BaseFragment(), MusicPresenter.GetMusicAlbumView {


    val TAG = "AllHouseFragment"
    lateinit var adapter:AllHouseAdapter
    var houseList = mutableListOf<MusicAlbum>()
    var presenter = MusicPresenter()

    override fun getLayout(): Int {
        return R.layout.fragment_house_all
    }

    override fun initData() {
        recyclerview.layoutManager = LinearLayoutManager(mContext)
        adapter = AllHouseAdapter(mContext,R.layout.item_all_house,houseList)
        recyclerview.adapter = adapter
        presenter.getMusicAlbum(this)
    }

    override fun initView() {
        tv_left.text = "全部合集"
    }

    override fun getAlbumSuccess(infos: MutableList<MusicAlbumInfo>?) {
        infos?.let {
            for (musicAlbun in infos) {
                houseList.add(musicAlbun.fields)
            }
            adapter.notifyDataSetChanged()
        }
    }
}