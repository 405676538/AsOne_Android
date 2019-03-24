package com.example.asone_android.app;

import android.Manifest;

public class Constant {
    public static final int REQUESTCODE_FROM_ACTIVITY = 101;
    public static final int OPEN_TEXT_FILE = 1012;

    public static final String ARG_START_PATH = "arg_start_path";
    public static final String ARG_CURRENT_PATH = "arg_current_path";
    public static final String ARG_FILTER = "arg_filter";
    public static final String STATE_START_PATH = "state_start_path";
    public static final String STATE_CURRENT_PATH = "state_current_path";
    public static final String RESULT_FILE_PATH = "result_file_path";
    public static final int HANDLE_CLICK_DELAY = 150;
    public static final String JAVASCRIPT_ANDROID_TAG = "android";
    public static final String VERSION_STATUS = "VERSION_STATUS";
    public static final String LastVersionCheckTime = "LastVersionCheckTime";

    public static final String[] sPermissionsArray = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.INSTALL_SHORTCUT,
            Manifest.permission.READ_CONTACTS,

    };
    /***
     * rank 模块
     */
    public static float DOUBLE_NUM = 30; //移动率 影响进度条的增长长度
    public static int SPACE_TIME = 2000; //间隔周期，每次移动间隔时间

    /***
     * EventBusCode
     *
     * */
    public static final int E_UPDATA_SYSTEM_MUSI = 11110;

    public static final String[] sixList = {"男","女","无"};
    public static final String[] countryList = {"中国","日本","韩国","美国","英国","俄国","银河共和国"};
    public static final String[] asmrList = {"剪刀","采耳","嘴唇音","聊天","按摩","摩擦","呢喃私语",
            "睡眠","粘液","雨声","自然声","轻敲","阅读","角色扮演","咀嚼","吃糖","低声噪音"};

}
