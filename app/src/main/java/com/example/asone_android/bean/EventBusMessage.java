package com.example.asone_android.bean;

public class EventBusMessage {

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
