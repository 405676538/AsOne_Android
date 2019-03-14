package com.example.asone_android.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.asone_android.Base.BaseActivity
import com.example.asone_android.R
import com.example.asone_android.net.MusicPresenter
import com.example.asone_android.utils.FileUtils
import kotlinx.android.synthetic.main.activity_system.*
import java.io.File
import kotlin.math.log

class SystemActivity : BaseActivity(), MusicPresenter.UpLoadView, MusicPresenter.CreatMusicView {


    val TAG = "SystemActivity"
    var presenter = MusicPresenter()

    var imgId = ""
    var voiceId = ""
    var title = ""
    var musicLabel = ""
    var artist = ""
    var country = ""


    override fun getLayout(): Int {
        return R.layout.activity_system
    }

    override fun initData() {
        btn_select_img.setOnClickListener {
            FileUtils.chooseFile(this,FileUtils.CHOOSE_FILE_CODE)
        }
        btn_select_audio.setOnClickListener {
            FileUtils.chooseFile(this,FileUtils.CHOOSE_VOICE_CODE)
        }
        btn_upload.setOnClickListener {
            getEdit()
            presenter.creatMusic(voiceId,imgId,title,musicLabel,artist,country,this)
        }
    }

    fun getEdit(){
        title = et_title.text.toString()
        musicLabel = et_music_label.text.toString()
        artist = et_artist.text.toString()
        country = et_country.text.toString()
    }

    fun setnull(){
        imgId = ""
        tv_show_img_id.text = ""
        voiceId = ""
        tv_show_audio_id.text = ""
        title = ""
        musicLabel = ""
        artist = ""
        country = ""
        et_title.setText("")
        et_music_label.setText("")
        et_artist.setText("")
        et_country.setText("")
    }

    override fun initView() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FileUtils.CHOOSE_FILE_CODE ||requestCode == FileUtils.CHOOSE_VOICE_CODE ){
            if (data != null){
                val path = FileUtils.getAbsoluteImagePath(this, data.data)
                presenter.postUpLoad(File(path),this,requestCode)
            }else{
                showShortToast("data 为空")
            }

        }
    }

    override fun upLoadSuccess(id: String?,requestCode: Int) {
        if (requestCode == FileUtils.CHOOSE_FILE_CODE){
            imgId = id!!
            tv_show_img_id.text = imgId
        }
        if (requestCode == FileUtils.CHOOSE_VOICE_CODE){
            voiceId = id!!
            tv_show_audio_id.text = voiceId
        }
    }

    override fun Fails(s: String?) {
        showShortToast(s)
    }

    override fun creatMusicSuccess(hint: String?) {
        showShortToast(hint)
        setnull()
    }

}