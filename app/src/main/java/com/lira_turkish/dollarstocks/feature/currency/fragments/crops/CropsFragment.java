package com.lira_turkish.dollarstocks.feature.currency.fragments.crops;

import static android.content.Context.MODE_PRIVATE;
import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.LangKey;
import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.NativeAd;
import com.lira_turkish.dollarstocks.NativeTemplateStyle;
import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.TemplateView;
import com.lira_turkish.dollarstocks.databinding.FragmentCropsBinding;
import com.lira_turkish.dollarstocks.dialog.ad.AdDialog;
import com.lira_turkish.dollarstocks.dialog.crops.CropsDialog;
import com.lira_turkish.dollarstocks.dialog.image.SelectImageDialog;
import com.lira_turkish.dollarstocks.feature.currency.fragments.crops.adapter.CropsAdapter;
import com.lira_turkish.dollarstocks.feature.currency.fragments.crops.model.Crops1;
import com.lira_turkish.dollarstocks.feature.currency.fragments.crops.model.CropsData;
import com.lira_turkish.dollarstocks.models.Crops;
import com.lira_turkish.dollarstocks.models.Service;
import com.lira_turkish.dollarstocks.services.api.ApiInterface;
import com.lira_turkish.dollarstocks.utils.AppController;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.language.BaseActivity;
import com.lira_turkish.dollarstocks.utils.language.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CropsFragment extends BaseFragment<FragmentCropsBinding> implements AdDialog.Listener, CropsDialog.Listener {

    private CropsPresenter presenter;
    private Service service;
    private CropsAdapter adapter;
    private BaseActivity baseActivity;
    private CropsDialog dialog;
    ApiInterface service1;
    List<CropsData>list;

    String lang;

    public CropsFragment(BaseActivity baseActivity, Service service) {
        this.baseActivity = baseActivity;
        this.service = service;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //crops
        setRootView(FragmentCropsBinding.inflate(inflater, container, false));
        list = new ArrayList<>();
        showNativAd();
        service1 = AppController.getInstance().getApi();
        getCropsApi();

        lang = AppPreferences.getInstance(getActivity()).prefs.getString(LangKey, "");

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected void setDataInCreate() {
        presenter = new CropsPresenter(this);
        getRootView().topBar.name.setText(service.getName());
        if (AppPreferences.getInstance(getActivity()).isAdmin()) {
            getRootView().addCrops.setVisibility(View.VISIBLE);
        }
        list = new ArrayList<>();
        adapter = new CropsAdapter(this, list);
        adapter.setListener(this);
        adapter.setMlistener(this);
        getRootView().crops.setItemAnimator(new DefaultItemAnimator());
        getRootView().crops.setAdapter(adapter);
    }

    @Override
    protected void getDataInResume() {
        //presenter.getCrops();
        getCropsApi();
    }

    @Override
    protected void clicks() {
        getRootView().addCrops.setOnClickListener(view -> {
            showCorpDialog(new Crops(), false);
        });
    }

    public void showCorpDialog(Crops crops, boolean isEdit) {
        if (!AppPreferences.getInstance(getActivity()).isAdmin()) {
            return;
        }
        dialog = new CropsDialog(baseActivity, crops, isEdit);
        dialog.setListener(this);
        dialog.setmListener(this);
        dialog.show();
    }

    public void setData(List<CropsData> data) {
        adapter.deleteAll();
        adapter.addAll(data);
    }

    @Override
    public void onSelectImage() {
        setImageDialog(dialog);
    }

    private void setImageDialog(CropsDialog dialog) {
        SelectImageDialog imageDialog = new SelectImageDialog(baseActivity);
        imageDialog.setListener(new SelectImageDialog.Listener() {
            @Override
            public void OnImageLoad(Bitmap bitmap) {
                dialog.setImage(bitmap);
                imageDialog.dismiss();
            }

            @Override
            public void onRemoveImage() {
                imageDialog.dismiss();
            }
        });
        imageDialog.show(requireActivity().getSupportFragmentManager(), "");
    }

    @Override
    public void onUpdate() {
        getDataInResume();
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

    public void getCropsApi() {

        service1.getCropsFromApi(lang).enqueue(new Callback<Crops1>() {
            @Override
            public void onResponse(Call<Crops1> call, Response<Crops1> response) {

                if (response.isSuccessful()) {
                    list = response.body().getData();
                    adapter.setData(list);
                    Log.e("else",response.message());

                }else {
                    Log.e("else",response.message());
                }
            }
            @Override
            public void onFailure(Call<Crops1> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();
                Log.e("else",t.getMessage());

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