package com.lira_turkish.dollarstocks.feature.currency.fragments.changer;

import static android.content.Context.MODE_PRIVATE;

import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.LangKey;
import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.ads.AdRequest;
import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.databinding.FragmentCurrencyExchangerBinding;
import com.lira_turkish.dollarstocks.feature.currency.fragments.changer.adapter.ExchangeCurrencysAdapter;
import com.lira_turkish.dollarstocks.feature.currency.fragments.changer.adapter.ResultsAdapter;
import com.lira_turkish.dollarstocks.models.CurrencyData;
import com.lira_turkish.dollarstocks.models.Screen;
import com.lira_turkish.dollarstocks.models.Service;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.formatter.SpinnerAdapter;
import com.lira_turkish.dollarstocks.utils.language.BaseFragment;
import com.lira_turkish.dollarstocks.utils.util.ToolUtil;

import java.util.ArrayList;
import java.util.Objects;

public class CurrencyExchangerFragment extends BaseFragment<FragmentCurrencyExchangerBinding> {

    private CurrencyExchangerPresenter presenter;
    private Service service;
    private ExchangeCurrencysAdapter exchangeCurrencysAdapter;

    private ArrayList<Screen> screens = new ArrayList<>();
    private ArrayList<Screen> result;
    String lang;

    public CurrencyExchangerFragment(Service service) {
        this.service = service;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //currency_exchanger
        setRootView(FragmentCurrencyExchangerBinding.inflate(inflater, container, false));


        lang = AppPreferences.getInstance(getContext()).prefs.getString(LangKey, "");


        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected void setDataInCreate() {
        presenter = new CurrencyExchangerPresenter(this);
        getRootView().topBar.name.setText(service.getName());
        getRootView().topBar.name.setTextSize(15);


        ArrayList<Screen> currencies = new ArrayList<>();
        exchangeCurrencysAdapter = new ExchangeCurrencysAdapter(requireContext(), currencies);
        getRootView().rvExchangeCurrency.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true));
        getRootView().rvExchangeCurrency.setItemAnimator(new DefaultItemAnimator());
        getRootView().rvExchangeCurrency.setAdapter(exchangeCurrencysAdapter);
        exchangeCurrencysAdapter.setListener(screen -> {
            result = new ArrayList<>();
            result.addAll(screens);
            result.remove(screen);
            getRootView().value.setText("");
            setResultAdapter(result, 0.0);
        });
    }

    private void setResultAdapter(ArrayList<Screen> list, Double value) {
        CurrencyData data = (CurrencyData) getRootView().spinner.getSelectedItem();
        list.add(new Screen(data.getCity(), "سوري", String.valueOf(1 / Double.parseDouble(data.getBuy()))));
        ResultsAdapter resultsAdapter = new ResultsAdapter(requireContext(), list, value);
        getRootView().rvCurrency.setItemAnimator(new DefaultItemAnimator());
        getRootView().rvCurrency.setAdapter(resultsAdapter);
    }

    @Override
    protected void getDataInResume() {
        presenter.getCites();
    }

    @Override
    protected void clicks() {
        getRootView().exchange.setOnClickListener(view -> {
            if (!getRootView().value.getText().toString().equals(""))
                exchange();
        });
        getRootView().spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!getRootView().value.getText().toString().equals(""))
                    exchange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ToolUtil.showLongToast(getString(R.string.select_city), requireContext());
            }
        });
    }

    private void exchange() {
        double value = Double.parseDouble(Objects.requireNonNull(getRootView().value.getText()).toString().trim());
        double r = value * Double.parseDouble(exchangeCurrencysAdapter.getSelected().getValue());
        result.remove(result.size() - 1);
        setResultAdapter(result, r);
    }

    public void setCites(ArrayList<CurrencyData> data) {
        getRootView().spinner.setAdapter(new SpinnerAdapter(requireContext(), data));
        presenter.getDef();
    }

    public void setWorldCurrencies(ArrayList<Screen> screens) {
        exchangeCurrencysAdapter.deleteAll();
        this.screens.clear();

        this.screens.add(new Screen("USD", "الدولار", "1"));
        for (int i = 0; i < 3; i++) {
            this.screens.add(screens.get(i));
        }
        exchangeCurrencysAdapter.addAll(this.screens);
    }
}