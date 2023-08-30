
package com.lira_turkish.dollarstocks.world.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rate implements Serializable
{

    @SerializedName("value")
    @Expose
    private Double value;
    @SerializedName("code")
    @Expose
    private String code;
    private final static long serialVersionUID = -5095288205963060260L;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
