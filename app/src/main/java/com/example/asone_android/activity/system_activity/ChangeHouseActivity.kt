package com.example.asone_android.activity.system_activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.asone_android.Base.BaseActivity
import com.example.asone_android.Base.BaseJson
import com.example.asone_android.R
import com.example.asone_android.adapter.MusicAdapter
import com.example.asone_android.adapter.MusicselectAdapter
import com.example.asone_android.app.Constant
import com.example.asone_android.bean.EventBusMessage
import com.example.asone_android.bean.Music
import com.example.asone_android.bean.MusicAlbum
import com.example.asone_android.net.MusicPresenter
import com.example.asone_android.utils.FileUtils
import kotlinx.android.synthetic.main.activity_change_house.*
import kotlinx.android.synthetic.main.include_top_bar_all.*
import java.io.File
import java.util.logging.Handler

/** 添加首页music */
class ChangeHouseActivity : BaseActivity(), MusicPresenter.UpLoadView, MusicPresenter.GetMusicView, MusicPresenter.CreatHouseAlbumMusic {



    var presenter = MusicPresenter()
    var albumImgId = ""
    var title = ""
    var selectMusicList = mutableListOf<Music>()
    var allMusicList = mutableListOf<Music>()

    lateinit var selectAdapter: MusicselectAdapter
    lateinit var allAdapter: MusicAdapter

    override fun getLayout(): Int {
        return R.layout.activity_change_house
    }

    override fun initData() {
        btn_select_img.setOnClickListener {
            FileUtils.chooseFile(this, FileUtils.CHOOSE_FILE_CODE)
        }
        rcl_select.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        rcl_all_music.layoutManager = LinearLayoutManager(this)
        selectAdapter = MusicselectAdapter(mContext, R.layout.item_music_select, selectMusicList)
        allAdapter = MusicAdapter(mContext, R.layout.item_music, allMusicList)
        rcl_select.adapter = selectAdapter
        rcl_all_music.adapter = allAdapter

        presenter.getAlbumMusic(0,"",this)

        btn_upload.setOnClickListener {
            title = et_title.text.toString()
            var musicAlbum = MusicAlbum()
            musicAlbum.imgUrl = albumImgId
            musicAlbum.musicList = selectMusicList
            presenter.creatHouseAlbum(title,musicAlbum,this)
        }

    }

    override fun initView() {
        tv_left.text = "修改首页列表"
        tv_left.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FileUtils.CHOOSE_FILE_CODE || requestCode == FileUtils.CHOOSE_VOICE_CODE) {
            if (data != null) {
                val path = FileUtils.getAbsoluteImagePath(this, data.data)
                presenter.postUpLoad(File(path), this, requestCode)
            } else {
                showShortToast("data 为空")
            }
        }
    }

    override fun Fails(s: String?) {

    }

    override fun upLoadSuccess(id: String?, type: Int) {
        tv_show_img_id.text = id
        albumImgId = id!!
    }

    override fun getMusicDuccess(album: MutableList<Music>?) {
        album.let {
            allMusicList.clear()
            allMusicList.addAll(album!!)
            for (music in allMusicList){
                music.playVisible = true
                music.selectVisible = true
            }
            allAdapter.notifyDataSetChanged()
        }
    }

    fun upLoadSelectList(){
        selectMusicList.clear()
        for (music in allMusicList){
            if (music.select){
                selectMusicList.add(music)
            }
        }
        selectAdapter.notifyDataSetChanged()
        allAdapter.notifyDataSetChanged()
    }

    override fun onEventMainThread(eventBusMessage: EventBusMessage?) {
        super.onEventMainThread(eventBusMessage)
        when(eventBusMessage!!.code){
            Constant.E_UPDATA_SYSTEM_MUSI -> upLoadSelectList()
            Constant.E_UPDATA_SYSTEM_MUSI_SELECT -> {
                deleteSelect(eventBusMessage.code1)
            }
        }
    }

    fun deleteSelect(position:Int){
        android.os.Handler(mainLooper).post {
            val music = selectMusicList[position]
            for (allmusic in allMusicList){
                if (music == allmusic){
                    allmusic.select = false
                }
            }
            selectMusicList.removeAt(position)
            allAdapter.notifyDataSetChanged()
            selectAdapter.notifyDataSetChanged()
        }
    }

    override fun albumUpSuccess(json: BaseJson?) {
        showShortToast(json!!.fileId)
    }


    override fun onDestroy() {
        super.onDestroy()
        selectAdapter.distory()
        allAdapter.distory()
    }
}