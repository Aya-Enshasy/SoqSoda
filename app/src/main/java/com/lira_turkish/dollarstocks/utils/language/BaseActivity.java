package com.lira_turkish.dollarstocks.utils.language;

import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.dialog.wait.WaitDialog;
import com.lira_turkish.dollarstocks.services.api.ApiClient;
import com.lira_turkish.dollarstocks.services.api.ApiInterface;

import java.util.Arrays;
import java.util.Map;

public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {

    private WaitDialog waitDialog;
    private T rootView;
    public ApiInterface service;

        public void setRootView(T rootView) {
        this.rootView = rootView;
        this.setContentView(rootView.getRoot());

            service = ApiClient.getRetrofit().create(ApiInterface.class);
//            sharedPreferences = AppController.getInstance().getSharedPreferences();

           setDataInCreate();

    }

    public T getRootView() {
        return rootView;
    }

    public WaitDialog getProgressDialog() {
        return waitDialog;
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        waitDialog = new WaitDialog(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.rootView.getRoot().clearFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getRootView().getRoot().setOnClickListener(view -> getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN));
            getDataInResume();
            clicks();

    }

    public void showAlertDialog(String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(R.string.ok, listener)
                .setCancelable(false)
                .show();
    }

    protected abstract void setDataInCreate();

    protected abstract void getDataInResume();

    protected abstract void clicks();

    protected void onLauncherResult(ActivityResult result) {
    }

    protected void onPermissionsResult(Map<String, Boolean> result) {
    }

    public ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), this::onLauncherResult);

    public ActivityResultLauncher<String[]> permission = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(), this::onPermissionsResult);
}
