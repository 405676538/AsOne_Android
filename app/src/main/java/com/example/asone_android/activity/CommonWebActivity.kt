package com.example.asone_android.activity

import com.example.asone_android.Base.AndroidJsBridge
import com.example.asone_android.Base.BaseWebsActivity
import com.example.asone_android.R
import kotlinx.android.synthetic.main.activity_web_common.*
import kotlinx.android.synthetic.main.include_top_bar_all.*

class CommonWebActivity:BaseWebsActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_web_common
    }

    override fun initData() {
        tv_left.text = "友盟官网"

    }

    override fun initView() {
        initWeb(ll_collect,"https://message.umeng.com/app/5c9367583fc195073500125a/testmode",AndroidJsBridge())
    }
}