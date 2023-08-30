
package com.lira_turkish.dollarstocks.crop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CropHistory implements Serializable
{

    @SerializedName("data")
    @Expose
    private List<CropHistoryData> data = null;
    @SerializedName("total")
    @Expose
    private Integer total;
    private final static long serialVersionUID = 7134255769464334355L;

    public List<CropHistoryData> getData() {
        return data;
    }

    public void setData(List<CropHistoryData> data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
