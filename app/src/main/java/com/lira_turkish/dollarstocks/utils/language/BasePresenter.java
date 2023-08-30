package com.lira_turkish.dollarstocks.utils.language;

import android.content.Context;


public abstract class BasePresenter {

    private Context context;

    public BasePresenter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }



}
