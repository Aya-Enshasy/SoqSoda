package com.lira_turkish.dollarstocks.feature.currency.fragments.world;

import static android.content.Context.MODE_PRIVATE;

import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.LangKey;
import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.NativeAd;
import com.lira_turkish.dollarstocks.NativeTemplateStyle;
import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.TemplateView;
import com.lira_turkish.dollarstocks.databinding.FragmentWorldCurrenciesBinding;
import com.lira_turkish.dollarstocks.feature.currency.fragments.world.adapter.WorldCurrenciesAdapter;
import com.lira_turkish.dollarstocks.models.CurrentCurrency;
import com.lira_turkish.dollarstocks.models.Service;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.language.BaseFragment;

import java.util.ArrayList;

public class WorldCurrenciesFragment extends BaseFragment<FragmentWorldCurrenciesBinding> {

    private WorldCurrenciesPresenter presenter;
    private Service service;
    private WorldCurrenciesAdapter adapter;
     String lang;
    public WorldCurrenciesFragment(Service service) {
        this.service = service;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //world_currencies
        setRootView(FragmentWorldCurrenciesBinding.inflate(inflater, container, false));
         lang = AppPreferences.getInstance(getActivity()).prefs.getString(LangKey, "");
        showNativAd();
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected void setDataInCreate() {
        presenter = new WorldCurrenciesPresenter(this);

        getRootView().topBar.name.setText(getString(R.string.world_currencies));

        ArrayList<CurrentCurrency> currencies = new ArrayList<>();
        adapter = new WorldCurrenciesAdapter(requireContext(), currencies);
        getRootView().worldCurrency.setItemAnimator(new DefaultItemAnimator());
        getRootView().worldCurrency.setAdapter(adapter);
    }

    @Override
    protected void getDataInResume() {
    //    presenter.getWorldCurrencies();
    }

    @Override
    protected void clicks() {

    }

    public void setCurrencies(ArrayList<CurrentCurrency> currencies) {
        adapter.deleteAll();
        adapter.addAll(currencies);
    }

    private void showNativAd() {
//        MobileAds.initialize(this);
        AdLoader adLoader = new AdLoader.Builder(getActivity(), "ca-app-pub-1210134677677997/1342585141")
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(new ColorDrawable(Color.parseColor("#1E1F22"))).build();
                        TemplateView template = getActivity().findViewById(R.id.my_template_main);
                        template.setStyles(styles);
                        template.setNativeAd(nativeAd);
                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }
}