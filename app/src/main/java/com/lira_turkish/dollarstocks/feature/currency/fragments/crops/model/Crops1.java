
package com.lira_turkish.dollarstocks.feature.currency.fragments.crops.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Crops1 implements Serializable
{

    @SerializedName("data")
    @Expose
    private List<CropsData> data = null;
    @SerializedName("total")
    @Expose
    private Integer total;
    private final static long serialVersionUID = 3131886712057191181L;

    public List<CropsData> getData() {
        return data;
    }

    public void setData(List<CropsData> data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
