package com.lira_turkish.dollarstocks.utils.listeners;

public interface RequestListener<T> {

    void onSuccess(T t, String msg);
    void onError(String msg);
    void onFail(String msg);
}
