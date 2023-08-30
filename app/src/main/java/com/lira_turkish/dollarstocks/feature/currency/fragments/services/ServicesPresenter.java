package com.lira_turkish.dollarstocks.feature.currency.fragments.services;


import com.lira_turkish.dollarstocks.utils.language.BasePresenter;

public class ServicesPresenter extends BasePresenter {

    private ServicesFragment fragment;

    public ServicesPresenter(ServicesFragment fragment) {
        super(fragment.requireContext());
        this.fragment = fragment;
    }
}
