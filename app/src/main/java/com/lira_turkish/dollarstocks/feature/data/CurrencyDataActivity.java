package com.lira_turkish.dollarstocks.feature.data;


import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.LangKey;
import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.view.View;


import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.databinding.ActivityCurrencyDataBinding;
import com.lira_turkish.dollarstocks.feature.data.history.HistoryActivity;
import com.lira_turkish.dollarstocks.models.CurrencyData;
import com.lira_turkish.dollarstocks.models.Day;
import com.lira_turkish.dollarstocks.models.History;
import com.lira_turkish.dollarstocks.utils.AppContent;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.formatter.DecimalFormatterManager;
import com.lira_turkish.dollarstocks.utils.language.BaseActivity;
import com.lira_turkish.dollarstocks.utils.util.ResourceUtil;
import com.lira_turkish.dollarstocks.utils.util.ToolUtil;
import com.lira_turkish.dollarstocks.utils.view.DetailsMarkerView;

import java.util.ArrayList;
import java.util.List;

public class CurrencyDataActivity extends BaseActivity<ActivityCurrencyDataBinding> {

    private CurrencyDataPresenter presenter;
    private CurrencyData data;
    private long dayInMilleSecond = 24 * 60 * 60 * 1000;
    private String selected;
    private ArrayList<History> histories = new ArrayList<>();
    private boolean isDay;
    String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //currency_data
        setRootView(ActivityCurrencyDataBinding.inflate(getLayoutInflater()));

        lang = AppPreferences.getInstance(getBaseContext()).prefs.getString(LangKey, "");

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
        presenter = new CurrencyDataPresenter(this);
        data = (CurrencyData) getIntent().getExtras().getSerializable(AppContent.CURRENCY_DATA);
        getRootView().included.name.setText(getString(R.string.dollar_in) + data.getCity());
        selected = ToolUtil.getDate(System.currentTimeMillis() - dayInMilleSecond);
        setSelect(AppContent.DAY);
    }

    @Override
    protected void getDataInResume() {
        presenter.getHistory(data.getCity(), selected);
    }

    @Override
    protected void clicks() {
        getRootView().statistics.setOnClickListener(view -> {
            Intent intent = new Intent(CurrencyDataActivity.this, HistoryActivity.class);
            intent.putExtra(AppContent.CURRENCY_DATA, data);
            startActivity(intent);
        });
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
        isDay = false;

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
                isDay = true;
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

    @SuppressLint("SetTextI18n")
    private void setRange() {
        if (!histories.isEmpty()) {
            History max = new ResourceUtil().getMaxValue(histories);
            History min = new ResourceUtil().getMinValue(histories);
            getRootView().dayRange.setText(DecimalFormatterManager.getFormatterBalanceWithRound()
                    .format(Double.parseDouble(max.getOption1())) + " - " + DecimalFormatterManager
                    .getFormatterBalanceWithRound().format(Double.parseDouble(min.getOption1())));

            getRootView().close.setText(DecimalFormatterManager.getFormatterBalanceWithRound()
                    .format(Double.parseDouble(min.getOption1())));

            ArrayList<Day> list = AppPreferences.getInstance(getBaseContext()).getDataForStorage().getDays();
            for (Day day : list) {
                if (day.getCity().equals(data.getCity())) {
                    getRootView().open.setText(DecimalFormatterManager.getFormatterBalanceWithRound()
                            .format(Double.parseDouble(day.getValue())-50));
                }
            }
        }
    }

    public void setHistoryData(ArrayList<History> data) {
        this.histories = data;
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> yValues = new ArrayList<>();
        for (History history : data) {
            entries.add(new Entry(data.indexOf(history), Float.parseFloat(history.getOption1())));
            yValues.add(history.getCreated_at());
        }
        setDataInLineChart(entries, yValues);
        if (isDay) {
            setRange();
        }
    }

    @SuppressLint("NewApi")
    private void setDataInLineChart(ArrayList<Entry> dataValues, ArrayList<String> Y_values) {
        getRootView().lineChart.getLegend().setEnabled(false);
        getRootView().lineChart.setPinchZoom(false);

        YAxis rightAxis = getRootView().lineChart.getAxisRight();
        rightAxis.setEnabled(false);
        YAxis leftAxis = getRootView().lineChart.getAxisLeft();
        //Set the y-axis on the left of the chart to be disabled
        leftAxis.setEnabled(true);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(true);
        leftAxis.setTextColor(getColor(R.color.primary));
        leftAxis.setTextSize(14f);
        //Set the x axis
        XAxis xAxis = getRootView().lineChart.getXAxis();

        xAxis.setTextColor(getColor(R.color.primary));
        xAxis.setTextSize(0.5f);
        //xAxis.setAxisMinimum(0f);
        xAxis.setDrawAxisLine(true);//Whether to draw the axis
        xAxis.setDrawGridLines(false);//Set the line corresponding to each point on the x-axis
        xAxis.setDrawLabels(true);//Draw label refers to the corresponding value on the x axis
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//Set the display position of the x axis
        xAxis.setGranularity(0.5f);
        xAxis.setLabelCount(0);

        Legend legend = getRootView().lineChart.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        legend.setTextColor(Color.WHITE);

        //hidexAxis description
        Description description = new Description();
        description.setEnabled(false);
        getRootView().lineChart.setDescription(description);
        LineDataSet lds = new LineDataSet(dataValues, "1");
        lds.setLineWidth(2f);
        lds.setColor(getColor(R.color.primary));

        lds.setCircleColor(getColor(R.color.primary_dark));
        lds.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

        List<ILineDataSet> dataset = new ArrayList<>();
        dataset.add(lds);

        LineData data = new LineData(dataset);
        getRootView().lineChart.setData(data);

        DetailsMarkerView detailsMarkerView = new DetailsMarkerView(this, R.layout.item_dialog_linechart, Y_values);
        detailsMarkerView.setChartView(getRootView().lineChart);
        getRootView().lineChart.setMarker(detailsMarkerView);

        getRootView().lineChart.invalidate();

    }
}

//minfest
