package com.example.asone_android.bean;

public class EventBusMessage {

    public static final int ADD_ALL_ARTIST_FRAGMENT = 1111;
    public static final int SHOW_NO_LOGIN = 1112;


    public EventBusMessage(int code) {
        this.code = code;
    }

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
