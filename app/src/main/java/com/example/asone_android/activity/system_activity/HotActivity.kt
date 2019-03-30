package com.example.asone_android.activity.system_activity

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.asone_android.Base.BaseActivity
import com.example.asone_android.Base.BaseJson
import com.example.asone_android.R
import com.example.asone_android.adapter.ArtistAdapter
import com.example.asone_android.adapter.HotAlbumAdapter
import com.example.asone_android.adapter.HotArtistAdapter
import com.example.asone_android.bean.Artist
import com.example.asone_android.bean.Music
import com.example.asone_android.bean.MusicAlbum
import com.example.asone_android.bean.MusicAlbumInfo
import com.example.asone_android.net.MusicPresenter
import com.example.asone_android.net.MusicPutPresenter
import kotlinx.android.synthetic.main.activity_hot.*
import kotlinx.android.synthetic.main.include_top_bar_all.*

class HotActivity : BaseActivity(), MusicPresenter.GetMusicAlbumView, MusicPresenter.GetArtistView, MusicPutPresenter.PutAlbumView, MusicPutPresenter.PutArtistView {
    override fun putArtistSuccess(baseJson: BaseJson?) {
        showShortToast(baseJson?.fileId)
        presenter.getArtistList(this,7,"")
    }

    override fun putAlbumSuccess(baseJson: BaseJson?) {
        showShortToast(baseJson?.fileId)
        presenter.getMusicAlbum(1,this)
    }

    override fun getArtistSuccess(artists: MutableList<Artist>?) {
        artists?.let {
            artList.clear()
            artList.addAll(artists)
        }
        artAdapter.notifyDataSetChanged()
    }

    override fun getAlbumSuccess(infos: MutableList<MusicAlbumInfo>?) {
        if (infos != null) {
            albumList.clear()
            for(position in infos.indices){
                if (position <= 10){
                    albumList.add(infos[position].fields)
                }
            }
        }
        albumAdapter.notifyDataSetChanged()
    }

    lateinit var albumAdapter: HotAlbumAdapter
    lateinit var artAdapter: HotArtistAdapter
    var putPresenter = MusicPutPresenter()
    var albumList = mutableListOf<MusicAlbum>()
    var artList  = mutableListOf<Artist>()
    var presenter = MusicPresenter()
    var id = ""
    var type = 0  //0:显示集合列表 1：显示up列表

    override fun getLayout(): Int {
        return R.layout.activity_hot
    }

    override fun initData() {
        tv_left.text = "热度更改"
        btn_change.setOnClickListener {
            if (recy_artist.visibility == View.VISIBLE){
                recy_artist.visibility = View.GONE
                type = 0
            }else{
                recy_artist.visibility = View.VISIBLE
                type = 1
            }

        }
        recy_album.layoutManager = GridLayoutManager(mContext,2)
        recy_artist.layoutManager = GridLayoutManager(mContext,2)
        albumAdapter = HotAlbumAdapter(mContext,R.layout.item_hot_album,albumList)
        artAdapter = HotArtistAdapter(mContext,R.layout.item_hot_album,artList)
        recy_album.adapter = albumAdapter
        recy_artist.adapter = artAdapter

        presenter.getMusicAlbum(1,this)
        presenter.getArtistList(this,7,"")

        albumAdapter.setOnItemClickListener { view, position ->
            id = albumList.get(position).albumId
            tv_name.text = albumList[position].title
        }

        artAdapter.setOnItemClickListener { view, position ->
            id = artList[position].upId
            tv_name.text = artList[position].name
        }

        btn_hot.setOnClickListener {
            var num = et_hot.text.toString().toInt()
            if (type == 0){
                putPresenter.putAlbumHot(num,id,this)
            }else{
                putPresenter.putArtistHot(num,id,this)
            }
        }

    }

    override fun initView() {

    }
}