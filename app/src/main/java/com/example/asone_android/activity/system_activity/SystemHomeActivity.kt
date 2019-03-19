package com.example.asone_android.activity.system_activity

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.asone_android.Base.BaseActivity
import com.example.asone_android.R
import com.example.asone_android.adapter.SystemModeAdapter
import com.example.asone_android.view.SwipeRefresh.BaseRecyAdapter
import kotlinx.android.synthetic.main.activity_system_home.*

class SystemHomeActivity: BaseActivity() {
    var modeList = mutableListOf<String>()

    override fun getLayout(): Int {
        return R.layout.activity_system_home
    }

    override fun initData() {
        modeList.add("修改首页列表")
        modeList.add("添加一个Music")
        modeList.add("添加一个艺术家或国家")
        modeList.add("2")
        recyclerview.layoutManager = GridLayoutManager(this,3)
        var adapter = SystemModeAdapter(this,R.layout.item_system_mode,modeList)
        recyclerview.adapter = adapter
        adapter.setOnItemClickListener { view, position ->
            when(modeList[position]) {
                "添加一个Music" ->  startActivity(Intent(mContext,AddMusicActivity::class.java))
                "修改首页列表"-> startActivity(Intent(mContext,ChangeHouseActivity::class.java))
                "添加一个艺术家或国家"-> startActivity(Intent(mContext,AddArtistActivity::class.java))
            }
        }
    }

    override fun initView() {
    }
}