package com.example.asone_android.activity.system_activity

import android.content.Intent
import android.view.View
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.example.asone_android.Base.BaseActivity
import com.example.asone_android.Base.BaseJson
import com.example.asone_android.R
import com.example.asone_android.net.MusicPresenter
import com.example.asone_android.utils.FileUtils
import kotlinx.android.synthetic.main.activity_system.*
import java.io.File
import kotlin.math.log

class AddMusicActivity : BaseActivity(), MusicPresenter.UpLoadView, MusicPresenter.CreatMusicView {


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
        artist = et_artist.text.toString()
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
        countryList.add("中国")
        countryList.add("韩国")
        countryList.add("日本")
        countryList.add("美国")
        countryList.add("英国")
        countryList.add("俄国")
        countryList.add("银河共和国")


        musicLabelList.add("剪刀")
        musicLabelList.add("采耳")
        musicLabelList.add("嘴唇音")
        musicLabelList.add("聊天")
        musicLabelList.add("按摩")
        musicLabelList.add("摩擦")
        musicLabelList.add("呢喃 私语")
        musicLabelList.add("睡眠")
        musicLabelList.add("粘液")
        musicLabelList.add("雨声")
        musicLabelList.add("自然声")
        musicLabelList.add("轻敲")
        musicLabelList.add("阅读")
        musicLabelList.add("角色扮演")
        musicLabelList.add("咀嚼")
        musicLabelList.add("吃糖")
        musicLabelList.add("低声噪音")
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

}