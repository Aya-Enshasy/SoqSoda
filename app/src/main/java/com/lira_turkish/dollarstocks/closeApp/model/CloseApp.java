
package com.lira_turkish.dollarstocks.closeApp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CloseApp implements Serializable
{

    @SerializedName("data")
    @Expose
    private List<CloseAppData> data = null;
    @SerializedName("total")
    @Expose
    private Integer total;
    private final static long serialVersionUID = -4351054426549192233L;

    public List<CloseAppData> getData() {
        return data;
    }

    public void setData(List<CloseAppData> data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
