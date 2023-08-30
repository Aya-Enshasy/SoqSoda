package com.lira_turkish.dollarstocks.feature.data.history;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.NativeAd;
import com.lira_turkish.dollarstocks.NativeTemplateStyle;
import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.TemplateView;
import com.lira_turkish.dollarstocks.databinding.ActivityHistoryBinding;
import com.lira_turkish.dollarstocks.feature.data.history.adapter.HistorysAdapter;
import com.lira_turkish.dollarstocks.models.CurrencyData;
import com.lira_turkish.dollarstocks.models.History;
import com.lira_turkish.dollarstocks.utils.AppContent;
import com.lira_turkish.dollarstocks.utils.language.BaseActivity;
import com.lira_turkish.dollarstocks.utils.util.ToolUtil;

import java.util.ArrayList;

public class HistoryActivity extends BaseActivity<ActivityHistoryBinding> {

    private HistoryPresenter presenter;
    private long dayInMilleSecond = 24 * 60 * 60 * 1000;
    private String selected;
    private CurrencyData data;

    private ArrayList<History> histories = new ArrayList<>();
    private HistorysAdapter adapter;

    private ArrayList<String> dates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showNativAd();
        super.onCreate(savedInstanceState);
        //history
        setRootView(ActivityHistoryBinding.inflate(getLayoutInflater()));

        getRootView().included.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void setDataInCreate() {
        presenter = new HistoryPresenter(this);

        data = (CurrencyData) getIntent().getExtras().getSerializable(AppContent.CURRENCY_DATA);
        getRootView().included.name.setText(getString(R.string.dollar_in) + data.getCity());

        adapter = new HistorysAdapter(this, new ArrayList<>());
        getRootView().history.setItemAnimator(new DefaultItemAnimator());
        getRootView().history.setAdapter(adapter);

        selected = ToolUtil.getDate(System.currentTimeMillis() - dayInMilleSecond);
        setSelect(AppContent.DAY);
    }

    @Override
    protected void getDataInResume() {
        presenter.getHistory(data.getCity(), selected);
    }

    @Override
    protected void clicks() {
        getRootView().day.setOnClickListener(view -> {
            selected = ToolUtil.getDate(System.currentTimeMillis() - dayInMilleSecond);
            setSelect(AppContent.DAY);
        });

        getRootView().week.setOnClickListener(view -> {
            selected = ToolUtil.getDate(System.currentTimeMillis() - (7 * dayInMilleSecond));
            setSelect(AppContent.WEEK);
        });

        getRootView().month.setOnClickListener(view -> {
            selected = ToolUtil.getDate(System.currentTimeMillis() - (30 * dayInMilleSecond));
            setSelect(AppContent.MONTH);
        });

        getRootView().threeMonth.setOnClickListener(view -> {
            selected = ToolUtil.getDate(System.currentTimeMillis() - (90 * dayInMilleSecond));
            setSelect(AppContent.THREE_MONTH);
        });

        getRootView().year.setOnClickListener(view -> {
            selected = ToolUtil.getDate(System.currentTimeMillis() - (365 * dayInMilleSecond));
            setSelect(AppContent.YEAR);
        });

        getRootView().fiveYears.setOnClickListener(view -> {
            selected = ToolUtil.getDate(System.currentTimeMillis() - (5 * 365 * dayInMilleSecond));
            setSelect(AppContent.FIVE_YEAR);
        });
    }

    @SuppressLint("NewApi")
    private void setSelect(String date) {
        getRootView().day.setBackgroundColor(getColor(R.color.light_gray));
        getRootView().day.setTextColor(getColor(R.color.gray));

        getRootView().week.setBackgroundColor(getColor(R.color.light_gray));
        getRootView().week.setTextColor(getColor(R.color.gray));

        getRootView().month.setBackgroundColor(getColor(R.color.light_gray));
        getRootView().month.setTextColor(getColor(R.color.gray));

        getRootView().threeMonth.setBackgroundColor(getColor(R.color.light_gray));
        getRootView().threeMonth.setTextColor(getColor(R.color.gray));

        getRootView().year.setBackgroundColor(getColor(R.color.light_gray));
        getRootView().year.setTextColor(getColor(R.color.gray));

        getRootView().fiveYears.setBackgroundColor(getColor(R.color.light_gray));
        getRootView().fiveYears.setTextColor(getColor(R.color.gray));

        switch (date) {
            case AppContent.DAY:
                getRootView().day.setBackgroundColor(getColor(R.color.gray));
                getRootView().day.setTextColor(getColor(R.color.white));
                break;
            case AppContent.WEEK:
                getRootView().week.setBackgroundColor(getColor(R.color.gray));
                getRootView().week.setTextColor(getColor(R.color.white));
                break;
            case AppContent.MONTH:
                getRootView().month.setBackgroundColor(getColor(R.color.gray));
                getRootView().month.setTextColor(getColor(R.color.white));
                break;
            case AppContent.THREE_MONTH:
                getRootView().threeMonth.setBackgroundColor(getColor(R.color.gray));
                getRootView().threeMonth.setTextColor(getColor(R.color.white));
                break;
            case AppContent.YEAR:
                getRootView().year.setBackgroundColor(getColor(R.color.gray));
                getRootView().year.setTextColor(getColor(R.color.white));
                break;
            case AppContent.FIVE_YEAR:
                getRootView().fiveYears.setBackgroundColor(getColor(R.color.gray));
                getRootView().fiveYears.setTextColor(getColor(R.color.white));
                break;
            default:
                break;
        }
        getDataInResume();
    }

    public void setHistoryData(ArrayList<History> data) {
        adapter.deleteAll();
        dates = new ArrayList<>();
        for (int i = data.size() - 1; i >= 0; i--) {
            String s = ToolUtil.getDate(data.get(i).getCreated_at());
            if (!dates.contains(s)) {
                adapter.addItem(data.get(i));
                dates.add(s);
            }
        }
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
