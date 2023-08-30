package com.lira_turkish.dollarstocks.feature.currency.fragments.date;

import static android.content.Context.MODE_PRIVATE;

import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.LangKey;
import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.lira_turkish.dollarstocks.NativeTemplateStyle;
import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.TemplateView;
import com.lira_turkish.dollarstocks.databinding.FragmentCurrencyDateBinding;
import com.lira_turkish.dollarstocks.dialog.city.CityDialog;
import com.lira_turkish.dollarstocks.dialog.crops.CropsDialog;
import com.lira_turkish.dollarstocks.feature.currency.fragments.changer.adapter.ExchangeCurrencysAdapter;
import com.lira_turkish.dollarstocks.feature.currency.fragments.date.adapter.CurrencyCardsAdapter;
import com.lira_turkish.dollarstocks.feature.currency.fragments.date.adapter.CurrentCurrencysAdapter;
import com.lira_turkish.dollarstocks.feature.main.HomeActivity;
import com.lira_turkish.dollarstocks.feature.screen.ScreenActivity;
import com.lira_turkish.dollarstocks.models.ApiResult;
import com.lira_turkish.dollarstocks.models.CurrencyData;
import com.lira_turkish.dollarstocks.models.Screen;
import com.lira_turkish.dollarstocks.models.Service;
import com.lira_turkish.dollarstocks.services.api.ApiClient;
import com.lira_turkish.dollarstocks.services.api.ApiInterface;
import com.lira_turkish.dollarstocks.services.firebase.FirebaseMessagingPresenter;
import com.lira_turkish.dollarstocks.utils.AppController;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.formatter.SpinnerAdapter;
import com.lira_turkish.dollarstocks.utils.language.BaseFragment;
import com.lira_turkish.dollarstocks.utils.listeners.RequestListener;
import com.lira_turkish.dollarstocks.utils.util.APIUtil;
import com.lira_turkish.dollarstocks.utils.util.NavigateUtil;
import com.lira_turkish.dollarstocks.world.model.Rate;
import com.lira_turkish.dollarstocks.world.model.World;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyDateFragment extends BaseFragment<FragmentCurrencyDateBinding> implements CropsDialog.Listener {

    public static final String DOLLAR = "dollar";
    private static final String CURRENCIES = "currencies";
    private static final String GOLD = "gold";
    List<Rate> list;
    ApiInterface service1;
    private CurrencyDatePresenter presenter;
    private Service service;
    private String tab = DOLLAR;
    private CurrentCurrencysAdapter currentCurrencysAdapter;
    private CurrencyCardsAdapter cardsAdapter;
    private ArrayList<CurrencyData> data = new ArrayList<>();
    private ArrayList<Screen> screens = new ArrayList<>();
    private final FirebaseMessagingPresenter.Listener listener = map -> getDataInResume();
    private InterstitialAd mInterstitialAd;
String lang;
    public CurrencyDateFragment(Service service) {
        this.service = service;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //lebanon
        setRootView(FragmentCurrencyDateBinding.inflate(inflater, container, false));
        service1 = ApiClient.getRetrofit().create(ApiInterface.class);
        showFullAd();
        showNativAd();
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    private void showFullAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(getActivity(),"ca-app-pub-1210134677677997/6546826271", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.e("badder", "onAdLoaded");
                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(getActivity());
                        } else {
                            Log.e("TAG", "The interstitial ad wasn't ready yet.");
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d("badder7", loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });

    }
    @Override
    protected void setDataInCreate() {

        presenter = new CurrencyDatePresenter(this);
        getRootView().included.name.setText(getString(R.string.dollar));

        ArrayList<Rate> currentCurrencies = new ArrayList<>();
        currentCurrencysAdapter = new CurrentCurrencysAdapter(requireContext(), currentCurrencies);
        getRootView().rvCurrentCurrency.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true));
        getRootView().rvCurrentCurrency.setItemAnimator(new DefaultItemAnimator());
        getRootView().rvCurrentCurrency.setAdapter(currentCurrencysAdapter);

        ArrayList<CurrencyData> currencyCards = new ArrayList<>();
        cardsAdapter = new CurrencyCardsAdapter(requireContext(), currencyCards, "");
        cardsAdapter.setListener(this);
        getRootView().rvCurrency.setItemAnimator(new DefaultItemAnimator());
        getRootView().rvCurrency.setAdapter(cardsAdapter);

        FirebaseMessagingPresenter.getInstance().removeListener(listener);
        FirebaseMessagingPresenter.getInstance().setListeners(listener);

    }

    @SuppressLint("NewApi")
    @Override
    protected void getDataInResume() {
        requireActivity().runOnUiThread(() -> {
            presenter.getCites();
            presenter.getScreen();
            tab = DOLLAR;
            getRootView().included.name.setText(R.string.dollar);
            selectTab();

        });
        getWorld();
    }

    @Override
    public void onStop() {
        super.onStop();
        FirebaseMessagingPresenter.getInstance().removeListener(listener);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void clicks() {
        getRootView().dollar.setOnClickListener(view -> {
            tab = DOLLAR;
            getRootView().included.name.setText(R.string.dollar);
            selectTab();
        });

        getRootView().currencies.setOnClickListener(view -> {
            tab = CURRENCIES;
            getRootView().included.name.setText(R.string.currencies);
            selectTab();
        });

        getRootView().gold.setOnClickListener(view -> {
            tab = GOLD;
            getRootView().included.name.setText(R.string.gold);
            selectTab();
        });

        getRootView().included.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectTab();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getRootView().included.plus.setOnClickListener(view -> {
            CityDialog dialog = new CityDialog(requireContext(), new CurrencyData(0, 1, getString(R.string.add_currency)
                    , "0", "0", "0", "0", 1, "", "", "",""), false);
            dialog.setListener(this);
            dialog.show();
        });

        getRootView().included.ivScreenCapture.setOnClickListener(view ->
                new NavigateUtil().activityIntent(requireContext(), ScreenActivity.class, true));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void selectTab() {
        getRootView().dollar.setBackgroundResource(R.drawable.s_gray_outline);
        getRootView().dollar.setTextColor(requireContext().getColor(R.color.gray));
        getRootView().currencies.setBackgroundResource(R.drawable.s_gray_outline);
        getRootView().currencies.setTextColor(requireContext().getColor(R.color.gray));
        getRootView().gold.setBackgroundResource(R.drawable.s_gray_outline);
        getRootView().gold.setTextColor(requireContext().getColor(R.color.gray));
        getRootView().included.plus.setVisibility(View.GONE);

        switch (tab) {
            case DOLLAR:
                getRootView().dollar.setBackgroundResource(R.drawable.s_green_outline);
                getRootView().dollar.setTextColor(requireContext().getColor(R.color.primary));
                getRootView().included.sp.setVisibility(View.GONE);
                if (AppPreferences.getInstance(getActivity()).isAdmin()) {
                    getRootView().included.plus.setVisibility(View.VISIBLE);
                }
                setCites(data);
                break;
            case CURRENCIES:
                getRootView().included.sp.setVisibility(View.VISIBLE);
                getRootView().currencies.setBackgroundResource(R.drawable.s_green_outline);
                getRootView().currencies.setTextColor(requireContext().getColor(R.color.primary));
                setCurrencies();
                break;
            case GOLD:
                getRootView().included.sp.setVisibility(View.VISIBLE);
                getRootView().gold.setBackgroundResource(R.drawable.s_green_outline);
                getRootView().gold.setTextColor(requireContext().getColor(R.color.primary));
                setGold();
                break;
        }
    }

    public void setCites(ArrayList<CurrencyData> data) {
        this.data = data;
        if (tab.equals(DOLLAR)) {
            cardsAdapter.deleteAll();
            cardsAdapter.addAll(data);
        }
        getRootView().included.spinner.setAdapter(new SpinnerAdapter(requireContext(), data));
        getRootView().included.spinner.setVisibility(View.GONE);
    }

    private void setCurrencies() {
        getRootView().included.spinner.setVisibility(View.VISIBLE);
        CurrencyData currencyData = (CurrencyData) getRootView().included.spinner.getSelectedItem();
        cardsAdapter.deleteAll();
        cardsAdapter.addItem(currencyData);
        for (int i = 0; i < 3; i++) {
            cardsAdapter.addItem(currencyData.update(screens.get(i).getFullname(), Double.parseDouble(screens.get(i).getValue())));
        }
    }

    private void setGold() {
        getRootView().included.spinner.setVisibility(View.VISIBLE);
        CurrencyData currencyData = (CurrencyData) getRootView().included.spinner.getSelectedItem();
        cardsAdapter.deleteAll();
        for (int i = 3; i < screens.size(); i++) {
            cardsAdapter.addItem(currencyData.update(screens.get(i).getFullname(), Double.parseDouble(screens.get(i).getValue())));
            Log.e("nn",screens.get(i).getValue()+"    "+ screens.get(i).getFullname());
        }
    }

    public void setScreen(ArrayList<Screen> data) {
        this.screens = data;
    }

    @Override
    public void beforeAction() {
        getProgressDialog().show();
    }

    @Override
    public void onAction() {
        getProgressDialog().dismiss();
        getDataInResume();
    }

    private void getWorld() {
        lang = AppPreferences.getInstance(getContext()).prefs.getString(LangKey, "");

        service1.getWorldCurrencies(lang).enqueue(new Callback<World>() {
            @Override
            public void onResponse(Call<World> call, Response<World> response) {
                Log.e("", response.code()+"");

                if (response.isSuccessful()) {
                    list = response.body().getRates();
                    currentCurrencysAdapter.setData(list);
                }
                else {
                    Log.e("", response.code()+"");
                }
            }
            @Override
            public void onFailure(Call<World> call, Throwable t) {
                call.cancel();
                Log.e("", t.getMessage()+"");
            }
        });
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