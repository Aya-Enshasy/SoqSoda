package com.lira_turkish.dollarstocks.feature.screen;

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

public class ScreenPresenter extends BasePresenter {

    private ScreenActivity activity;
    String lang;
    ApiInterface service1;

    public ScreenPresenter(ScreenActivity activity) {
        super(activity);
        this.activity = activity;
    }

    public void getCites() {
        service1 = ApiClient.getRetrofit().create(ApiInterface.class);
        lang = AppPreferences.getInstance(getContext()).prefs.getString(LangKey, "");


        Response response = AppPreferences.getInstance(getContext()).getDataForStorage();
        if (response.getCurrencyDataTypeOne() != null) {
            activity.setCites(response.getCurrencyDataTypeOne().getData());
        } else {
            activity.getProgressDialog().show();
        }
        new APIUtil<ArrayList<CurrencyData>>(getContext()).getData(service1.getCurrencyData("1",lang),
                new RequestListener<ApiResult<ArrayList<CurrencyData>>>() {
                    @Override
                    public void onSuccess(ApiResult<ArrayList<CurrencyData>> arrayListApiResult, String msg) {
                        activity.setCites(arrayListApiResult.getData());
                        response.setCurrencyDataTypeOne(arrayListApiResult);
                        AppPreferences.getInstance(getContext()).setDataForStorage(response);
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
    }

    public void getWorldCurrencies() {
        service1 = ApiClient.getRetrofit().create(ApiInterface.class);

        Response response = AppPreferences.getInstance(getContext()).getDataForStorage();
        if (response.getScreen() != null) {
            activity.setWorldCurrencies(response.getScreen().getData());
        }
        new APIUtil<ArrayList<Screen>>(getContext()).getData(service1.getScreen(), new RequestListener<ApiResult<ArrayList<Screen>>>() {
            @Override
            public void onSuccess(ApiResult<ArrayList<Screen>> currencyApiResult, String msg) {
                activity.getProgressDialog().dismiss();
                activity.setWorldCurrencies(currencyApiResult.getData());
                response.setScreen(currencyApiResult);
                AppPreferences.getInstance(getContext()).setDataForStorage(response);
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
    }
}
