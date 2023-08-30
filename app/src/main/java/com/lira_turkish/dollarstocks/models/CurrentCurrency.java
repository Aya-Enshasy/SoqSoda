package com.lira_turkish.dollarstocks.models;

public class CurrentCurrency {
    //veriables
    private String currency;
    private String name;
    private double value;
    //methods

    public CurrentCurrency(String currency, String name, double value) {
        this.currency = currency;
        this.name = name;
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    //tostring

    @Override
    public String toString() {
        return "CurrentCurrency{" +
                ", currency='" + currency + '\'' +
                ", value=" + value +
                '}';
    }
}