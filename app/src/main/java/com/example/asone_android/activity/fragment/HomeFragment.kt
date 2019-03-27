package com.example.asone_android.activity.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.example.asone_android.Base.BaseFragment
import com.example.asone_android.R
import com.example.asone_android.adapter.HomeArcAdapter
import com.example.asone_android.adapter.HouseAdapter
import com.example.asone_android.app.Constant
import com.example.asone_android.bean.*
import com.example.asone_android.net.MusicPresenter
import com.example.asone_android.utils.ACache
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_top_bar_all.*
import org.greenrobot.eventbus.EventBus

class HomeFragment : BaseFragment(), MusicPresenter.GetMusicAlbumView, SwipeRefreshLayout.OnRefreshListener, MusicPresenter.GetArtistView {


    val TAG = "HomeFragment"


    lateinit var houseAdapter: HouseAdapter
    lateinit var artAdapter:HomeArcAdapter
    var houseList = mutableListOf<MusicAlbum>()
    var artList = mutableListOf<Artist>()
    var presenter = MusicPresenter()
    var isBeginScoll = false
    var srlMain: SwipeRefreshLayout? = null
    var albumPosition = 0

    override fun getLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
    }

    override fun initView() {
        tv_left.text = "主页"
        srlMain = srl_main
        recy_art_list.layoutManager = LinearLayoutManager(mContext)
        artAdapter = HomeArcAdapter(mContext,R.layout.item_home_artist,artList)
        recy_art_list.adapter = artAdapter
        recy_art_list.setHasFixedSize(true)
        recy_art_list.isNestedScrollingEnabled = false

        recyclerview.layoutManager = LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
        houseAdapter = HouseAdapter(mContext, R.layout.item_house, houseList)
        recyclerview.adapter = houseAdapter

        onRefresh()
        srl_main.setOnRefreshListener(this)
        recyclerview.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                Log.d(TAG, "onScrolled")
                if (isBeginScoll) {
                    srl_main.isEnabled = false
                } else {
                    isBeginScoll = true
                }
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                Log.d(TAG, "onS|crollStateChanged")
                srl_main.isEnabled = true
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        add_select.setOnClickListener {
            EventBus.getDefault().post(EventBusMessage(EventBusMessage.ADD_ALL_ARTIST_FRAGMENT,0,"")) //添加所有up fragment页面
        }

        tv_all_house.setOnClickListener {
            EventBus.getDefault().post(EventBusMessage(EventBusMessage.ADD_ALL_HOUSE_FRAGMENT))
        }

        artAdapter.setOnItemClickListener { view, position ->
            var buddle =Bundle()
            buddle.putInt(MusicListFragment.type,3)
            buddle.putString(MusicListFragment.query,artList[position].name)
            buddle.putString(MusicListFragment.mainTitle,artList[position].brief)
            buddle.putString(MusicListFragment.img,artList[position].head)
            EventBus.getDefault().post(
                    EventBusMessage(EventBusMessage.ADD_MUSIC_LIST,buddle)
            )
        }

        houseAdapter.setOnItemClickListener { view, position ->
            var buddle =Bundle()
            buddle.putInt(MusicListFragment.type,5)
            buddle.putString(MusicListFragment.query,houseList[position].albumId)
            buddle.putString(MusicListFragment.mainTitle,houseList[position].title)
            buddle.putString(MusicListFragment.img,houseList[position].imgUrl)
            EventBus.getDefault().post(
                    EventBusMessage(EventBusMessage.ADD_MUSIC_LIST,buddle)
            )
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d(TAG,"onHiddenChanged")
    }

    override fun onRefresh() {
        presenter.getMusicAlbum(this)
        if (TextUtils.isEmpty(ACache.get().getAsString(ACache.TAG_USER_ID))){
            add_select.visibility = View.VISIBLE
            tv_hint_art.visibility = View.VISIBLE
            tv_select.visibility = View.GONE
            return
        }
        presenter.getArtistList(this,6,"")
    }

    override fun getAlbumSuccess(infos: MutableList<MusicAlbumInfo>?) {
        houseList.clear()
        if (infos != null) {
            for (musicAlbun in infos) {
                houseList.add(musicAlbun.fields)
            }
        }
        srl_main.isRefreshing = false
        houseAdapter.notifyDataSetChanged()
    }

    override fun getArtistSuccess(artists: MutableList<Artist>?) {
        if (artists.isNullOrEmpty()){
            add_select.visibility = View.VISIBLE
            tv_hint_art.visibility = View.VISIBLE
            tv_select.visibility = View.GONE
        }else{
            add_select.visibility = View.GONE
            tv_hint_art.visibility = View.GONE
            tv_select.visibility = View.VISIBLE
            artList.clear()
            artList.addAll(artists)
            artAdapter.notifyDataSetChanged()
        }
        srl_main.isRefreshing = false
    }

    override fun onEventMainThread(eventBusMessage: EventBusMessage?) {
        super.onEventMainThread(eventBusMessage)
        Log.d(TAG,"onEventMainThread"+eventBusMessage?.code)
        when(eventBusMessage?.code){
            EventBusMessage.CAN_SCALL_HOME ->   srlMain!!.isEnabled = true

        }
    }
}