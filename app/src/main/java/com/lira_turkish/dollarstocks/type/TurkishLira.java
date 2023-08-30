package com.lira_turkish.dollarstocks.type;

public class TurkishLira {

    String id;
    String option1;
    String option2;
    String option3;
    String type;
    String state;
    String updateAt;
    String createdAt;
    String relative;
    String country;
    String lastSell;
    String lastBuy;

    public TurkishLira(String id, String option1, String option2, String option3, String type, String state, String updateAt, String createdAt, String relative, String country, String lastSell, String lastBuy) {
        this.id = id;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.type = type;
        this.state = state;
        this.updateAt = updateAt;
        this.createdAt = createdAt;
        this.relative = relative;
        this.country = country;
        this.lastSell = lastSell;
        this.lastBuy = lastBuy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getRelative() {
        return relative;
    }

    public void setRelative(String relative) {
        this.relative = relative;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLastSell() {
        return lastSell;
    }

    public void setLastSell(String lastSell) {
        this.lastSell = lastSell;
    }

    public String getLastBuy() {
        return lastBuy;
    }

    public void setLastBuy(String lastBuy) {
        this.lastBuy = lastBuy;
    }
}
