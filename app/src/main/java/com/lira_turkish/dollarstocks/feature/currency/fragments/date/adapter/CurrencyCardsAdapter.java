package com.lira_turkish.dollarstocks.feature.currency.fragments.date.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.databinding.ItemCurrencyCardBinding;
import com.lira_turkish.dollarstocks.dialog.city.CityDialog;
import com.lira_turkish.dollarstocks.dialog.crops.CropsDialog;
import com.lira_turkish.dollarstocks.models.CurrencyData;
import com.lira_turkish.dollarstocks.utils.AppController;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.formatter.DecimalFormatterManager;
import com.lira_turkish.dollarstocks.utils.util.NavigateUtil;
import com.lira_turkish.dollarstocks.world.model.Rate;

import java.util.ArrayList;
import java.util.List;


public class CurrencyCardsAdapter extends RecyclerView.Adapter<CurrencyCardsAdapter.CurrencyCardHolder> {

    private ArrayList<CurrencyData> items;
    private Context context;
    private CropsDialog.Listener listener;
    private String type;

    public void setListener(CropsDialog.Listener listener) {
        this.listener = listener;
    }

    public CurrencyCardsAdapter(Context context, ArrayList<CurrencyData> items, String type) {
        this.context = context;
        this.items = items;
        this.type = type;
    }

    public void setData(ArrayList<CurrencyData> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CurrencyCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //currency_card
        return new CurrencyCardHolder(ItemCurrencyCardBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyCardHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(CurrencyData item) {
        this.items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(ArrayList<CurrencyData> items) {
        if (items != null) {
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void deleteItem(CurrencyData item) {
        int i = this.items.indexOf(item);
        this.items.remove(item);
        notifyItemRemoved(i);
    }

    public void deleteAll() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public class CurrencyCardHolder extends RecyclerView.ViewHolder {

        private ItemCurrencyCardBinding binding;
        private CurrencyData card;

        public CurrencyCardHolder(ItemCurrencyCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.statistics.setOnClickListener(view -> new NavigateUtil().openCurrencyData(context, card));
            binding.upOrDown.setOnClickListener(view -> {
                if (AppPreferences.getInstance(context).isAdmin()) {
                    CityDialog dialog = new CityDialog(context, card, true);
                    dialog.setListener(listener);
                    dialog.show();
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void setData(CurrencyData item) {
            this.card = item;
            binding.name.setText(item.getCity());
            if (type.equals("tur")) {
                binding.buy.setText(DecimalFormatterManager.getFormatterBalance().format(Double.parseDouble(item.getBuy())));
                binding.lastBuy.setText(DecimalFormatterManager.getFormatterBalance().format(Double.parseDouble(item.getLastBuy())));
                binding.shell.setText(DecimalFormatterManager.getFormatterBalance().format(Double.parseDouble(item.getShell())));
                binding.lastShell.setText(DecimalFormatterManager.getFormatterBalance().format(Double.parseDouble(item.getLastShell())));
            } else {
                binding.buy.setText(DecimalFormatterManager.getFormatterBalanceWithRound().format(Double.parseDouble(item.getBuy())));
                binding.lastBuy.setText(DecimalFormatterManager.getFormatterBalanceWithRound().format(Double.parseDouble(item.getLastBuy())));
                binding.shell.setText(DecimalFormatterManager.getFormatterBalanceWithRound().format(Double.parseDouble(item.getShell())));
                binding.lastShell.setText(DecimalFormatterManager.getFormatterBalanceWithRound().format(Double.parseDouble(item.getLastShell())));
            }
            binding.percentage.setText("% " + DecimalFormatterManager.getFormatterBalance().format(((Double.parseDouble(item.getBuy()) - Double.parseDouble(item.getLastBuy())) * 100 / Double.parseDouble(item.getLastBuy()))));
            binding.time.setText(item.getUpdatedDate());
            binding.upOrDown.setImageResource(card.getState()==1 ? R.drawable.ic_cursor_up : R.drawable.ic_cursor_down);
        }
    }
}
