
package com.lira_turkish.dollarstocks.feature.currency.fragments.world.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lira_turkish.dollarstocks.models.Currency;

import java.io.Serializable;
import java.util.List;

public class GetWorld implements Serializable
{

    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;
    @SerializedName("rates")
    @Expose
    private List<Currency> rates = null;
    private final static long serialVersionUID = 5244639253048788222L;

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public List<Currency> getRates() {
        return rates;
    }

    public void setRates(List<Currency> rates) {
        this.rates = rates;
    }

}
