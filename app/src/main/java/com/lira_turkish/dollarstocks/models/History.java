package com.lira_turkish.dollarstocks.models;

import com.google.gson.annotations.SerializedName;

public class History {

    @SerializedName("id")
    private String id;

    @SerializedName("option1")
    private String option1;

    @SerializedName("option2")
    private String option2;

    @SerializedName("option3")
    private String option3;

    @SerializedName("type")
    private String type;

    @SerializedName("currency_id")
    private String currency_id;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("updated_at")
    private String updated_at;



    public String getId() {
        return id;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getType() {
        return type;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    @Override
    public String toString() {
        return "History{" +
                "id='" + id + '\'' +
                ", option1='" + option1 + '\'' +
                ", option2='" + option2 + '\'' +
                ", option3='" + option3 + '\'' +
                ", type='" + type + '\'' +
                ", currency_id='" + currency_id + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
