package com.lira_turkish.dollarstocks.feature.data.history.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.lira_turkish.dollarstocks.databinding.ItemHistoryBinding;
import com.lira_turkish.dollarstocks.models.History;
import com.lira_turkish.dollarstocks.utils.util.ToolUtil;

import java.util.ArrayList;

public class HistorysAdapter extends RecyclerView.Adapter<HistorysAdapter.HistoryHolder> {

    private ArrayList<History> items;
    private Context context;
    public HistorysAdapter(Context context, ArrayList<History> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //history
        return new HistoryHolder(ItemHistoryBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        holder.setData(items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(History item) {
        this.items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(ArrayList<History> items) {
        if (items != null) {
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void deleteItem(History item) {
        int i = this.items.indexOf(item);
        this.items.remove(item);
        notifyItemRemoved(i);
    }

    public void deleteAll() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {

        private ItemHistoryBinding binding;
        private History history;

        public HistoryHolder(ItemHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(History item, int position) {
            this.history = item;
            binding.counter.setText(String.valueOf(position + 1));
            binding.price.setText(item.getOption1());
            binding.date.setText(ToolUtil.getDate(item.getCreated_at()));
        }
    }
}
