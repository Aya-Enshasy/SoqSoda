
package com.lira_turkish.dollarstocks.expectation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Expectation implements Serializable
{

    @SerializedName("data")
    @Expose
    private List<ExpectationData> data = null;
    @SerializedName("total")
    @Expose
    private Integer total;
    private final static long serialVersionUID = 7595186490340315618L;

    public List<ExpectationData> getData() {
        return data;
    }

    public void setData(List<ExpectationData> data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
