package com.lira_turkish.dollarstocks.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CurrencyData implements Serializable {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("buy")
    @Expose
    private String buy;
    @SerializedName("shell")
    @Expose
    private String shell;
    @SerializedName("last_buy")
    @Expose
    private String lastBuy;
    @SerializedName("last_shell")
    @Expose
    private String lastShell;
    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("relative")
    @Expose
    private String relative;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;
    private final static long serialVersionUID = 7666901864571887932L;



    public CurrencyData(Integer id, Integer type, String city, String buy, String shell, String lastBuy, String lastShell, Integer state, String relative, String updatedAt, String createdAt, String updatedDate) {
        super();
        this.id = id;
        this.type = type;
        this.city = city;
        this.buy = buy;
        this.shell = shell;
        this.lastBuy = lastBuy;
        this.lastShell = lastShell;
        this.state = state;
        this.relative = relative;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.updatedDate = updatedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getShell() {
        return shell;
    }

    public void setShell(String shell) {
        this.shell = shell;
    }

    public String getLastBuy() {
        return lastBuy;
    }

    public void setLastBuy(String lastBuy) {
        this.lastBuy = lastBuy;
    }

    public String getLastShell() {
        return lastShell;
    }

    public void setLastShell(String lastShell) {
        this.lastShell = lastShell;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRelative() {
        return relative;
    }

    public void setRelative(String relative) {
        this.relative = relative;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }


    public CurrencyData update(String city, double value) {
        return new CurrencyData(this.id, this.type, city, String.valueOf(Double.parseDouble(this.buy) * value),
                String.valueOf(Double.parseDouble(this.shell) * value)
                , String.valueOf(Double.parseDouble(this.lastBuy) * value),
                String.valueOf(Double.parseDouble(this.lastShell) * value), this.state, this.relative, this.updatedAt, this.createdAt, this.updatedDate);
    }

    @Override
    public String toString() {
        return "CurrencyData{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", city='" + city + '\'' +
                ", buy='" + buy + '\'' +
                ", last_buy='" + lastBuy + '\'' +
                ", shell='" + shell + '\'' +
                ", last_shell='" + lastShell + '\'' +
                ", state='" + state + '\'' +
                ", relative='" + relative + '\'' +
                ", updated_at='" + updatedAt + '\'' +
                ", created_at='" + getCreatedAt() + '\'' +
                '}';
    }
}
