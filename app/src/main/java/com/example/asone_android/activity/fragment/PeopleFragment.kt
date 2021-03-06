package com.example.asone_android.activity.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.asone_android.Base.BaseFragment
import com.example.asone_android.R
import com.example.asone_android.adapter.ArtistAdapter
import com.example.asone_android.adapter.CountryAdapter
import com.example.asone_android.bean.Artist
import com.example.asone_android.bean.Country
import com.example.asone_android.bean.EventBusMessage
import com.example.asone_android.net.MusicPresenter
import com.example.asone_android.view.ScrollLinearLayoutManager
import com.example.asone_android.view.SwipeRefresh.BaseRecyAdapter
import kotlinx.android.synthetic.main.fragment_people.*
import kotlinx.android.synthetic.main.include_top_bar_all.*
import org.greenrobot.eventbus.EventBus

/**
 * 约定 后端过滤  type
 * name = 1
 *  age = 2
 *  six = 3
 * country = 4
 * recommend = 5
 *
 *
 * */

class PeopleFragment: BaseFragment(), SwipeRefreshLayout.OnRefreshListener, MusicPresenter.GetArtistView, MusicPresenter.GetCountryView {


    var presenter = MusicPresenter()
    lateinit var artistAdapter:ArtistAdapter
    lateinit var countryAdapter: CountryAdapter
    var artistList = mutableListOf<Artist>()
    var countryList = mutableListOf<Country>()


    override fun getLayout(): Int {
        return R.layout.fragment_people
    }

    override fun initData() {
        rcl_artist.layoutManager = GridLayoutManager(mContext,3) as RecyclerView.LayoutManager?
        artistAdapter = ArtistAdapter(mContext,R.layout.item_artist,artistList)
        rcl_artist.adapter = artistAdapter


        rcl_country.layoutManager = LinearLayoutManager(mContext)
        countryAdapter = CountryAdapter(mContext,R.layout.item_people_country,countryList)
        rcl_country.adapter = countryAdapter
        rcl_country.setHasFixedSize(true)
        rcl_country.isNestedScrollingEnabled = false
        srl_main.setOnRefreshListener(this)
        onRefresh()

        countryAdapter.setOnItemClickListener { view, position ->
            EventBus.getDefault().post(EventBusMessage
            (EventBusMessage.ADD_ALL_ARTIST_FRAGMENT,4,countryList[position].name))
        }

        artistAdapter.setOnItemClickListener{view, position ->
            val buddle = Bundle()
            buddle.putInt(MusicListFragment.type,3)
            buddle.putString(MusicListFragment.query,artistList[position].name)
            buddle.putString(MusicListFragment.mainTitle,artistList[position].brief)
            buddle.putString(MusicListFragment.img,artistList[position].head)
            EventBus.getDefault().post(
                    EventBusMessage(EventBusMessage.ADD_MUSIC_LIST,buddle))
        }
    }

    override fun initView() {
        tv_left.text = "艺术家"
        tv_show_all.setOnClickListener {
            EventBus.getDefault().post(EventBusMessage
            (EventBusMessage.ADD_ALL_ARTIST_FRAGMENT,0,""))
        }
    }

    override fun onRefresh() {
        presenter.getArtistList(this,7,"")
        presenter.getCountry(this)
    }

    override fun getArtistSuccess(artists: MutableList<Artist>?) {
        artistList.clear()
        artists?.let {
            for (position in artists.indices){
                if (position <= 5){
                    artistList.add(artists[position])
                }
            }
        }
        artistAdapter.notifyDataSetChanged()
        srl_main.isRefreshing = false
    }

    override fun GetCountrySuccess(countries: MutableList<Country>?) {
        countryList.clear()
        countries?.let {
            countryList.addAll(countries)
        }
        countryAdapter.notifyDataSetChanged()
        srl_main.isRefreshing = false
    }


}