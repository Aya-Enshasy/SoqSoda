package com.lira_turkish.dollarstocks.feature.currency.fragments.currency;


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

public class CurrencyPresenter extends BasePresenter {

    private CurrencyFragment fragment;
    String lang;
    ApiInterface service1;

    public CurrencyPresenter(CurrencyFragment fragment) {
        super(fragment.requireContext());
        this.fragment = fragment;
    }

    public void getCurrency(String type) {
        service1 = ApiClient.getRetrofit().create(ApiInterface.class);
        lang = AppPreferences.getInstance(getContext()).prefs.getString(LangKey, "");

        Response response = setData(type);
        new APIUtil<ArrayList<CurrencyData>>(getContext()).getData(service1.getCurrencyData(type,lang)
                , new RequestListener<ApiResult<ArrayList<CurrencyData>>>() {
                    @Override
                    public void onSuccess(ApiResult<ArrayList<CurrencyData>> arrayListApiResult, String msg) {
                        fragment.getProgressDialog().dismiss();

                        fragment.setData(arrayListApiResult.getData());
                        if (type.equals("5")) {
                            response.setCurrencyDataTypeFive(arrayListApiResult);
                        } else if (type.equals("6")) {
                            response.setCurrencyDataTypeSix(arrayListApiResult);
                        }
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

    private Response setData(String type) {
        Response response = AppPreferences.getInstance(getContext()).getDataForStorage();
        if (type.equals("5") && response.getCurrencyDataTypeFive() != null) {
            fragment.setData(response.getCurrencyDataTypeFive().getData());
        } else if (type.equals("6") && response.getCurrencyDataTypeSix() != null) {
            fragment.setData(response.getCurrencyDataTypeSix().getData());
        } else {
            fragment.getProgressDialog().show();
        }
        return response;
    }
}
