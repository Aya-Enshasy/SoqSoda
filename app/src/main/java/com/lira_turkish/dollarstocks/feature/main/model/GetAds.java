
package com.lira_turkish.dollarstocks.feature.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetAds implements Serializable
{
    @SerializedName("data")
    @Expose
    private List<GetAdsData> data = null;
    @SerializedName("total")
    @Expose
    private Integer total;
    private final static long serialVersionUID = 7595186490340315618L;

    public List<GetAdsData> getData() {
        return data;
    }

    public void setData(List<GetAdsData> data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}