package com.lira_turkish.dollarstocks.feature.splash;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;


import com.google.firebase.messaging.FirebaseMessaging;
import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.feature.main.HomeActivity;
import com.lira_turkish.dollarstocks.models.ApiResult;
import com.lira_turkish.dollarstocks.models.CurrencyData;
import com.lira_turkish.dollarstocks.models.Day;
import com.lira_turkish.dollarstocks.models.Response;
import com.lira_turkish.dollarstocks.services.api.ApiClient;
import com.lira_turkish.dollarstocks.services.api.ApiInterface;
import com.lira_turkish.dollarstocks.utils.AppController;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.language.BasePresenter;
import com.lira_turkish.dollarstocks.utils.listeners.RequestListener;
import com.lira_turkish.dollarstocks.utils.util.APIUtil;
import com.lira_turkish.dollarstocks.utils.util.ToolUtil;

import java.util.ArrayList;
import java.util.Locale;

public class SplashPresenter extends BasePresenter {
//implements MaxAdViewAdListener
    private SplashActivity activity;
    protected boolean isDataFinish, isAdsFinish;
    ApiInterface service1;
String lang;
    public static final String PREF_NAME = "Preferences";
    public static final String LangKey = "lang_K";


    public SplashPresenter(SplashActivity activity) {
        super(activity);
        this.activity = activity;
        FirebaseMessaging.getInstance().subscribeToTopic("sp");//dei

        service1 = ApiClient.getRetrofit().create(ApiInterface.class);
        lang = AppPreferences.getInstance(getContext()).prefs.getString(LangKey, "");

        setLang();
        getData();



    }

    private void getData() {
        new APIUtil<ArrayList<CurrencyData>>(getContext()).getData(service1.getCurrencyData("1",lang),
                new RequestListener<ApiResult<ArrayList<CurrencyData>>>() {
                    @Override
                    public void onSuccess(ApiResult<ArrayList<CurrencyData>> arrayListApiResult, String msg) {
                        updateData(arrayListApiResult.getData(), false);
                        getDataTwo();
                        Intent intent = new Intent(getContext(), HomeActivity.class);
                        getContext().startActivity(intent);
                        activity.finish();

                    }

                    @Override
                    public void onError(String msg) {
                        isDataFinish = true;
                    }

                    @Override
                    public void onFail(String msg) {
                        isDataFinish = true;
                    }
                });
    }

    private void getDataTwo() {
        new APIUtil<ArrayList<CurrencyData>>(getContext()).getData(service1.getCurrencyData("5",lang),
                new RequestListener<ApiResult<ArrayList<CurrencyData>>>() {
                    @Override
                    public void onSuccess(ApiResult<ArrayList<CurrencyData>> arrayListApiResult, String msg) {
                        updateData(arrayListApiResult.getData(), true);
                        getDataThree();
                    }

                    @Override
                    public void onError(String msg) {
                        isDataFinish = true;
                    }

                    @Override
                    public void onFail(String msg) {
                        isDataFinish = true;
                    }
                });
    }

    private void getDataThree() {
        new APIUtil<ArrayList<CurrencyData>>(getContext()).getData(service1.getCurrencyData("6",lang),
                new RequestListener<ApiResult<ArrayList<CurrencyData>>>() {
                    @Override
                    public void onSuccess(ApiResult<ArrayList<CurrencyData>> arrayListApiResult, String msg) {
                        updateData(arrayListApiResult.getData(), true);
                        AppPreferences.getInstance(getContext()).setDate(ToolUtil.getDate(System.currentTimeMillis()));
                        isDataFinish = true;
                    }

                    @Override
                    public void onError(String msg) {
                        isDataFinish = true;
                    }

                    @Override
                    public void onFail(String msg) {
                        isDataFinish = true;
                    }
                });
    }

    private void updateData(ArrayList<CurrencyData> data, boolean isAdd) {
        if (AppPreferences.getInstance(getContext()).getDate()
                .equals(ToolUtil.getDate(System.currentTimeMillis()))) {
            return;
        }
        Response response = AppPreferences.getInstance(getContext()).getDataForStorage();
        ArrayList<Day> list = new ArrayList<>();
        for (CurrencyData d : data) {
            list.add(new Day(d.getShell(), d.getCity()));
        }
        if (isAdd) {
            response.updateDays(list);
        } else {
            response.setDays(list);
        }
        AppPreferences.getInstance(getContext()).setDataForStorage(response);
    }

    private void setLang() {
        String lang = getLang(activity);
        if (!lang.isEmpty()) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            Resources resources = activity.getResources();
            Configuration config = resources.getConfiguration();
            config.setLocale(locale);
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        }
    }

    public static String getLang(Context context) {
        String lang = AppPreferences.getInstance(context.getApplicationContext()).prefs.getString(LangKey, "");
        return lang;
    }


}
