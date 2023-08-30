package com.lira_turkish.dollarstocks.feature.currency.fragments.news;

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
import com.lira_turkish.dollarstocks.databinding.FragmentNewsBinding;
import com.lira_turkish.dollarstocks.dialog.crops.CropsDialog;
import com.lira_turkish.dollarstocks.feature.currency.fragments.news.adapter.NewssAdapter;
import com.lira_turkish.dollarstocks.models.CurrencyData;
import com.lira_turkish.dollarstocks.models.Service;
import com.lira_turkish.dollarstocks.services.firebase.FirebaseMessagingPresenter;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.language.BaseFragment;

import java.util.ArrayList;

public class NewsFragment extends BaseFragment<FragmentNewsBinding> {

    private NewsPresenter presenter;
    private Service service;
    private NewssAdapter adapter;
    String lang;

    private final FirebaseMessagingPresenter.Listener listener = map -> getDataInResume();

    public NewsFragment(Service service) {
        this.service = service;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //news
        setRootView(FragmentNewsBinding.inflate(inflater, container, false));

        showNativAd();
        lang = AppPreferences.getInstance(getActivity()).prefs.getString(LangKey, "");

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected void setDataInCreate() {
        presenter = new NewsPresenter(this);
        getRootView().topBar.name.setText(service.getName());

        ArrayList<CurrencyData> news = new ArrayList<>();
        adapter = new NewssAdapter(getContext(), news);
        adapter.setListener(new CropsDialog.Listener() {
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
        getRootView().news.setItemAnimator(new DefaultItemAnimator());
        getRootView().news.setAdapter(adapter);

        FirebaseMessagingPresenter.getInstance().removeListener(listener);
        FirebaseMessagingPresenter.getInstance().setListeners(listener);
    }

    @Override
    protected void getDataInResume() {
        requireActivity().runOnUiThread(() -> presenter.getNews());
    }

    @Override
    public void onStop() {
        super.onStop();
        FirebaseMessagingPresenter.getInstance().removeListener(listener);
    }

    @Override
    protected void clicks() {

    }

    public void setData(ArrayList<CurrencyData> data) {
        adapter.deleteAll();
        adapter.addAll(data);
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