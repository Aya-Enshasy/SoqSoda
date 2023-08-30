package com.lira_turkish.dollarstocks.utils;

import android.app.Application;

import com.lira_turkish.dollarstocks.services.api.ApiClient;
import com.lira_turkish.dollarstocks.services.api.ApiInterface;

public class AppController extends Application {

    private static AppController mInstance;

    private ApiInterface api;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //get api
        api = ApiClient.getRetrofit().create(ApiInterface.class);
    }


    public ApiInterface getApi() {
        return api;
    }
}

