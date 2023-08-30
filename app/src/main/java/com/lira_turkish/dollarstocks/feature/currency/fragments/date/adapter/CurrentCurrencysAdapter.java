package com.lira_turkish.dollarstocks.feature.currency.fragments.date.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.lira_turkish.dollarstocks.databinding.ItemCurrentCurrencyBinding;
import com.lira_turkish.dollarstocks.models.CurrentCurrency;
import com.lira_turkish.dollarstocks.world.model.Rate;

import java.util.List;

public class CurrentCurrencysAdapter extends RecyclerView.Adapter<CurrentCurrencysAdapter.CurrentCurrencyHolder> {

    private List<Rate> items;
    private Context context;

    public CurrentCurrencysAdapter(Context context, List<Rate> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CurrentCurrencyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //current_curreny
        return new CurrentCurrencyHolder(ItemCurrentCurrencyBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentCurrencyHolder holder, int position) {
        holder.binding.currency.setText(items.get(position).getCode());
        holder.binding.value.setText(items.get(position).getValue()+"");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Rate item) {
        this.items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(List<Rate> items) {
        if (items != null) {
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void setData(List<Rate> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void deleteItem(CurrentCurrency item) {
        int i = this.items.indexOf(item);
        this.items.remove(item);
        notifyItemRemoved(i);
    }

    public void deleteAll() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public class CurrentCurrencyHolder extends RecyclerView.ViewHolder {

        private ItemCurrentCurrencyBinding binding;

        public CurrentCurrencyHolder(ItemCurrentCurrencyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(CurrentCurrency item) {
            binding.currency.setText(item.getCurrency());
            binding.value.setText(String.valueOf(item.getValue()));
        }
    }
}
