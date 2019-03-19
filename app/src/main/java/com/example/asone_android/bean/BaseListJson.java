package com.example.asone_android.bean;

import com.google.gson.JsonElement;

public class BaseListJson {
    private String model = "";
    private JsonElement fields;
    private String pk = "";

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public JsonElement getFields() {
        return fields;
    }

    public void setFields(JsonElement fields) {
        this.fields = fields;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    @Override
    public String toString() {
        return "BaseListJson{" +
                "model='" + model + '\'' +
                ", fields=" + fields +
                ", pk='" + pk + '\'' +
                '}';
    }
}
