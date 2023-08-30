package com.lira_turkish.dollarstocks.models;

import com.google.firebase.Timestamp;
import com.google.gson.annotations.SerializedName;

public class Crops {
    //veriables
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private String price;

    @SerializedName("img")
    private String img;

    @SerializedName("state")
    private String state;

    @SerializedName("code")
    private String code;

    @SerializedName("relative")
    private String relative;
    private Timestamp timestamp;

    //methods


    public Crops(String id, String name, String price, String img, String state, String code, Timestamp timestamp) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.img = img;
        this.state = state;
        this.code = code;
        this.timestamp = timestamp;
    }

    public Crops() {}

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImg() {
        return img;
    }

    public String getState() {
        return state;
    }

    public String getCode() {
        return code;
    }

    public String getRelative() {
        return relative;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    //tostring


    @Override
    public String toString() {
        return "Crops{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", img='" + img + '\'' +
                ", state='" + state + '\'' +
                ", relative='" + relative + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}