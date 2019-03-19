package com.example.asone_android.activity.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.asone_android.Base.BaseFragment
import com.example.asone_android.R
import com.example.asone_android.adapter.ArtistAdapter
import com.example.asone_android.adapter.CountryAdapter
import com.example.asone_android.bean.Artist
import com.example.asone_android.bean.Country
import com.example.asone_android.net.MusicPresenter
import kotlinx.android.synthetic.main.fragment_people.*

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
        rcl_artist.layoutManager = GridLayoutManager(mContext,2)
        artistAdapter = ArtistAdapter(mContext,R.layout.item_artist,artistList)
        rcl_artist.adapter = artistAdapter

        rcl_country.layoutManager = LinearLayoutManager(mContext)
        countryAdapter = CountryAdapter(mContext,R.layout.item_people_country,countryList)
        rcl_country.adapter = countryAdapter

        srl_main.setOnRefreshListener(this)
        onRefresh()
    }

    override fun initView() {

    }

    override fun onRefresh() {
        presenter.getArtistList(this)
        presenter.getCountry(this)
    }

    override fun getArtistSuccess(artists: MutableList<Artist>?) {
        artistList.clear()
        artists?.let {
            artistList.addAll(artists)
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