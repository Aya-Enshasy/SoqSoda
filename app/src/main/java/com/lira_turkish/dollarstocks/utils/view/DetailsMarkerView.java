package com.lira_turkish.dollarstocks.utils.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.lira_turkish.dollarstocks.R;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Ibrahim khalil on 10/5/2021.
 */


@SuppressLint("ViewConstructor")
public class DetailsMarkerView extends MarkerView {

    private final TextView mTvMonth;
    private final TextView mTvChart1;
    private final ArrayList<String> yValues;


    public DetailsMarkerView(Context context, int layoutResource, ArrayList<String> yValues) {
        super(context, layoutResource);
        mTvMonth = findViewById(R.id.tv_chart_month);
        mTvChart1 = findViewById(R.id.tv_chart_1);
        this.yValues = yValues;
    }

    //Each time you redraw, this method will be called to refresh the data
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
        try {

            if (e.getY() == 0) {
                mTvChart1.setText("No data");
            } else {
                mTvChart1.setText(yValues.get((int)e.getX()).substring(0,10));
            }
            mTvMonth.setText(""+e.getY());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        super.refreshContent(e, highlight);
    }


    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

    public String concat(float money, String values) {
        return values + new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "yuan";
    }


}
