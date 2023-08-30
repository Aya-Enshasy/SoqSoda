
package com.lira_turkish.dollarstocks.world.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class World implements Serializable
{

    @SerializedName("timestamp")
    @Expose
    private Long timestamp;
    @SerializedName("rates")
    @Expose
    private List<Rate> rates = null;
    private final static long serialVersionUID = 6865889276620833787L;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

}
