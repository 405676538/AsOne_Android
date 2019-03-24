package com.example.asone_android.activity.fragment

import com.example.asone_android.Base.BaseFragment
import com.example.asone_android.R
import kotlinx.android.synthetic.main.include_top_bar_all.*

class DisclaimerFragment : BaseFragment() {
    override fun getLayout(): Int {
        return R.layout.fragment_disclaimer
    }

    override fun initData() {
    }

    override fun initView() {
        tv_left.text = "侵权投诉"
    }
}