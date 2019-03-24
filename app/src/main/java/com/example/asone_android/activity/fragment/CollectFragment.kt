package com.example.asone_android.activity.fragment

import android.app.Activity
import android.content.Intent
import com.example.asone_android.Base.BaseFragment
import com.example.asone_android.R
import com.example.asone_android.activity.system_activity.SystemHomeActivity
import com.example.asone_android.app.Constant
import com.example.asone_android.bean.EventBusMessage
import com.example.asone_android.bean.VersionInfo
import com.example.asone_android.net.MusicPresenter
import com.example.asone_android.utils.AppUtils
import com.example.asone_android.utils.PhoneUtil
import com.example.asone_android.utils.version.VersionUpdataHelper
import kotlinx.android.synthetic.main.fragment_collect.*
import kotlinx.android.synthetic.main.include_top_bar_all.*
import org.greenrobot.eventbus.EventBus


class CollectFragment: BaseFragment(), MusicPresenter.GetBVersionView {
    override fun versionFails() {
        showShortToast("无需更新")
    }


    var presenter = MusicPresenter()
    override fun getLayout(): Int {
        return R.layout.fragment_collect
    }

    override fun initData() {
        tv_left.text = "我的"
        ll_disclaimer.setOnClickListener {
            EventBus.getDefault().post(EventBusMessage(EventBusMessage.ADD_DISCLAIMER))
        }
        tv_version.text = PhoneUtil.getVersionName(mContext).toString()
        ll_version_up.setOnClickListener {
            presenter.getVersionInfo(this)
        }
    }

    override fun initView() {
        iv_sys.setOnClickListener {
            startActivity(Intent(activity, SystemHomeActivity::class.java))
        }
    }

    override fun getVersionSuccess(versionInfo: VersionInfo?) {
        if (versionInfo == null){
            showShortToast("无需更新")
        }
        val localCode = PhoneUtil.getVersion(mContext)
        val neCode = versionInfo?.versionCode!!.toInt()
        if (neCode > localCode){
            var builder = AppUtils.showDialog(mContext) { download(versionInfo) }
            builder.show()
        }else{
            showShortToast("无需更新")
        }
    }

    fun download(versionInfo:VersionInfo?){
        if (checkPermission(Constant.sPermissionsArray[4], Constant.sPermissionsArray[5])) {
            VersionUpdataHelper(mContext as Activity?, AppUtils.getDownLoadFileUrl(versionInfo!!.apkId), true,"")
        } else {
            showShortToast(getString(R.string.storage_permission_not_has_tip))
        }
    }
}