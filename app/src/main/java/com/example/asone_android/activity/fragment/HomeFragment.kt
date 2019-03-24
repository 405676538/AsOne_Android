package com.example.asone_android.activity.fragment

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
import com.example.asone_android.bean.Artist
import com.example.asone_android.bean.EventBusMessage
import com.example.asone_android.bean.MusicAlbum
import com.example.asone_android.bean.MusicAlbumInfo
import com.example.asone_android.net.MusicPresenter
import com.example.asone_android.utils.ACache
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
            EventBus.getDefault().post(EventBusMessage(EventBusMessage.ADD_ALL_ARTIST_FRAGMENT)) //添加所有up fragment页面
        }

        tv_all_house.setOnClickListener {
            EventBus.getDefault().post(EventBusMessage(EventBusMessage.ADD_ALL_HOUSE_FRAGMENT))
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d(TAG,"onHiddenChanged")
    }

    override fun onRefresh() {
        presenter.getMusicAlbum(this)
        presenter.getArtistList(this)
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

    override fun getArtistSuccess(artists: MutableList<Artist>?, collects: MutableList<Artist>?) {
        if (collects.isNullOrEmpty()){
            add_select.visibility = View.VISIBLE
            tv_hint_art.visibility = View.VISIBLE
        }else{
            tv_select.visibility = View.VISIBLE
            artList.clear()
            artList.addAll(collects)
            artAdapter.notifyDataSetChanged()
        }
    }

    override fun onEventMainThread(eventBusMessage: EventBusMessage?) {
        super.onEventMainThread(eventBusMessage)
        Log.d(TAG,"onEventMainThread"+eventBusMessage?.code)
        when(eventBusMessage?.code){
            EventBusMessage.CAN_SCALL_HOME ->   srlMain!!.isEnabled = true

        }
    }
}