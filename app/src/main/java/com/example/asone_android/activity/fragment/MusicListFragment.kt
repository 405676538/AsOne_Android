package com.example.asone_android.activity.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.asone_android.Base.BaseFragment
import com.example.asone_android.R
import com.example.asone_android.adapter.MusicListAdapter
import com.example.asone_android.bean.Music
import com.example.asone_android.net.MusicPresenter
import com.example.asone_android.utils.AppUtils
import com.example.asone_android.utils.ImageUtil
import kotlinx.android.synthetic.main.fragment_music_list.*

/**
 * # 处理音乐列表数据 0:全部 1：openNum  2:musicLabel 3:artist  4:upTime
 * */
class MusicListFragment : BaseFragment(), MusicPresenter.GetMusicView, MusicPresenter.GetAlbumMusicListView {
    override fun getAlbumMusicListSuccess(musics: MutableList<Music>?) {
        musics?.let {
            list.clear()
            list.addAll(musics)
            adapter.notifyDataSetChanged()
        }
    }

    override fun getMusicDuccess(album: MutableList<Music>?) {
        album?.let {
            list.clear()
            list.addAll(album)
            adapter.notifyDataSetChanged()
        }

    }

    companion object {
        val type = "type"
        val query = "query"
        var mainTitle = "mainTitle"
        val img = "img"
    }

    var type = 0
    var query = ""
    var maintitle = ""
    var img = ""

    val presenter = MusicPresenter()
    var list = mutableListOf<Music>()
    lateinit var adapter: MusicListAdapter

    override fun getLayout(): Int {
        return R.layout.fragment_music_list
    }

    override fun initData() {
        recyclerview.layoutManager = LinearLayoutManager(mContext)
        adapter = MusicListAdapter(mContext,R.layout.item_music_list,list)
        recyclerview.adapter = adapter


    }

    override fun initView() {
        type = arguments?.getInt(MusicListFragment.type)!!
        query = arguments?.getString(MusicListFragment.query)!!
        maintitle = arguments?.getString(MusicListFragment.mainTitle)!!
        img = arguments?.getString(MusicListFragment.img)!!
        logi("type====="+type)
        logi("query====="+query)
        logi("maintitle====="+maintitle)
        logi("img====="+img)

        toolbar.title = maintitle
        if (maintitle.isEmpty()){
            toolbar.title = "音乐"
        }
        ImageUtil.GlideImage(AppUtils.getDownLoadFileUrl(img),iv_main_img)
        if (type == 5){
            presenter.getAlbumMusicList(query,this)
        }else{
            presenter.getAlbumMusic(type,query,this)
        }
    }
}