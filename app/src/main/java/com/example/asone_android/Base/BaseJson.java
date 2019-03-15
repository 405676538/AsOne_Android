package com.example.asone_android.Base;

public class BaseJson {
    private String msg;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseJson{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
