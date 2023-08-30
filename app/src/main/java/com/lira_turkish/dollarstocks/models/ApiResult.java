package com.lira_turkish.dollarstocks.models;

import com.google.gson.annotations.SerializedName;

public class ApiResult<T> {

    @SerializedName(value = "data", alternate = {"rates", "message_id"})
    private T data;

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                ", data=" + data +
                '}';
    }

    public class AnyThing {

    }
}

