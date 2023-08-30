package com.lira_turkish.dollarstocks.type;

public class WorldCurrency {

    String code;
    double value;
    String country;
    int flag;

    public WorldCurrency(String code, double value, String country, int flag) {
        this.code = code;
        this.value = value;
        this.country = country;
        this.flag = flag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}