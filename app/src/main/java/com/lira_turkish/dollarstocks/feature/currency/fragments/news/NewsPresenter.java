package com.lira_turkish.dollarstocks.feature.currency.fragments.news;

import static android.content.Context.MODE_PRIVATE;

import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.LangKey;
import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.content.SharedPreferences;


import com.lira_turkish.dollarstocks.models.ApiResult;
import com.lira_turkish.dollarstocks.models.CurrencyData;
import com.lira_turkish.dollarstocks.models.Response;
import com.lira_turkish.dollarstocks.services.api.ApiClient;
import com.lira_turkish.dollarstocks.services.api.ApiInterface;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.language.BasePresenter;
import com.lira_turkish.dollarstocks.utils.listeners.RequestListener;
import com.lira_turkish.dollarstocks.utils.util.APIUtil;

import java.util.ArrayList;

public class NewsPresenter extends BasePresenter {

    private NewsFragment fragment;
    String lang;
    ApiInterface service1;
    public NewsPresenter(NewsFragment fragment) {
        super(fragment.requireContext());
        this.fragment = fragment;
    }

    public void getNews() {
        lang = AppPreferences.getInstance(getContext()).prefs.getString(LangKey, "");

        service1 = ApiClient.getRetrofit().create(ApiInterface.class);

        Response response = AppPreferences.getInstance(getContext()).getDataForStorage();
        if (response.getCurrencyDataTypeFour() != null) {
            fragment.setData(response.getCurrencyDataTypeFour().getData());
        } else {
            fragment.getProgressDialog().show();
        }
        new APIUtil<ArrayList<CurrencyData>>(getContext()).getData(service1.getCurrencyData("4",lang)
                , new RequestListener<ApiResult<ArrayList<CurrencyData>>>() {
                    @Override
                    public void onSuccess(ApiResult<ArrayList<CurrencyData>> arrayListApiResult, String msg) {
                        fragment.setData(arrayListApiResult.getData());
                        response.setCurrencyDataTypeFour(arrayListApiResult);
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
