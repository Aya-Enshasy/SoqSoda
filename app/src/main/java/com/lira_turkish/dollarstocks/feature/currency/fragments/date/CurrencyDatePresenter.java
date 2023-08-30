package com.lira_turkish.dollarstocks.feature.currency.fragments.date;

import static android.content.Context.MODE_PRIVATE;

import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.LangKey;
import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.content.SharedPreferences;


import com.lira_turkish.dollarstocks.models.ApiResult;
import com.lira_turkish.dollarstocks.models.CurrencyData;
import com.lira_turkish.dollarstocks.models.Response;
import com.lira_turkish.dollarstocks.models.Screen;
import com.lira_turkish.dollarstocks.services.api.ApiClient;
import com.lira_turkish.dollarstocks.services.api.ApiInterface;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.language.BasePresenter;
import com.lira_turkish.dollarstocks.utils.listeners.RequestListener;
import com.lira_turkish.dollarstocks.utils.util.APIUtil;

import java.util.ArrayList;

public class CurrencyDatePresenter extends BasePresenter {

    private CurrencyDateFragment fragment;
    String lang;
    ApiInterface service1;

    public CurrencyDatePresenter(CurrencyDateFragment activity) {
        super(activity.requireContext());
        this.fragment = activity;
    }



    public void getCites() {
        service1 = ApiClient.getRetrofit().create(ApiInterface.class);
        lang = AppPreferences.getInstance(getContext()).prefs.getString(LangKey, "");

        Response response = AppPreferences.getInstance(getContext()).getDataForStorage();
        if (response.getCurrencyDataTypeOne() != null) {
            fragment.setCites(response.getCurrencyDataTypeOne().getData());
        }
        new APIUtil<ArrayList<CurrencyData>>(getContext()).getData(service1.getCurrencyData("1",lang),
                new RequestListener<ApiResult<ArrayList<CurrencyData>>>() {
                    @Override
                    public void onSuccess(ApiResult<ArrayList<CurrencyData>> arrayListApiResult, String msg) {
                        fragment.setCites(arrayListApiResult.getData());
                        response.setCurrencyDataTypeOne(arrayListApiResult);
                        AppPreferences.getInstance(getContext()).setDataForStorage(response);
                        fragment.getProgressDialog().dismiss();
                    }

                    @Override
                    public void onError(String msg) {
                        fragment.getProgressDialog().dismiss();
                    }

                    @Override
                    public void onFail(String msg) {
                        fragment.getProgressDialog().dismiss();
                    }
                });
    }

    public void getScreen() {
        service1 = ApiClient.getRetrofit().create(ApiInterface.class);

        Response response = AppPreferences.getInstance(getContext()).getDataForStorage();
        if (response.getScreen() != null) {
            fragment.setScreen(response.getScreen().getData());
        }
        new APIUtil<ArrayList<Screen>>(getContext()).getData(service1.getScreen(),
                new RequestListener<ApiResult<ArrayList<Screen>>>() {
                    @Override
                    public void onSuccess(ApiResult<ArrayList<Screen>> arrayListApiResult, String msg) {
                        fragment.setScreen(arrayListApiResult.getData());
                        response.setScreen(arrayListApiResult);
                        AppPreferences.getInstance(getContext()).setDataForStorage(response);
                        fragment.getProgressDialog().dismiss();
                    }

                    @Override
                    public void onError(String msg) {
                        fragment.getProgressDialog().dismiss();
                    }

                    @Override
                    public void onFail(String msg) {
                        fragment.getProgressDialog().dismiss();
                    }
                });
    }
}