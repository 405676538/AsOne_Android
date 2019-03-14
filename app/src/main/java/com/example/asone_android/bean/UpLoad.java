package com.example.asone_android.bean;

public class UpLoad {
    private String fileId;

    @Override
    public String toString() {
        return "UpLoad{" +
                "id='" + fileId + '\'' +
                '}';
    }

    public String getId() {
        return fileId;
    }

    public void setId(String fileId) {
        this.fileId = fileId;
    }
}
