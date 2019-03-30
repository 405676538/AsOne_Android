package com.example.asone_android.activity.system_activity

import android.content.Intent
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.example.asone_android.Base.BaseActivity
import com.example.asone_android.Base.BaseJson
import com.example.asone_android.R
import com.example.asone_android.app.Constant
import com.example.asone_android.bean.CollectInfo
import com.example.asone_android.bean.Country
import com.example.asone_android.net.MusicPresenter
import com.example.asone_android.utils.FileUtils
import kotlinx.android.synthetic.main.activity_add_artist.*
import java.io.File

class AddArtistActivity : BaseActivity(), MusicPresenter.UpLoadView, MusicPresenter.CreatArtistView, MusicPresenter.CreatCountryView, MusicPresenter.CreatSoundTypeView, MusicPresenter.GetCountryView {
    override fun GetCountrySuccess(countries: MutableList<Country>?) {
        for (str in countries!!) {
            countrylList.clear()
            countrylList.add(str.name)
        }


    }


    var presenter = MusicPresenter()
    val sixlList = mutableListOf<String>()
    val countrylList = mutableListOf<String>()

    var imgId = ""
    var name = ""
    var age = ""
    var six = ""
    var brief = ""
    var country = ""
    var commend = "否" //是否推荐

    /** ------------以下参数添加国家  声音种类----------- */
    var countryImg = ""
    var countryName = ""

    var soundImg = ""
    var soundName = ""

    override fun getLayout(): Int {
        return R.layout.activity_add_artist
    }

    override fun initData() {
        presenter.getCountry(this)

        btn_select_img.setOnClickListener {
            FileUtils.chooseFile(this, FileUtils.CHOOSE_FILE_CODE)
        }

        btn_select_country_img.setOnClickListener {
            FileUtils.chooseFile(this, FileUtils.CHOOSE_COUNTRY_CODE)
        }

        btn_select_sound_img.setOnClickListener {
            FileUtils.chooseFile(this, FileUtils.CHOOSE_SOUND_CODE)
        }

        tv_select_six.setOnClickListener {
            var options = OptionsPickerBuilder(AddArtistActivity@this, OnOptionsSelectListener
            { options1, _, _, v -> tv_select_six.text = sixlList[options1] }).build<String>()
            options.setPicker(sixlList)
            options.show()
        }

        tv_country.setOnClickListener {
            var options = OptionsPickerBuilder(AddArtistActivity@ this, OnOptionsSelectListener
            { options1, _, _, v -> tv_country.text = countrylList[options1] }).build<String>()
            options.setPicker(countrylList)
            options.show()
        }

        btn_upload.setOnClickListener {
            brief = et_brief.text.toString()
            name = et_name.text.toString()
            age = et_age.text.toString()
            six = tv_select_six.text.toString()
            country = tv_country.text.toString()
            commend = if (cb_commend.isChecked) "是" else "否"
            presenter.creatArtist(imgId, brief, name, age, six, country, commend, this)
        }

        btn_upload_country.setOnClickListener {
            countryName = et_country.text.toString()
            presenter.creatCountry(countryName,countryImg,this)
        }

        btn_upload_sound.setOnClickListener {
            soundName = et_type_sound.text.toString()
            presenter.creatSoundType(soundName,soundImg,this)
        }

    }

    override fun initView() {

        for (str in Constant.sixList) {
            sixlList.add(str)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FileUtils.CHOOSE_FILE_CODE || requestCode == FileUtils.CHOOSE_COUNTRY_CODE || requestCode == FileUtils.CHOOSE_SOUND_CODE) {
            if (data != null) {
                val path = FileUtils.getAbsoluteImagePath(this, data.data)
                var file = File(path)
                presenter.postUpLoad(file, this, requestCode)
            } else {
                showShortToast("data 为空")
            }
        }
    }

    override fun Fails(s: String?) {

    }

    override fun upLoadSuccess(id: String?, type: Int) {
        if (type == FileUtils.CHOOSE_FILE_CODE) {
            tv_show_img_id.text = id
            imgId = id!!
        }
        if (type == FileUtils.CHOOSE_COUNTRY_CODE) {
            tv_show_country_id.text = id
            countryImg = id!!
        }

        if (type == FileUtils.CHOOSE_SOUND_CODE){
            tv_show_sound_id.text = id
            soundImg = id!!
        }
    }

    override fun creatArtistSuccess(s: String?) {
        showShortToast(s)
    }

    override fun CreatCountrySuccess(ba: BaseJson?) {
        showShortToast(ba?.fileId)
    }

    override fun creatSoundTypeSuccess(baseJson: BaseJson?) {
        showShortToast(baseJson?.fileId)
    }


}