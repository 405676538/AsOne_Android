package com.example.asone_android.activity.fragment

import com.example.asone_android.Base.BaseFragment
import com.example.asone_android.R
import kotlinx.android.synthetic.main.fragment_music_list.*

/**
 * # 处理音乐列表数据 0:全部 1：openNum  2:musicLabel 3:artist  4:upTime
 * */
class MusicListFragment : BaseFragment() {

    companion object {
        val type = "type"
        var query = "query"
    }

    init {

    }

    override fun getLayout(): Int {
        return R.layout.fragment_music_list
    }

    override fun initData() {
    }

    override fun initView() {
        toolbar.title = "music list"
    }
}