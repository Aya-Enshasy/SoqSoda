package com.lira_turkish.dollarstocks.feature.currency.fragments.changer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.lira_turkish.dollarstocks.databinding.ItemExchangeBinding;
import com.lira_turkish.dollarstocks.models.Screen;
import com.lira_turkish.dollarstocks.utils.formatter.DecimalFormatterManager;

import java.util.ArrayList;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultHolder> {

    private ArrayList<Screen> items;
    private Context context;
    private Double value;

    public ResultsAdapter(Context context, ArrayList<Screen> items, Double value) {
        this.context = context;
        this.items = items;
        this.value = value;
    }

    @NonNull
    @Override
    public ResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //result
        return new ResultHolder(ItemExchangeBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Screen item) {
        this.items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(ArrayList<Screen> items) {
        if (items != null) {
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void deleteItem(Screen item) {
        int i = this.items.indexOf(item);
        this.items.remove(item);
        notifyItemRemoved(i);
    }

    public void deleteAll() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public class ResultHolder extends RecyclerView.ViewHolder {

        private ItemExchangeBinding binding;

        public ResultHolder(ItemExchangeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(Screen item) {
            binding.first.setText(item.getFullname());
            binding.second.setText(DecimalFormatterManager.getFormatterBalance().format(value /Double.parseDouble(item.getValue())));
        }
    }
}
