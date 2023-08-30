package com.lira_turkish.dollarstocks.feature.currency.fragments.crops.history;


import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.LangKey;
import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.NativeAd;
import com.lira_turkish.dollarstocks.NativeTemplateStyle;
import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.TemplateView;
import com.lira_turkish.dollarstocks.crop.model.CropHistory;
import com.lira_turkish.dollarstocks.crop.model.CropHistoryData;
import com.lira_turkish.dollarstocks.databinding.ActivityCropsHistoryBinding;
import com.lira_turkish.dollarstocks.feature.currency.fragments.crops.history.adapter.CropsHistorysAdapter;
import com.lira_turkish.dollarstocks.services.api.ApiClient;
import com.lira_turkish.dollarstocks.services.api.ApiInterface;
import com.lira_turkish.dollarstocks.utils.AppContent;
import com.lira_turkish.dollarstocks.utils.AppController;
import com.lira_turkish.dollarstocks.utils.language.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CropsHistoryActivity extends BaseActivity<ActivityCropsHistoryBinding> {
    private CropsHistoryPresenter presenter;
    private CropsHistorysAdapter adapter;
    private List<CropHistoryData> list;
    ApiInterface service;

    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showNativAd();
        super.onCreate(savedInstanceState);
        setRootView(ActivityCropsHistoryBinding.inflate(getLayoutInflater()));
        service = ApiClient.getRetrofit().create(ApiInterface.class);
        list = new ArrayList<>() ;
        dialog = new Dialog(this);

        int id = getIntent().getExtras().getInt(AppContent.CROPS_ID);
        String name = getIntent().getExtras().getString(AppContent.CROPS_NAME);
        getRootView().included.name.setText(name);
        adapter();
        getCropsHistory(id);

        getRootView().included.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    @Override
    protected void setDataInCreate() {
        presenter = new CropsHistoryPresenter(this);
        adapter = new CropsHistorysAdapter(this, new ArrayList<>());
        getRootView().history.setItemAnimator(new DefaultItemAnimator());
        getRootView().history.setAdapter(adapter);
    }

    @Override
    protected void getDataInResume() {
       // adapter.deleteAll();

    }

    @Override
    protected void clicks() {

    }


    private void getCropsHistory(int id) {
        refreshDialog();
        service.cropHistory(id).enqueue(new Callback<CropHistory>() {
            @Override
            public void onResponse(Call<CropHistory> call, Response<CropHistory> response) {
                Log.e("code", response.code()+"");
                Log.e("code_id", id+"");

                if (response.isSuccessful()) {
                    list = response.body().getData();
                    adapter.setData(list);
                    dialog.dismiss();

                }
                else {
                    dialog.dismiss();

                    Log.e("", response.code()+"");
                }
            }
            @Override
            public void onFailure(Call<CropHistory> call, Throwable t) {
                call.cancel();
                Log.e("", t.getMessage()+"");
                dialog.dismiss();

            }
        });
    }

    private void adapter(){

        list = new ArrayList<>() ;
        getRootView().history.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        adapter = new CropsHistorysAdapter(this,list);
        getRootView().history.setAdapter(adapter);

    }

    private void refreshDialog() {
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.transparent);

        ProgressBar progress = dialog.findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void showNativAd() {
//        MobileAds.initialize(this);
        AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-1210134677677997/1342585141")
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(new ColorDrawable(Color.parseColor("#1E1F22"))).build();
                        TemplateView template = findViewById(R.id.my_template_main);
                        template.setStyles(styles);
                        template.setNativeAd(nativeAd);
                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

}
