package com.example.asone_android.activity.system_activity

import android.content.Intent
import com.example.asone_android.Base.BaseActivity
import com.example.asone_android.Base.BaseJson
import com.example.asone_android.R
import com.example.asone_android.net.MusicPresenter
import com.example.asone_android.utils.FileUtils
import kotlinx.android.synthetic.main.activity_up_version.*
import java.io.File

class VersionUpActivity:BaseActivity(), MusicPresenter.UpLoadView, MusicPresenter.AddVersionView {
    override fun addVsersionSuccess(baseJson: BaseJson?) {
        showShortToast(baseJson!!.fileId)
    }

    var presenter = MusicPresenter()
    override fun getLayout(): Int {
        return R.layout.activity_up_version
    }

    override fun initData() {
        btn_select_apk.setOnClickListener {
            FileUtils.chooseFile(this, FileUtils.CHOOSE_FILE_CODE)
        }
        up_load.setOnClickListener {
            presenter.addVersion(et_version_code.text.toString(),tv_apk_name.text.toString(),this)
        }
    }

    override fun initView() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FileUtils.CHOOSE_FILE_CODE) {
            if (data != null) {
                val path = FileUtils.getAbsoluteImagePath(this, data.data)
                var file = File(path)
                MusicPresenter().postUpLoad(file, this, requestCode)
            } else {
                showShortToast("data 为空")
            }
        }
    }


    override fun Fails(s: String?) {
        showShortToast(s)
    }

    override fun upLoadSuccess(id: String?, type: Int) {
        tv_apk_name.text = id
    }

}