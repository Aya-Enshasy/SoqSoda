package com.lira_turkish.dollarstocks.feature.data;

import static android.content.Context.MODE_PRIVATE;

import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.LangKey;
import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.content.SharedPreferences;
import android.util.Log;


import com.lira_turkish.dollarstocks.models.ApiResult;
import com.lira_turkish.dollarstocks.models.History;
import com.lira_turkish.dollarstocks.models.Response;
import com.lira_turkish.dollarstocks.services.api.ApiClient;
import com.lira_turkish.dollarstocks.services.api.ApiInterface;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.language.BasePresenter;
import com.lira_turkish.dollarstocks.utils.listeners.RequestListener;
import com.lira_turkish.dollarstocks.utils.util.APIUtil;

import java.util.ArrayList;

public class CurrencyDataPresenter extends BasePresenter {
    ApiInterface service;
    private CurrencyDataActivity activity;
    private String lang, day, week, month, year, fiveYear;


    public CurrencyDataPresenter(CurrencyDataActivity activity) {
        super(activity);
        this.activity = activity;
    }

    public void getHistory(String city, String date) {

        service= ApiClient.getRetrofit().create(ApiInterface.class);
        lang = AppPreferences.getInstance(getContext()).prefs.getString(LangKey, "");

        Response response = AppPreferences.getInstance(getContext()).getDataForStorage();
        if (response.getHistory() != null) {
            activity.setHistoryData(response.getHistory().getData());
        } else {
            activity.getProgressDialog().show();
        }
        new APIUtil<ArrayList<History>>(getContext()).getData(service.getHistory(city, date,lang),
                new RequestListener<ApiResult<ArrayList<History>>>() {
                    @Override
                    public void onSuccess(ApiResult<ArrayList<History>> arrayListApiResult, String msg) {
                        activity.setHistoryData(arrayListApiResult.getData());
                        response.setHistory(arrayListApiResult);
                        AppPreferences.getInstance(getContext()).setDataForStorage(response);
                        activity.getProgressDialog().dismiss();
                        Log.e("uu",arrayListApiResult.getData()+"");
                    }

                    @Override
                    public void onError(String msg) {
                        activity.getProgressDialog().dismiss();

                    }

                    @Override
                    public void onFail(String msg) {
                        activity.getProgressDialog().dismiss();

                    }
                });
        activity.getProgressDialog().dismiss();

    }
}
