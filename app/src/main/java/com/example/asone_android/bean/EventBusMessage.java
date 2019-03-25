package com.example.asone_android.bean;

public class EventBusMessage {

    // 0:不过滤 1:name 2:age 3:six 4:country 5:recommend 6 查询收藏的列表
    public static final int ADD_ALL_ARTIST_FRAGMENT = 1111;
    public static final int SHOW_NO_LOGIN = 1112;
    public static final int CAN_SCALL_HOME = 1113;
    public static final int ADD_ALL_HOUSE_FRAGMENT = 1114;
    public static final int ADD_DISCLAIMER = 1115;


    public EventBusMessage(int code) {
        this.code = code;
    }

    public EventBusMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public EventBusMessage(int code, String msg, String msg1) {
        this.code = code;
        this.msg = msg;
        this.msg1 = msg1;
    }

    public EventBusMessage(int code, int  code1, String msg) {
        this.code = code;
        this.code1 = code1;
        this.msg = msg;
    }

    private int code;

    private int code1;

    private String msg;

    private String msg1;

    public int getCode1() {
        return code1;
    }

    public void setCode1(int code1) {
        this.code1 = code1;
    }

    public String getMsg1() {
        return msg1;
    }

    public void setMsg1(String msg1) {
        this.msg1 = msg1;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
