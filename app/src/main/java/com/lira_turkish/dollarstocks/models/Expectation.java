package com.lira_turkish.dollarstocks.models;

import com.google.firebase.Timestamp;
import com.google.gson.annotations.SerializedName;

public class Expectation {

    //veriables
    @SerializedName("id")
    private String id;
    @SerializedName("img")
    private String img;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("timestamp")
    private Timestamp timestamp;

    //methods

    public Expectation(String id, String img, String title, String content, Timestamp timestamp) {
        this.id = id;
        this.img = img;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Expectation() {
    }

    public String getId() {
        return id;
    }

    public String getImg() {
        return img;
    }

    public String getTitle() {return title;}

    public String getContent() {
        return content;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }

    //tostring

    @Override
    public String toString() {
        return "News{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", updated_at='" + timestamp + '\'' +
                '}';
    }
}