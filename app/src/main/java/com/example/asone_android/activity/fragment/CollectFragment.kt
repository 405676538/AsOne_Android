package com.example.asone_android.activity.fragment

import android.content.Intent
import com.example.asone_android.Base.BaseFragment
import com.example.asone_android.R
import com.example.asone_android.activity.SystemActivity
import kotlinx.android.synthetic.main.fragment_collect.*

class CollectFragment: BaseFragment() {
    override fun getLayout(): Int {
        return R.layout.fragment_collect
    }

    override fun initData() {
    }

    override fun initView() {
        iv_sys.setOnClickListener {
            startActivity(Intent(activity,SystemActivity::class.java))
        }
    }
}