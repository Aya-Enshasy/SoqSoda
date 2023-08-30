package com.lira_turkish.dollarstocks.feature.currency.fragments.currency;

import static android.content.Context.MODE_PRIVATE;

import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.LangKey;
import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.NativeAd;
import com.lira_turkish.dollarstocks.NativeTemplateStyle;
import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.TemplateView;
import com.lira_turkish.dollarstocks.databinding.FragmentCurrencyBinding;
import com.lira_turkish.dollarstocks.dialog.crops.CropsDialog;
import com.lira_turkish.dollarstocks.feature.currency.fragments.date.adapter.CurrencyCardsAdapter;
import com.lira_turkish.dollarstocks.models.ApiResult;
import com.lira_turkish.dollarstocks.models.CurrencyData;
import com.lira_turkish.dollarstocks.models.Service;
import com.lira_turkish.dollarstocks.services.api.ApiClient;
import com.lira_turkish.dollarstocks.services.api.ApiInterface;
import com.lira_turkish.dollarstocks.services.firebase.FirebaseMessagingPresenter;
import com.lira_turkish.dollarstocks.utils.formatter.SpinnerAdapter;
import com.lira_turkish.dollarstocks.utils.language.BaseFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyFragment extends BaseFragment<FragmentCurrencyBinding> {
    ApiInterface service1;
    private CurrencyPresenter presenter;
    private Service service;
    private CurrencyCardsAdapter cardsAdapter;
    private final FirebaseMessagingPresenter.Listener listener = map -> getDataInResume();

    public CurrencyFragment(Service service) {
        this.service = service;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //lebanon
        setRootView(FragmentCurrencyBinding.inflate(inflater, container, false));
        showNativAd();

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected void setDataInCreate() {
        presenter = new CurrencyPresenter(this);

        getRootView().topBar.name.setText(service.getName());

        cardsAdapter = new CurrencyCardsAdapter(requireContext(), new ArrayList<>(), service.getType().equals(Service.LP_CURRENCY) ? "" : "tur");
        cardsAdapter.setListener(new CropsDialog.Listener() {
            @Override
            public void beforeAction() {
                getProgressDialog().show();
            }

            @Override
            public void onAction() {
                getProgressDialog().dismiss();
                getDataInResume();
            }
        });
        getRootView().rvCurrency.setItemAnimator(new DefaultItemAnimator());
        getRootView().rvCurrency.setAdapter(cardsAdapter);
        FirebaseMessagingPresenter.getInstance().removeListener(listener);
        FirebaseMessagingPresenter.getInstance().setListeners(listener);
    }

    @Override
    protected void getDataInResume() {
        requireActivity().runOnUiThread(() -> presenter.getCurrency(service.getType().equals(Service.LP_CURRENCY) ? "6" : "5"));
    }

    @Override
    public void onStop() {
        super.onStop();
        FirebaseMessagingPresenter.getInstance().removeListener(listener);
    }

    @Override
    protected void clicks() {
    }

    @SuppressLint("SetTextI18n")
    public void setData(ArrayList<CurrencyData> data) {
        cardsAdapter.deleteAll();
        cardsAdapter.addAll(data);
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