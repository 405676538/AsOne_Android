package com.example.asone_android.activity.fragment

import android.content.Intent
import com.example.asone_android.Base.BaseFragment
import com.example.asone_android.R
import com.example.asone_android.activity.system_activity.AddMusicActivity
import com.example.asone_android.activity.system_activity.SystemHomeActivity
import kotlinx.android.synthetic.main.fragment_collect.*
import kotlinx.android.synthetic.main.include_top_bar_all.*

class CollectFragment: BaseFragment() {
    override fun getLayout(): Int {
        return R.layout.fragment_collect
    }

    override fun initData() {
        tv_left.text = "我的"
    }

    override fun initView() {
        iv_sys.setOnClickListener {
            startActivity(Intent(activity, SystemHomeActivity::class.java))
        }
    }
}