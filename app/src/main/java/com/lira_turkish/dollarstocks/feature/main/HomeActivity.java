package com.lira_turkish.dollarstocks.feature.main;

import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.LangKey;
import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lira_turkish.dollarstocks.NativeTemplateStyle;
import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.TemplateView;
import com.lira_turkish.dollarstocks.closeApp.model.CloseApp;
import com.lira_turkish.dollarstocks.databinding.ActivityMainBinding;
import com.lira_turkish.dollarstocks.dialog.drawer.DrawerDialog;
import com.lira_turkish.dollarstocks.dialog.switching.SwitchingDialog;
import com.lira_turkish.dollarstocks.feature.main.adapter.ServicesAdapter;
import com.lira_turkish.dollarstocks.feature.main.model.GetAds;
import com.lira_turkish.dollarstocks.feature.main.model.GetAdsData;
import com.lira_turkish.dollarstocks.models.Service;
import com.lira_turkish.dollarstocks.services.api.ApiClient;
import com.lira_turkish.dollarstocks.services.api.ApiInterface;
import com.lira_turkish.dollarstocks.utils.AppController;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.language.BaseActivity;
import com.lira_turkish.dollarstocks.utils.util.NavigateUtil;
import com.lira_turkish.dollarstocks.utils.util.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity<ActivityMainBinding> {
    ServicesAdapter adapter;
    Dialog dialog, dialog1;
    FirebaseFirestore database;
    ApiInterface service;
    List<GetAdsData> list;
    int stat = 0;
    int activeAccount;
    int id = 0;
    String lang;
    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;
    private static final int FLEXIBLE_APP_UPDATE_REQ_CODE = 123;
    private InterstitialAd mInterstitialAd;
    Dialog notification_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showFullAd();
        showNativAd();
        setRootView(ActivityMainBinding.inflate(getLayoutInflater()));
        super.onCreate(savedInstanceState);

        lang = AppPreferences.getInstance(getBaseContext()).prefs.getString(LangKey, "");
        dialog = new Dialog(HomeActivity.this);
        dialog1 = new Dialog(HomeActivity.this);
        notification_dialog = new Dialog(HomeActivity.this);
        FirebaseApp.initializeApp(this);
        database = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        service = ApiClient.getRetrofit().create(ApiInterface.class);
        getAdsFromApi();

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        if (NotificationManagerCompat.from(getBaseContext()).areNotificationsEnabled()) {

        } else {
            notificationDialog();
        }


        if (AppPreferences.getInstance(this).isAdmin() == false) {
            getAds();
            getRootView().plus.setVisibility(View.GONE);
        } else {
            getRootView().plus.setVisibility(View.VISIBLE);
            getRootView().plus.setOnClickListener(view -> {
                AdDialog();
            });
        }


        if (AppPreferences.getInstance(this).prefs.getBoolean("isNoteficationActive", false) == false) {
            SwitchingDialog switchingDialog = new SwitchingDialog(HomeActivity.this);
            switchingDialog.setDetails(getString(R.string.sound_notifications));
            switchingDialog.setListener(b -> {
                AppPreferences.getInstance(HomeActivity.this).setNotification(b);
                if (b)
                    AppPreferences.getInstance(this).editor.putBoolean("isNoteficationActive", true);
                else
                    AppPreferences.getInstance(this).editor.putBoolean("isNoteficationActive", false);
            });
            switchingDialog.show();

        }
    }

    private void showFullAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, "ca-app-pub-1210134677677997/6546826271", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.e("badder", "onAdLoaded");
                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(HomeActivity.this);
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

    private void checkUpdate() {

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {

                startUpdateFlow(appUpdateInfo);
            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackBarForCompleteUpdate();
            }
        });
    }

    private void startUpdateFlow(AppUpdateInfo appUpdateInfo) {
        try {

            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, this, FLEXIBLE_APP_UPDATE_REQ_CODE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FLEXIBLE_APP_UPDATE_REQ_CODE) {
            if (resultCode == RESULT_CANCELED) {
                //     Toast.makeText(getApplicationContext(), "Update canceled by user! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_OK) {
                //   Toast.makeText(getApplicationContext(),"Update success! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
            } else {
                //  Toast.makeText(getApplicationContext(), "Update Failed! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
                checkUpdate();
            }
        }
    }

    private void popupSnackBarForCompleteUpdate() {
        Snackbar.make(findViewById(android.R.id.content), "New app is ready!", Snackbar.LENGTH_INDEFINITE)

                .setAction("Install", view -> {
                    if (appUpdateManager != null) {

                        appUpdateManager.completeUpdate();
                    }
                })
                .show();
    }

    private void removeInstallStateUpdateListener() {
        if (appUpdateManager != null) {
            appUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeInstallStateUpdateListener();
    }


    @Override
    protected void setDataInCreate() {
        FirebaseApp.initializeApp(this);

        ArrayList<Service> list = new ArrayList<>();
        list.add(new Service(0, getString(R.string.syrian_lira), getString(R.string.syrian_lira_details), R.drawable.group_83, Service.SY_CURRENCY));
        list.add(new Service(1, getString(R.string.formal_news), getString(R.string.formal_news_details), R.drawable.report_news_svgrepo_com, Service.NEWS));
        list.add(new Service(2, getString(R.string.turkish_lira), getString(R.string.turkish_lira_details), R.drawable.turkesh_lira, Service.TR_CURRENCY));
        list.add(new Service(3, getString(R.string.lebanon_lira), getString(R.string.lebanon_lira_details), R.drawable.lebanon_lira, Service.LP_CURRENCY));
        list.add(new Service(4, getString(R.string.world_currencies), getString(R.string.world_currencies_details), R.drawable.exchange_money_svgrepo_com__1_, Service.WORLD_CURRENCIES));
        list.add(new Service(6, getString(R.string.crops_pricing), getString(R.string.crops_pricing), R.drawable.growth_grow_gardening_plant_svgrepo_com, Service.CROPS));
        list.add(new Service(7, getString(R.string.expectations), getString(R.string.expectations_details), R.drawable.growth_svgrepo_com, Service.EXPECTATIONS));
        list.add(new Service(9, getString(R.string.currency_exchange), getString(R.string.exchange_details), R.drawable.currency_exchange, Service.EXCHANGE));

        adapter = new ServicesAdapter(this, list);
        getRootView().rvCurrency.setItemAnimator(new DefaultItemAnimator());
        getRootView().rvCurrency.setAdapter(adapter);
    }

    @Override
    protected void getDataInResume() {
    }

    @Override
    protected void clicks() {
        getRootView().menu.setOnClickListener(view -> {
            DrawerDialog drawerDialog = new DrawerDialog(HomeActivity.this);
            drawerDialog.show();
        });

        getRootView().notifications.setOnClickListener(view -> {

            SwitchingDialog switchingDialog = new SwitchingDialog(HomeActivity.this);
            switchingDialog.setDetails(getString(R.string.sound_notifications));
            switchingDialog.setListener(b -> {
                AppPreferences.getInstance(HomeActivity.this).setNotification(b);
            });
            switchingDialog.show();
        });
    }

    private void getAdsFromApi() {
        service.getAdvertisements().enqueue(new Callback<GetAds>() {
            @Override
            public void onResponse(Call<GetAds> call, Response<GetAds> response) {
                Log.e("ii", response.code() + "  ");

                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {

                        int id1 = response.body().getData().get(0).getId();
                        id = id1;
                        Log.e("id", id + "");
                        AppPreferences.getInstance(getBaseContext()).editor.putInt("id ads", id);
                        AppPreferences.getInstance(getBaseContext()).editor.apply();


                        getRootView().included.getRoot().setVisibility(response.body().getData().get(0).getStatus() == 1 ? View.VISIBLE : View.GONE);
                        new ResourceUtil().loadImage(HomeActivity.this, response.body().getData().get(0).getImage(), getRootView().included.ivIcon);
                        getRootView().included.tvName.setText(response.body().getData().get(0).getName());
                        getRootView().included.tvDetails.setText(response.body().getData().get(0).getUrl());
                        getRootView().included.tvDetails.setOnClickListener(view -> new NavigateUtil().openLink(HomeActivity.this, response.body().getData().get(0).getUrl()));

                        if (AppPreferences.getInstance(HomeActivity.this).isAdmin()) {
                            getRootView().plus.setVisibility(AppPreferences.getInstance(HomeActivity.this).isAdmin() ? View.VISIBLE : View.GONE);
                            getRootView().plus.setOnClickListener(view -> {
                                AdDialog();
                            });
                        }
                    }

                    if (response.body().getData() == null || AppPreferences.getInstance(getBaseContext()).prefs.getInt("id ads", 0) == 0) {
                        getRootView().included.getRoot().setVisibility(View.GONE);
                    }

                } else {
                    Log.e("else GetAds", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<GetAds> call, Throwable t) {
//                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();
//                Log.e("else GetAds", t.getMessage());

            }
        });

    }

    private void AdDialog() {
        dialog.setContentView(R.layout.dialog_ad);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        EditText title = dialog.findViewById(R.id.title);
        EditText link = dialog.findViewById(R.id.link);
        CheckBox visible = dialog.findViewById(R.id.visible);
        ProgressBar progress = dialog.findViewById(R.id.progress);
        progress.setVisibility(View.GONE);
        Button update = dialog.findViewById(R.id.update);
        ImageView delete = dialog.findViewById(R.id.delete);


        if (AppPreferences.getInstance(getBaseContext()) != null) {
//            update.setText(R.string.update);
            String name = AppPreferences.getInstance(getBaseContext()).prefs.getString("name ads", "");
            String url = AppPreferences.getInstance(getBaseContext()).prefs.getString("url ads", "");
            int status = AppPreferences.getInstance(getBaseContext()).prefs.getInt("status ads", 0);
            int iid = AppPreferences.getInstance(getBaseContext()).prefs.getInt("id ads", 0);
            AppPreferences.getInstance(getBaseContext()).editor.apply();
            title.setText(name);
            link.setText(url);
            if (status == 1) {
                visible.setChecked(true);
            } else {
                visible.setChecked(false);
            }
            if (iid <= 0) {
                Log.e("id", iid + "");
                delete.setVisibility(View.GONE);
            } else {
                delete.setVisibility(View.VISIBLE);
            }
        }

        visible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (visible.isChecked()) {
                    stat = 1;
                    Log.e("1", "1");
                } else {
                    stat = 0;
                    Log.e("0", "0");
                }
            }
        });
        Log.e("stat", stat + "");

        int ads_id = AppPreferences.getInstance(getBaseContext()).prefs.getInt("id ads", 0);
        if (ads_id <= 0) {
            Log.e("add", id + "");
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progress.setVisibility(View.VISIBLE);
                    if (visible.isChecked() == true) {
                        addAdvertisement(title.getText().toString(), link.getText().toString(), 1, progress);

                    } else if (visible.isChecked() == false) {
                        addAdvertisement(title.getText().toString(), link.getText().toString(), 0, progress);

                    } else if (title.getText().toString().isEmpty() || link.getText().toString().isEmpty()) {
                        title.setText(getString(R.string.field_required));
                        link.setText(getString(R.string.field_required));
                        return;
                    } else
                        Toast.makeText(getBaseContext(), "something error", Toast.LENGTH_SHORT).show();


                }
            });
        } else {
            Log.e("ii", id + "else");
            service.closeAPP().enqueue(new Callback<CloseApp>() {
                @Override
                public void onResponse(Call<CloseApp> call, Response<CloseApp> response) {
                    Log.e("", response.code() + "");
                    if (response.isSuccessful()) {
                        if (response.body().getData() != null) {
                            int activeValue = response.body().getData().get(0).getStatus();
                            activeAccount = activeValue;
                            Log.e("activeAccount", activeAccount + "");

                            dialog1.setContentView(R.layout.dialog_go_to_next_app);
                            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                            dialog1.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog1.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
                            dialog1.getWindow().getAttributes().windowAnimations = R.style.animation;

                            Button go = dialog1.findViewById(R.id.go);
                            TextView tv_details = dialog1.findViewById(R.id.tv_details);

                            tv_details.setText(response.body().getData().get(0).getTitle());

                            go.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(response.body().getData().get(0).getUrl()));
                                    startActivity(i);
                                }
                            });

                            if (activeValue == 1) {
                                dialog1.show();
                                dialog1.setCancelable(false);
                            }
                        }
                    } else {
                        Log.e("", response.code() + "");
                        //  dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<CloseApp> call, Throwable t) {
                    call.cancel();
                    Log.e("", t.getMessage() + "");
                    //    dialog.dismiss();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progress.setVisibility(View.VISIBLE);

                    deleteAds(id, progress);
                }
            });

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progress.setVisibility(View.VISIBLE);

                    if (visible.isChecked() == true) {
                        Log.e("tit", title.getText().toString());
                        updateCloseAPP(id, title.getText().toString(), link.getText().toString(), 1, progress);
                    } else if (visible.isChecked() == false) {
                        updateCloseAPP(id, title.getText().toString(), link.getText().toString(), 0, progress);

                    } else if (title.getText().toString().isEmpty() || link.getText().toString().isEmpty()) {
                        title.setText(getString(R.string.field_required));
                        link.setText(getString(R.string.field_required));
                        return;
                    } else
                        Toast.makeText(getBaseContext(), "something error", Toast.LENGTH_SHORT).show();

                }
            });


        }

        dialog.show();
        dialog.setCancelable(true);
    }

    private void addAdvertisement(String name, String url, int status, ProgressBar progressBar) {
        service.addAdvertisements(name, url, status).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.e("add", "add   ");
                    Log.e("add", "add   " + id);

                    getAdsFromApi();
                    Log.e("success", response.message());
                    dialog.dismiss();
                    progressBar.setVisibility(View.GONE);
                    getAdsFromApi();
                    AppPreferences.getInstance(getBaseContext()).editor.putString("name ads", name);
                    AppPreferences.getInstance(getBaseContext()).editor.putString("url ads", url);
                    AppPreferences.getInstance(getBaseContext()).prefs.getInt("status ads", status);
                    AppPreferences.getInstance(getBaseContext()).editor.apply();

                } else {
                    Log.e("else", response.code() + "");
                    progressBar.setVisibility(View.GONE);

                }
                Log.e("aaaaa", response.code() + "");

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("error", t.getMessage() + "");
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    private void getAds() {
        service.closeAPP().enqueue(new Callback<CloseApp>() {
            @Override
            public void onResponse(Call<CloseApp> call, Response<CloseApp> response) {
                Log.e("", response.code() + "");
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        int activeValue = response.body().getData().get(0).getStatus();
                        activeAccount = activeValue;
                        Log.e("activeAccount", activeAccount + "");

                        dialog1.setContentView(R.layout.dialog_go_to_next_app);
                        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                        dialog1.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog1.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
                        dialog1.getWindow().getAttributes().windowAnimations = R.style.animation;

                        Button go = dialog1.findViewById(R.id.go);
                        TextView tv_details = dialog1.findViewById(R.id.tv_details);

                        tv_details.setText(response.body().getData().get(0).getTitle());

                        go.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(response.body().getData().get(0).getUrl()));
                                startActivity(i);
                            }
                        });

                        if (activeValue == 1) {
                            dialog1.show();
                            dialog1.setCancelable(false);
                        }
                    }
                } else {
                    Log.e("", response.code() + "");
                    //  dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<CloseApp> call, Throwable t) {
                call.cancel();
                Log.e("", t.getMessage() + "");
                //    dialog.dismiss();
            }
        });
    }

    private void deleteAds(int id, ProgressBar progressBar) {
        service.deleteAdvertisements(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("", response.code() + "");
                progressBar.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {
                    Log.e("remove", response.code() + "");
                    getAdsFromApi();
                    progressBar.setVisibility(View.GONE);
                    AppPreferences.getInstance(getBaseContext()).editor.putString("name ads", "");
                    AppPreferences.getInstance(getBaseContext()).editor.putString("url ads", "");
                    AppPreferences.getInstance(getBaseContext()).prefs.getInt("status ads", 0);
                    AppPreferences.getInstance(getBaseContext()).prefs.getInt("id ads", 0);
                    AppPreferences.getInstance(getBaseContext()).editor.apply();

                    dialog.dismiss();
                    getAdsFromApi();
                    getAds();
                } else {
                    Log.e("remnove else", response.code() + "");
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                call.cancel();
                Log.e("remove filed", t.getMessage() + "");
                progressBar.setVisibility(View.GONE);

            }
        });

    }

    private void updateCloseAPP(int id, String name, String url, int status, ProgressBar progressBar) {
        service.updateAdvertisements(id, name, url, status).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("", response.code() + "");
                progressBar.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {
                    getAdsFromApi();
                    Log.e("updaete", "update   " + id);

                    AppPreferences.getInstance(getBaseContext()).editor.putString("name ads", name);
                    AppPreferences.getInstance(getBaseContext()).editor.putString("url ads", url);
                    AppPreferences.getInstance(getBaseContext()).prefs.getInt("status ads", status);
                    AppPreferences.getInstance(getBaseContext()).prefs.getInt("id ads", id);
                    AppPreferences.getInstance(getBaseContext()).editor.apply();

                    Log.e("", response.code() + "");
                    progressBar.setVisibility(View.GONE);
                    dialog.dismiss();
                } else {
                    progressBar.setVisibility(View.GONE);

                    Log.e("", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                call.cancel();
                Log.e("", t.getMessage() + "");
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    private void notificationDialog() {
        notification_dialog.setContentView(R.layout.dialog_notification);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        notification_dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        notification_dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
        notification_dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        Button go = notification_dialog.findViewById(R.id.go);


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                    notification_dialog.dismiss();
            }
        });


        notification_dialog.show();
        notification_dialog.setCancelable(true);
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

