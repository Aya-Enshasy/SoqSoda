
package com.lira_turkish.dollarstocks.crop;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CropsModel implements Serializable
{

    @SerializedName("data")
    @Expose
    private List<CropsModelData> data = null;
    @SerializedName("total")
    @Expose
    private Integer total;
    private final static long serialVersionUID = 7022090951515799195L;

    public List<CropsModelData> getData() {
        return data;
    }

    public void setData(List<CropsModelData> data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
