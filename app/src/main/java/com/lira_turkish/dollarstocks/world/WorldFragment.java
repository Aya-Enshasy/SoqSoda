package com.lira_turkish.dollarstocks.world;

import static android.content.Context.MODE_PRIVATE;

import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.LangKey;
import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lira_turkish.dollarstocks.NativeTemplateStyle;
import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.TemplateView;
import com.lira_turkish.dollarstocks.databinding.FragmentWorldBinding;
import com.lira_turkish.dollarstocks.models.CurrentCurrency;
import com.lira_turkish.dollarstocks.models.Service;
import com.lira_turkish.dollarstocks.services.api.ApiClient;
import com.lira_turkish.dollarstocks.services.api.ApiInterface;
import com.lira_turkish.dollarstocks.utils.AppController;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.language.BaseActivity;
import com.lira_turkish.dollarstocks.utils.language.BaseFragment;
import com.lira_turkish.dollarstocks.world.model.Rate;
import com.lira_turkish.dollarstocks.world.model.World;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorldFragment extends BaseFragment<FragmentWorldBinding> {
    FragmentWorldBinding binding;
    WordAdapter adapter;
    List<Rate> list;
    ArrayList<CurrentCurrency> worldCurrency;
    ApiInterface service;
    private BaseActivity baseActivity;
    Dialog dialog;
    private Service service1;
    String lang;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WorldFragment(BaseActivity baseActivity, Service service1) {
        this.baseActivity = baseActivity;
        this.service1 = service1;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWorldBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        dialog = new Dialog(getActivity());
        showNativAd();
        service = ApiClient.getRetrofit().create(ApiInterface.class);
        list = new ArrayList<>() ;
        binding.topBar.name.setText(R.string.world_currencies);

        lang = AppPreferences.getInstance(getContext()).prefs.getString(LangKey, "");

        getWorld();
        toolbar();

        if (AppPreferences.getInstance(getContext())==null){
            getWorld();
            Log.e("getExpectation","getExpectation");
        }else {
            getWorldFromShared();
        }



        return view;
    }

    @Override
    protected void setDataInCreate() {

    }

    @Override
    protected void getDataInResume() {

    }

    @Override
    protected void clicks() {

    }


    private void getWorld() {
        service.getWorldCurrencies(lang).enqueue(new Callback<World>() {
            @Override
            public void onResponse(Call<World> call, Response<World> response) {
                Log.e("", response.code()+"");

                if (response.isSuccessful()) {
                    list = response.body().getRates();
                    adapter.setData(list);
                    Gson gson = new Gson();
                    String json = gson.toJson(list);
                    AppPreferences.getInstance(getContext()).editor.putString("ExpectationData", json);
                    Log.e("json",json);
                    AppPreferences.getInstance(getContext()).editor.apply();
                    dialog.dismiss();
                }
                else {
                    Log.e("", response.code()+"");
                    dialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<World> call, Throwable t) {
                call.cancel();
                Log.e("", t.getMessage()+"");
                dialog.dismiss();
            }
        });
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

    private void getWorld2() {
        refreshDialog();
        service.getWorldCurrencies(lang).enqueue(new Callback<World>() {
            @Override
            public void onResponse(Call<World> call, Response<World> response) {
                Log.e("", response.code()+"");

                if (response.isSuccessful()) {
                    list = response.body().getRates();
                    adapter.setData(list);
                    Gson gson = new Gson();
                    String json = gson.toJson(list);
                    AppPreferences.getInstance(getContext()).editor.putString("ExpectationData", json);
                    Log.e("json",json);
                    AppPreferences.getInstance(getContext()).editor.apply();
                    dialog.dismiss();
                }
                else {
                    Log.e("", response.code()+"");
                    dialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<World> call, Throwable t) {
                call.cancel();
                Log.e("", t.getMessage()+"");
                dialog.dismiss();
            }
        });
    }

    private void getWorldFromShared(){
        Gson gson = new Gson();
        String json = AppPreferences.getInstance(getContext()).prefs.getString("ExpectationData", null);
        Type type = new TypeToken<ArrayList<Rate>>() {}.getType();
        gson.fromJson(json, type);
        Log.e("getCropsFromShared","getCropsFromShared");

        binding.worldCurrency.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
        adapter = new WordAdapter(getActivity(),gson.fromJson(json, type));
        binding.worldCurrency.setAdapter(adapter);

    }

    private void toolbar(){
        binding.topBar.ivReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWorld2();
            }
        });
        binding.topBar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
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