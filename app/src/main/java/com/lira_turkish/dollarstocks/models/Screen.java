package com.lira_turkish.dollarstocks.models;

import com.google.gson.annotations.SerializedName;

public class Screen {

    //veriables

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("value")
    private String value;

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("update_at")
    private String update_at;
    //methods


    public Screen(String name, String fullname, String value) {
        this.name = name;
        this.value = value;
        this.fullname = fullname;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUpdate_at() {
        return update_at;
    }

    //tostring


    @Override
    public String toString() {
        return "Screen{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", fullname='" + fullname + '\'' +
                ", update_at='" + update_at + '\'' +
                '}';
    }
}