package com.lira_turkish.dollarstocks.feature.currency.fragments.news.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.databinding.ItemNewsBinding;
import com.lira_turkish.dollarstocks.dialog.crops.CropsDialog;
import com.lira_turkish.dollarstocks.dialog.news.NewsDialog;
import com.lira_turkish.dollarstocks.models.CurrencyData;
import com.lira_turkish.dollarstocks.utils.formatter.DecimalFormatterManager;
import com.lira_turkish.dollarstocks.utils.util.NavigateUtil;

import java.util.ArrayList;

public class NewssAdapter extends RecyclerView.Adapter<NewssAdapter.NewsHolder> {

    private ArrayList<CurrencyData> items;
    private Context context;
    private CropsDialog.Listener listener;

    public void setListener(CropsDialog.Listener listener) {
        this.listener = listener;
    }

    public NewssAdapter(Context context, ArrayList<CurrencyData> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //news
        return new NewsHolder(ItemNewsBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
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

    public class NewsHolder extends RecyclerView.ViewHolder {

        private ItemNewsBinding binding;
        private CurrencyData data;

        public NewsHolder(ItemNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.upOrDown.setOnClickListener(view -> {
                NewsDialog dialog = new NewsDialog(context, data);
                dialog.setListener(listener);
                dialog.show();
            });
            binding.getRoot().setOnClickListener(view -> new NavigateUtil().openCurrencyData(context, data));
        }

        @SuppressLint("SetTextI18n")
        public void setData(CurrencyData item) {
            this.data = item;
            binding.name.setText(item.getCity());
            binding.value.setText(item.getBuy());
            binding.percentage.setText("% " + DecimalFormatterManager.getFormatterBalance().format(((Double.parseDouble(item.getBuy()) - Double.parseDouble(item.getLastBuy())) * 100 / Double.parseDouble(item.getLastBuy()))));
            binding.upOrDown.setImageResource(item.getState().equals("0") ? R.drawable.ic_cursor_down : R.drawable.ic_cursor_up);
        }
    }
}
