package com.example.asone_android.activity.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.example.asone_android.Base.BaseFragment
import com.example.asone_android.R
import com.example.asone_android.adapter.AllHouseAdapter
import com.example.asone_android.bean.EventBusMessage
import com.example.asone_android.bean.MusicAlbum
import com.example.asone_android.bean.MusicAlbumInfo
import com.example.asone_android.net.MusicPresenter
import kotlinx.android.synthetic.main.fragment_house_all.*
import kotlinx.android.synthetic.main.include_top_bar_all.*
import org.greenrobot.eventbus.EventBus

class AllHouseFragment: BaseFragment(), MusicPresenter.GetMusicAlbumView, SwipeRefreshLayout.OnRefreshListener {
    override fun onRefresh() {
        presenter.getMusicAlbum(this)
    }


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
        srl_main.setOnRefreshListener(this)
        onRefresh()
        adapter.setOnItemClickListener { view, position ->
            var buddle = Bundle()
            buddle.putInt(MusicListFragment.type,5)
            buddle.putString(MusicListFragment.query,houseList[position].albumId)
            buddle.putString(MusicListFragment.mainTitle,houseList[position].title)
            buddle.putString(MusicListFragment.img,houseList[position].imgUrl)
            EventBus.getDefault().post(
                    EventBusMessage(EventBusMessage.ADD_MUSIC_LIST,buddle)
            )
        }
    }

    override fun initView() {
        tv_left.text = "全部合集"
    }

    override fun getAlbumSuccess(infos: MutableList<MusicAlbumInfo>?) {
        infos?.let {
            for (musicAlbun in infos) {
                houseList.clear()
                houseList.add(musicAlbun.fields)
            }
            srl_main.isRefreshing = false
            adapter.notifyDataSetChanged()
        }
    }
}