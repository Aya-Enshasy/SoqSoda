package com.lira_turkish.dollarstocks.feature.currency.fragments.crops.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.databinding.ItemCropsBinding;
import com.lira_turkish.dollarstocks.dialog.ad.AdDialog;
import com.lira_turkish.dollarstocks.dialog.crops.CropsDialog;
import com.lira_turkish.dollarstocks.feature.currency.fragments.crops.CropsFragment;
import com.lira_turkish.dollarstocks.feature.currency.fragments.crops.history.CropsHistoryActivity;
import com.lira_turkish.dollarstocks.feature.currency.fragments.crops.model.CropsData;
import com.lira_turkish.dollarstocks.models.Crops;
import com.lira_turkish.dollarstocks.utils.AppContent;
import com.lira_turkish.dollarstocks.utils.util.ResourceUtil;

import java.util.List;

public class CropsAdapter extends RecyclerView.Adapter<CropsAdapter.CropsHolder> {

    private CropsFragment fragment;
    private AdDialog.Listener listener;
    private CropsDialog.Listener mlistener;
    List<CropsData> list;

    public CropsAdapter(CropsFragment fragment, List<CropsData> list) {
        this.fragment = fragment;
        this.list = list;
    }

    public void setListener(AdDialog.Listener listener) {
        this.listener = listener;
    }

    public void setMlistener(CropsDialog.Listener mlistener) {
        this.mlistener = mlistener;
    }

    @NonNull
    @Override
    public CropsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //crops
        return new CropsHolder(ItemCropsBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CropsHolder holder, int position) {
        holder.binding.name.setText(list.get(position).getName());
        holder.binding.price.setText(list.get(position).getPrice());
        holder.binding.currency.setText(list.get(position).getRelative());
        holder.binding.upOrDown.setImageResource(list.get(position).getState().equals("0") ? R.drawable.ic_cursor_down : R.drawable.ic_cursor_up);
        if (list.get(position).getImg() != null && !list.get(position).getImg().isEmpty())
            new ResourceUtil().loadImage(fragment.requireContext(), list.get(position).getImg(), holder.binding.image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(CropsData item) {
        this.list.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(List<CropsData> items) {
        if (items != null) {
            this.list.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void deleteItem(Crops item) {
        int i = this.list.indexOf(item);
        this.list.remove(item);
        notifyItemRemoved(i);
    }

    public void deleteAll() {
        this.list.clear();
        notifyDataSetChanged();
    }

    public void setData(List<CropsData> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public class CropsHolder extends RecyclerView.ViewHolder {

        private ItemCropsBinding binding;
        private Crops crops;

        public CropsHolder(ItemCropsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.background.setOnClickListener(view -> {
                Intent intent = new Intent(fragment.requireContext(), CropsHistoryActivity.class);
                intent.putExtra(AppContent.CROPS_ID, crops.getId());
                intent.putExtra(AppContent.CROPS_NAME, crops.getName());
                fragment.requireContext().startActivity(intent);
            });
            binding.linear.setOnClickListener(view -> fragment.showCorpDialog(crops, true));
        }


    }
}
