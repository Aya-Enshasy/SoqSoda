package com.lira_turkish.dollarstocks.utils.formatter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.models.CurrencyData;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter implements android.widget.SpinnerAdapter {

    private ArrayList<CurrencyData> items;
    private Context context;
    private boolean isCondition;

    public SpinnerAdapter(Context context, ArrayList<CurrencyData> items) {
        this.context = context;
        this.items = items;
    }

    public void setCondition(boolean condition) {
        isCondition = condition;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = View.inflate(parent.getContext(), R.layout.item_spinner, null);
        TextView textView = view.findViewById(R.id.text);
        textView.setText(items.get(position).getCity());
        return view;
    }
}
