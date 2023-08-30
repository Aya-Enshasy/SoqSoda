package com.lira_turkish.dollarstocks.feature.currency.fragments.world.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lira_turkish.dollarstocks.databinding.ItemWorldCurrencyBinding;
import com.lira_turkish.dollarstocks.models.CurrentCurrency;

import java.util.ArrayList;

public class WorldCurrenciesAdapter extends RecyclerView.Adapter<WorldCurrenciesAdapter.WorldCurrencieHolder> {

    private ArrayList<CurrentCurrency> items;
    private Context context;

    public WorldCurrenciesAdapter(Context context, ArrayList<CurrentCurrency> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public WorldCurrencieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //world_currencies
        return new WorldCurrencieHolder(ItemWorldCurrencyBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WorldCurrencieHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(CurrentCurrency item) {
        this.items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(ArrayList<CurrentCurrency> items) {
        if (items != null) {
            this.items.addAll(items);
            notifyDataSetChanged();
        }
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

    public class WorldCurrencieHolder extends RecyclerView.ViewHolder {

        private ItemWorldCurrencyBinding binding;

        public WorldCurrencieHolder(ItemWorldCurrencyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(CurrentCurrency item) {
            binding.name.setText(item.getName());
            binding.currencySymbol.setText(item.getCurrency());
            binding.value.setText(String.valueOf(item.getValue()));
        }
    }
}
