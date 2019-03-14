package com.example.asone_android.activity

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

class SystemActivity : BaseActivity(), MusicPresenter.UpLoadView, MusicPresenter.CreatMusicView {


    val TAG = "SystemActivity"
    var presenter = MusicPresenter()

    var imgId = ""
    var voiceId = ""
    var title = ""
    var musicLabel = ""
    var artist = ""
    var country = ""
    val musicLabelList = mutableListOf<String>()


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
            var options =OptionsPickerBuilder(SystemActivity@this,object : OnOptionsSelectListener{
                override fun onOptionsSelect(options1: Int, options2: Int, options3: Int, v: View?) {
                    tv_music_label.text = musicLabelList[options1]
                }

            }).build<String>()
            options.setPicker(musicLabelList)
            options.show()
        }

    }

    fun getEdit(){
        title = et_title.text.toString()
        musicLabel = tv_music_label.text.toString()
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
        tv_music_label.setText("")
        et_artist.setText("")
        et_country.setText("")
    }

    override fun initView() {
        musicLabelList.add("风雨声")
        musicLabelList.add("夜间")
        musicLabelList.add("人声")
        musicLabelList.add("摩擦声")
        musicLabelList.add("呼吸声")

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