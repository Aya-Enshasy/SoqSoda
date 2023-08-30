package com.lira_turkish.dollarstocks.feature.currency.fragments.expectation;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;


import com.lira_turkish.dollarstocks.databinding.FragmentExpectationBinding;
import com.lira_turkish.dollarstocks.dialog.ad.AdDialog;
import com.lira_turkish.dollarstocks.dialog.crops.CropsDialog;
import com.lira_turkish.dollarstocks.dialog.expectation.ExpectationDialog;
import com.lira_turkish.dollarstocks.dialog.image.SelectImageDialog;
import com.lira_turkish.dollarstocks.feature.currency.fragments.expectation.adapter.ExpectationsAdapter;
import com.lira_turkish.dollarstocks.models.Expectation;
import com.lira_turkish.dollarstocks.models.Service;
import com.lira_turkish.dollarstocks.utils.AppController;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.language.BaseActivity;
import com.lira_turkish.dollarstocks.utils.language.BaseFragment;

import java.util.ArrayList;

public class ExpectationFragment extends BaseFragment<FragmentExpectationBinding> implements CropsDialog.Listener, AdDialog.Listener {

    private ExpectationPresenter presenter;
    private Service service;
    private ExpectationsAdapter expectationsAdapter;
    private BaseActivity baseActivity;
    ExpectationDialog dialog;

    public ExpectationFragment(BaseActivity baseActivity, Service service) {
        this.service = service;
        this.baseActivity = baseActivity;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //expectation
        setRootView(FragmentExpectationBinding.inflate(inflater, container, false));
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected void setDataInCreate() {
        presenter = new ExpectationPresenter(this);
        if (AppPreferences.getInstance(getActivity()).isAdmin()) {
            getRootView().addExpectation.setVisibility(View.VISIBLE);
        }
        getRootView().topBar.name.setText(service.getName());

        ArrayList<Expectation> expectations = new ArrayList<>();
        expectationsAdapter = new ExpectationsAdapter(this, expectations);
        getRootView().expectations.setItemAnimator(new DefaultItemAnimator());
        getRootView().expectations.setAdapter(expectationsAdapter);
    }

    @Override
    protected void getDataInResume() {
        presenter.getExpectation();
    }

    @Override
    protected void clicks() {
        getRootView().addExpectation.setOnClickListener(view -> showExpectationDialog(new Expectation(), false));
    }

    public void showExpectationDialog(Expectation expectation, boolean isEdit) {
        if (!AppPreferences.getInstance(getActivity()).isAdmin()) {
            return;
        }
        dialog = new ExpectationDialog(this, expectation, isEdit);
        dialog.setListener(this);
        dialog.setImageListener(this);
        dialog.show();
    }

    public void setData(ArrayList<Expectation> data) {
        expectationsAdapter.deleteAll();
//        Collections.reverse(data);
        expectationsAdapter.addAll(data);
    }

    @Override
    public void onSelectImage() {
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
}