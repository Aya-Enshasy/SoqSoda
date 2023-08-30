package com.lira_turkish.dollarstocks.feature.currency.fragments.services.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lira_turkish.dollarstocks.databinding.ItemExtraServicesBinding;
import com.lira_turkish.dollarstocks.models.ExtraService;

import java.util.ArrayList;

public class ExtraServicesAdapter extends RecyclerView.Adapter<ExtraServicesAdapter.ServiceHolder> {

    private ArrayList<ExtraService> items;
    private Context context;

    public ExtraServicesAdapter(Context context, ArrayList<ExtraService> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //service
        return new ServiceHolder(ItemExtraServicesBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ExtraService item) {
        this.items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(ArrayList<ExtraService> items) {
        if (items != null) {
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void deleteItem(ExtraService item) {
        int i = this.items.indexOf(item);
        this.items.remove(item);
        notifyItemRemoved(i);
    }

    public void deleteAll() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public class ServiceHolder extends RecyclerView.ViewHolder {

        private ItemExtraServicesBinding binding;

        public ServiceHolder(ItemExtraServicesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(ExtraService item) {
        }
    }
}
