package com.lira_turkish.dollarstocks.models;

import java.io.Serializable;

public class Service implements Serializable {

    public static final String SY_CURRENCY = "sy_currency";

    public static final String LP_CURRENCY = "lp_currency";
    public static final String TR_CURRENCY = "tr_currency";
    public static final String NEWS = "news";
    public static final String WORLD_CURRENCIES = "world_currencies";
    public static final String CROPS = "crops";
    public static final String EXPECTATIONS = "expectations";
    public static final String SERVICES = "services";
    public static final String SITES = "sites";
    public static final String EXCHANGE = "exchange";
    public static final String CONTROL_PANEL = "control";

    //veriables
    private int id;
    private String name;
    private String details;
    private int icon;
    private String type;
    //private String value;
    //methods


    public Service(int id, String name, String details, int icon, String type) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.icon = icon;
        this.type = type;
        //this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public int getIcon() {
        return icon;
    }

    public String getType() {
        return type;
    }

    //tostring


    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", icon_url='" + icon + '\'' +
                ", icon_url='" + type + '\'' +
                '}';
    }
}