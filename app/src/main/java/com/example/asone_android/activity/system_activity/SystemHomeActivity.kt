package com.example.asone_android.activity.system_activity

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import com.example.asone_android.Base.BaseActivity
import com.example.asone_android.R
import com.example.asone_android.activity.CommonWebActivity
import com.example.asone_android.adapter.SystemModeAdapter
import com.example.asone_android.utils.ACache
import com.umeng.socialize.UMAuthListener
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import kotlinx.android.synthetic.main.activity_system_home.*
import kotlinx.android.synthetic.main.include_top_bar_all.*




class SystemHomeActivity : BaseActivity() {
    val TAG = "SystemHomeActivity"
    var modeList = mutableListOf<String>()

    override fun getLayout(): Int {
        return R.layout.activity_system_home
    }

    override fun initData() {
        print("onComplete========")
        modeList.add("修改首页列表")
        modeList.add("添加一个Music")
        modeList.add("添加一个艺术家,国家,声音种类")
        modeList.add("分享")
        modeList.add("登录")
        modeList.add("推送")
        recyclerview.layoutManager = GridLayoutManager(this, 3)
        var adapter = SystemModeAdapter(this, R.layout.item_system_mode, modeList)
        recyclerview.adapter = adapter
        adapter.setOnItemClickListener { view, position ->
            when (modeList[position]) {
                "添加一个Music" -> startActivity(Intent(mContext, AddMusicActivity::class.java))
                "修改首页列表" -> startActivity(Intent(mContext, ChangeHouseActivity::class.java))
                "添加一个艺术家,国家,声音种类" -> startActivity(Intent(mContext, AddArtistActivity::class.java))
                "登录" -> {
                    var api: UMShareAPI = UMShareAPI.get(this)
                    api.getPlatformInfo(this, SHARE_MEDIA.QQ, object : UMAuthListener {
                        override fun onComplete(p0: SHARE_MEDIA?, p1: Int, p2: MutableMap<String, String>?) {
                            val uid = p2?.get("uid")
                            val name = p2?.get("name")
                            val head = p2?.get("iconurl")
                            ACache.get().put(ACache.TAG_USER_ID,uid)
                            ACache.get().put(ACache.TAG_USER_NAME,name)
                            ACache.get().put(ACache.TAG_USER_HEAD,head)

                        }

                        override fun onCancel(p0: SHARE_MEDIA?, p1: Int) {
                        }

                        override fun onError(p0: SHARE_MEDIA?, p1: Int, p2: Throwable?) {
                            Log.e(TAG, p2?.message)
                        }

                        override fun onStart(p0: SHARE_MEDIA?) {
                        }

                    })
                }
                "推送" -> {
                    showShortToast("在友盟后台进行")
                    startActivity(Intent(mContext, CommonWebActivity::class.java))
                }
                "分享" -> {
                    val sendIntent = Intent()
                    sendIntent.action = Intent.ACTION_SEND
                    // 比如发送文本形式的数据内容
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, "Share to..."))
                }
            }

        }


    }

//                    ShareAction(this).withText("hello")
//                            .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
//                            .setCallback(object : UMShareListener {
//                                override fun onResult(p0: SHARE_MEDIA?) {
//                                    print("onComplete=========$p0")
//
//                                }
//
//                                override fun onCancel(p0: SHARE_MEDIA?) {
//                                    print("onCancel=========$p0")
//                                }
//
//                                override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
//                                    print("onError=========$p0")
//                                }
//
//                                override fun onStart(p0: SHARE_MEDIA?) {
//                                    print("onStart=========$p0")
//                                }
//
//                            }).open()

    override fun initView() {
        tv_left.text = "后台"
        tv_left.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

}