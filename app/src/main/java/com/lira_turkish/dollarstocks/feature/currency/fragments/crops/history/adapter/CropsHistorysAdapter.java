package com.lira_turkish.dollarstocks.feature.currency.fragments.crops.history.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.lira_turkish.dollarstocks.crop.model.CropHistoryData;
import com.lira_turkish.dollarstocks.databinding.ItemHistoryBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CropsHistorysAdapter extends RecyclerView.Adapter<CropsHistorysAdapter.ViewHolder> {

    private List<CropHistoryData> list;
    private Context context;

    public CropsHistorysAdapter(Context context, List<CropHistoryData> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemHistoryBinding binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.counter.setText(String.valueOf(position + 1));
        holder.binding.price.setText(list.get(position).getPrice());

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sd.parse(list.get(position).getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sd = new SimpleDateFormat("yyyy-MM-dd");
        holder.binding.date.setText(sd.format(d));


    }
    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }


    public void setData(List<CropHistoryData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addToList(List<CropHistoryData> myList){
        list.addAll(myList);
        notifyDataSetChanged();
    }

public static class ViewHolder extends RecyclerView.ViewHolder  {
    ItemHistoryBinding binding;
    public ViewHolder(ItemHistoryBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

    }
}

}