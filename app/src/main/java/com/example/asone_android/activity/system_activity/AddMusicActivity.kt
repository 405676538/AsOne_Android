package com.example.asone_android.activity.system_activity

import android.content.Intent
import android.view.View
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.example.asone_android.Base.BaseActivity
import com.example.asone_android.Base.BaseJson
import com.example.asone_android.R
import com.example.asone_android.app.Constant
import com.example.asone_android.bean.Artist
import com.example.asone_android.net.MusicPresenter
import com.example.asone_android.utils.FileUtils
import kotlinx.android.synthetic.main.activity_system.*
import kotlinx.android.synthetic.main.include_top_bar_all.*
import java.io.File
import kotlin.math.log

class AddMusicActivity : BaseActivity(), MusicPresenter.UpLoadView, MusicPresenter.CreatMusicView, MusicPresenter.GetArtistView {



    val TAG = "AddMusicActivity"
    var presenter = MusicPresenter()

    var imgId = ""
    var voiceId = ""
    var title = ""
    var musicLabel = ""
    var artist = ""
    var country = ""
    val musicLabelList = mutableListOf<String>()
    val countryList = mutableListOf<String>()


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
        tv_music_label.setOnClickListener {
            var options =OptionsPickerBuilder(SystemActivity@this, OnOptionsSelectListener
            { options1, _, _, _ -> tv_music_label.text = musicLabelList[options1] }).build<String>()
            options.setPicker(musicLabelList)
            options.show()
        }
        tv_country.setOnClickListener {
            var opTions =OptionsPickerBuilder(SystemActivity@this,object : OnOptionsSelectListener{
            override fun onOptionsSelect(options1: Int, options2: Int, options3: Int, v: View?) {
                tv_country.text = countryList[options1]
            }

        }).build<String>()
            opTions.setPicker(countryList)
            opTions.show()
        }
    }

    fun getEdit(){
        title = et_title.text.toString()
        musicLabel = tv_music_label.text.toString()
        artist = tv_artist.text.toString()
        country = tv_country.text.toString()
    }

    fun setnull(){
//        imgId = ""
//        tv_show_img_id.text = ""
        voiceId = ""
        tv_show_audio_id.text = ""
//        title = ""
//        musicLabel = ""
//        artist = ""
//        country = ""
//        et_title.setText("")
//        tv_music_label.text = ""
//        et_artist.setText("")
//        tv_country.text = ""
    }

    override fun initView() {

        for (str in Constant.countryList){
            countryList.add(str)
        }

        for (str in Constant.asmrList){
            musicLabelList.add(str)
        }

        presenter.getArtistList(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FileUtils.CHOOSE_FILE_CODE ||requestCode == FileUtils.CHOOSE_VOICE_CODE ){
            if (data != null){
                val path = FileUtils.getAbsoluteImagePath(this, data.data)
                var file = File(path)
                presenter.postUpLoad(file,this,requestCode)
                if (requestCode == FileUtils.CHOOSE_VOICE_CODE){
                    et_title.setText(file.name)
                }
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

    override fun getArtistSuccess(artists: MutableList<Artist>?,collects: MutableList<Artist>?) {
        var nameList = mutableListOf<String>()
        for (art in artists!!){
            nameList.add(art.name)
        }
        tv_artist.setOnClickListener {
            val opTions =OptionsPickerBuilder(AddMusicActivity@this, OnOptionsSelectListener
            { options1, options2, options3, v -> tv_artist.text = artists[options1].name }).build<String>()
            opTions.setPicker(nameList)
            opTions.show()
        }
    }

}