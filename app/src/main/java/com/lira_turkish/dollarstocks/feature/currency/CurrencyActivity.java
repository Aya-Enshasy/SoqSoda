package com.lira_turkish.dollarstocks.feature.currency;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lira_turkish.dollarstocks.crop.CropFragment;
import com.lira_turkish.dollarstocks.databinding.ActivityCurrencyBinding;
import com.lira_turkish.dollarstocks.expectation.ExpectationFragment;
import com.lira_turkish.dollarstocks.feature.currency.fragments.changer.CurrencyExchangerFragment;
import com.lira_turkish.dollarstocks.feature.currency.fragments.currency.CurrencyFragment;
import com.lira_turkish.dollarstocks.feature.currency.fragments.date.CurrencyDateFragment;
import com.lira_turkish.dollarstocks.feature.currency.fragments.news.NewsFragment;
import com.lira_turkish.dollarstocks.feature.currency.fragments.services.ServicesFragment;
import com.lira_turkish.dollarstocks.models.Service;
import com.lira_turkish.dollarstocks.utils.AppContent;
import com.lira_turkish.dollarstocks.utils.AppController;
import com.lira_turkish.dollarstocks.utils.language.BaseActivity;
import com.lira_turkish.dollarstocks.utils.util.NavigateUtil;
import com.lira_turkish.dollarstocks.world.WorldFragment;


public class CurrencyActivity extends BaseActivity<ActivityCurrencyBinding> {

    private CurrencyPresenter presenter;
    private Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(ActivityCurrencyBinding.inflate(getLayoutInflater()));


    }

    @Override
    protected void setDataInCreate() {
        presenter = new CurrencyPresenter(this);
        if (getIntent().getExtras() != null) {
            service = (Service) getIntent().getExtras().getSerializable(AppContent.SERVICES);
            switch (service.getType()) {
                case Service.LP_CURRENCY:
                case Service.TR_CURRENCY:
                    new NavigateUtil().replaceFragment(getSupportFragmentManager(), new CurrencyFragment(service), getRootView().container.getId());
                    break;
                case Service.SY_CURRENCY:
                    new NavigateUtil().replaceFragment(getSupportFragmentManager(), new CurrencyDateFragment(service), getRootView().container.getId());
                    break;
                case Service.CONTROL_PANEL:
                    onBackPressed();
                    break;

                case Service.CROPS:
                    new NavigateUtil().replaceFragment(getSupportFragmentManager(), new CropFragment(this, service), getRootView().container.getId());
                    break;

                case Service.NEWS:
                    new NavigateUtil().replaceFragment(getSupportFragmentManager(), new NewsFragment(service), getRootView().container.getId());
                    break;

                case Service.SERVICES:
                    new NavigateUtil().replaceFragment(getSupportFragmentManager(), new ServicesFragment(service), getRootView().container.getId());
                    break;

                case Service.WORLD_CURRENCIES:
                    new NavigateUtil().replaceFragment(getSupportFragmentManager(), new WorldFragment(this,service), getRootView().container.getId());
                    break;

                case Service.EXCHANGE:
                    new NavigateUtil().replaceFragment(getSupportFragmentManager(), new CurrencyExchangerFragment(service), getRootView().container.getId());
                    break;

                case Service.EXPECTATIONS:
                    new NavigateUtil().replaceFragment(getSupportFragmentManager(), new ExpectationFragment(this, service), getRootView().container.getId());
                    break;

                case Service.SITES:
                    new NavigateUtil().openLink(this, "https://dei-sy.com/");
                    onBackPressed();
                    break;
                default:
                    onBackPressed();
                    break;
            }
        } else {
            onBackPressed();
        }
    }

    @Override
    protected void getDataInResume() {

    }

    @Override
    protected void clicks() {

    }
}

//minfest
