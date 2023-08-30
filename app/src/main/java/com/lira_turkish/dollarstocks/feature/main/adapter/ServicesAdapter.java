package com.lira_turkish.dollarstocks.feature.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lira_turkish.dollarstocks.databinding.ItemServiceBinding;
import com.lira_turkish.dollarstocks.models.Service;
import com.lira_turkish.dollarstocks.utils.language.BaseActivity;
import com.lira_turkish.dollarstocks.utils.util.NavigateUtil;

import java.util.ArrayList;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceHolder> {
    private static final String TAG = "ServicesAdapter:ads";
    private ArrayList<Service> items;
    private BaseActivity baseActivity;

    public ServicesAdapter(BaseActivity baseActivity, ArrayList<Service> items) {
        this.baseActivity = baseActivity;
        this.items = items;
    }


    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //service
        return new ServiceHolder(ItemServiceBinding.inflate(LayoutInflater
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

    public void addItem(Service item) {
        this.items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(ArrayList<Service> items) {
        if (items != null) {
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void deleteItem(Service item) {
        int i = this.items.indexOf(item);
        this.items.remove(item);
        notifyItemRemoved(i);
    }

    public void deleteAll() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public class ServiceHolder extends RecyclerView.ViewHolder {

        private ItemServiceBinding binding;
        private Service service;

        public ServiceHolder(ItemServiceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(view -> {
                     new NavigateUtil().openServices(baseActivity, service);

            });
        }

        public void setData(Service item) {
            this.service = item;
            binding.tvName.setText(item.getName());
//            binding.tvDetails.setText(item.getDetails());
            binding.ivIcon.setImageResource(item.getIcon());
        }
    }
}
