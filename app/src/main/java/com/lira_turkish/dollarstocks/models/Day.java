package com.lira_turkish.dollarstocks.models;

public class Day {

    private String value;
    private String city;

    public Day(String value, String city) {
        this.value = value;
        this.city = city;
    }

    public String getValue() {
        return value;
    }

    public String getCity() {
        return city;
    }

    public Day setValue(String  value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "Day{" +
                ", value='" + value + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
