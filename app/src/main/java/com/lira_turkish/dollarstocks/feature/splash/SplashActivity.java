package com.lira_turkish.dollarstocks.feature.splash;


import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.LangKey;
import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.lira_turkish.dollarstocks.databinding.ActivitySplashBinding;
import com.lira_turkish.dollarstocks.feature.main.HomeActivity;
import com.lira_turkish.dollarstocks.models.ApiResult;
import com.lira_turkish.dollarstocks.models.CurrencyData;
import com.lira_turkish.dollarstocks.models.Day;
import com.lira_turkish.dollarstocks.models.Response;
import com.lira_turkish.dollarstocks.services.api.ApiClient;
import com.lira_turkish.dollarstocks.services.api.ApiInterface;
import com.lira_turkish.dollarstocks.utils.AppController;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.language.BaseActivity;
import com.lira_turkish.dollarstocks.utils.listeners.RequestListener;
import com.lira_turkish.dollarstocks.utils.util.APIUtil;
import com.lira_turkish.dollarstocks.utils.util.NavigateUtil;
import com.lira_turkish.dollarstocks.utils.util.ToolUtil;

import java.util.ArrayList;
import java.util.Locale;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    private static final String TAG = "SplashActivity:ads";
    private SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
        super.onCreate(savedInstanceState);
        //splash
        setRootView(ActivitySplashBinding.inflate(getLayoutInflater()));


    }

    @Override
    protected void setDataInCreate() {
        presenter = new SplashPresenter(this);

        getExtras();

    }

    private void getExtras() {
        if (getIntent().getExtras() != null) {
            RemoteMessage message = new RemoteMessage(getIntent().getExtras());
            Log.e(getClass().getName() + "intent", message.getData().toString());
            if (!message.getData().isEmpty()) {
                String url = message.getData().get("link");
                if (url != null && !url.isEmpty())
                    new NavigateUtil().openLink(this, url);
            }
        }
    }


    @Override
    protected void getDataInResume() {
    }

    @Override
    protected void clicks() {

    }


}
