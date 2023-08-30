package com.lira_turkish.dollarstocks.feature.currency.fragments.services;

import static android.content.Context.MODE_PRIVATE;

import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.LangKey;
import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.lira_turkish.dollarstocks.databinding.FragmentServicesBinding;
import com.lira_turkish.dollarstocks.feature.currency.fragments.services.adapter.ExtraServicesAdapter;
import com.lira_turkish.dollarstocks.models.ExtraService;
import com.lira_turkish.dollarstocks.models.Service;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.language.BaseFragment;

import java.util.ArrayList;

public class ServicesFragment extends BaseFragment<FragmentServicesBinding> {

    private ServicesPresenter presenter;
    private Service service;
    private ExtraServicesAdapter extraServicesAdapter;
    String lang;

    public ServicesFragment(Service service) {
        this.service = service;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //services
        setRootView(FragmentServicesBinding.inflate(inflater, container, false));

        lang = AppPreferences.getInstance(getActivity()).prefs.getString(LangKey, "");



        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected void setDataInCreate() {
        presenter = new ServicesPresenter(this);
        getRootView().topBar.name.setText(service.getName());

        ArrayList<ExtraService> extraServices = new ArrayList<>();
        extraServices.add(new ExtraService());
        extraServices.add(new ExtraService());
        extraServices.add(new ExtraService());
        extraServices.add(new ExtraService());
        extraServices.add(new ExtraService());
        extraServices.add(new ExtraService());
        extraServicesAdapter = new ExtraServicesAdapter(getContext(), extraServices);
        getRootView().extraServices.setItemAnimator(new DefaultItemAnimator());
        getRootView().extraServices.setAdapter(extraServicesAdapter);
    }

    @Override
    protected void getDataInResume() {
        extraServicesAdapter.deleteAll();

    }

    @Override
    protected void clicks() {

    }
}