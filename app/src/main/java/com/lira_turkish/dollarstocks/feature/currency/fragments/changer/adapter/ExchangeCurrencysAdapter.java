package com.lira_turkish.dollarstocks.feature.currency.fragments.changer.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.databinding.ItemExchangeCurrencyBinding;
import com.lira_turkish.dollarstocks.models.CurrentCurrency;
import com.lira_turkish.dollarstocks.models.Screen;

import java.util.ArrayList;

public class ExchangeCurrencysAdapter extends RecyclerView.Adapter<ExchangeCurrencysAdapter.ExchangeCurrencyHolder> {

    private ArrayList<Screen> items;
    private Context context;
    private Screen selected;

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public ExchangeCurrencysAdapter(Context context, ArrayList<Screen> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ExchangeCurrencyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //exchange_currency
        return new ExchangeCurrencyHolder(ItemExchangeCurrencyBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ExchangeCurrencyHolder holder, int position) {
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
            selected = items.get(0);
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

    public Screen getSelected() {
        return selected;
    }

    public class ExchangeCurrencyHolder extends RecyclerView.ViewHolder {

        private ItemExchangeCurrencyBinding binding;
        private Screen currency;

        public ExchangeCurrencyHolder(ItemExchangeCurrencyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(view -> {
                selected = currency;
                notifyDataSetChanged();
            });
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        public void setData(Screen item) {
            this.currency = item;
            binding.currency.setText(item.getFullname());
            if (currency.getName().equals(selected.getName())) {
                binding.getRoot().setBackgroundResource(R.color.primary);
                binding.currency.setTextColor(context.getColor(R.color.white));
                listener.onSelect(selected);
            } else {
                binding.getRoot().setBackgroundResource(R.color.white);
                binding.currency.setTextColor(context.getColor(R.color.black));
            }
        }
    }

    public interface Listener {
        void onSelect(Screen screen);
    }
}
