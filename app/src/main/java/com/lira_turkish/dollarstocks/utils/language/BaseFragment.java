package com.lira_turkish.dollarstocks.utils.language;

import static android.content.Context.MODE_PRIVATE;

import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;


import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.dialog.wait.WaitDialog;
import com.lira_turkish.dollarstocks.models.Expectation;
import com.lira_turkish.dollarstocks.utils.AppController;

import java.util.Map;

public abstract class BaseFragment<T extends ViewBinding> extends Fragment {

    private WaitDialog waitDialog;
    private T rootView;

    public void setRootView(T rootView) {
        this.rootView = rootView;
    }

    public T getRootView() {
        return rootView;
    }

    public WaitDialog getProgressDialog() {
        return waitDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        waitDialog = new WaitDialog(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        rootView.getRoot().findViewById(R.id.iv_back).setOnClickListener(view -> requireActivity().onBackPressed());
        rootView.getRoot().findViewById(R.id.iv_reload).setOnClickListener(view ->
        {
            getProgressDialog().show();
            getDataInResume();
        });

        setDataInCreate();

        return rootView.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();


        getDataInResume();
        clicks();

    }

    protected abstract void setDataInCreate();

    protected abstract void getDataInResume();

    protected abstract void clicks();

    protected void onLauncherResult(ActivityResult result) {
    }

    protected void onPermissionsResult(Map<String, Boolean> result) {
    }

    public ActivityResultLauncher<Intent> launcher = registerForActivityResult(new
            ActivityResultContracts.StartActivityForResult(), this::onLauncherResult);

    public ActivityResultLauncher<String[]> permission = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(), this::onPermissionsResult);

}
