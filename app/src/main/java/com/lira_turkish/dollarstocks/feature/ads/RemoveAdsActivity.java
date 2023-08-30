package com.lira_turkish.dollarstocks.feature.ads;


import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.LangKey;
import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.lira_turkish.dollarstocks.databinding.ActivityRemoveAdsBinding;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.language.BaseActivity;

public class RemoveAdsActivity extends BaseActivity<ActivityRemoveAdsBinding> {

    private RemoveAdsPresenter presenter;
    String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove_ads
        setRootView(ActivityRemoveAdsBinding.inflate(getLayoutInflater()));

        lang = AppPreferences.getInstance(this).prefs.getString(LangKey, "");
    }


    @Override
    protected void setDataInCreate() {
        presenter = new RemoveAdsPresenter(this);
    }

    @Override
    protected void getDataInResume() {

    }

    @Override
    protected void clicks() {
        getRootView().ivBack.setOnClickListener(view -> onBackPressed());
    }
}