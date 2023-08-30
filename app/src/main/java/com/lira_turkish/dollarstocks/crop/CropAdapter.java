package com.lira_turkish.dollarstocks.crop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.databinding.ItemCropsBinding;
import com.lira_turkish.dollarstocks.feature.currency.fragments.crops.history.CropsHistoryActivity;
import com.lira_turkish.dollarstocks.feature.currency.fragments.crops.model.CropsData;
import com.lira_turkish.dollarstocks.utils.AppContent;
import com.lira_turkish.dollarstocks.utils.MyInterface;

import java.util.List;


public class CropAdapter extends RecyclerView.Adapter<CropAdapter.ViewHolder> {
    private List<CropsData> list  ;
    Context context;
    private static MyInterface listener ;


    public CropAdapter(Context context,MyInterface listener,List<CropsData> list ){
        this.context= context;
        this.listener= listener;
        this.list= list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemCropsBinding binding = ItemCropsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.name.setText(list.get(position).getName());
        holder.binding.price.setText(list.get(position).getPrice());
        holder.binding.currency.setText(list.get(position).getCurrency());
        holder.binding.upOrDown.setImageResource(list.get(position).getState()== 0 ? R.drawable.ic_cursor_down : R.drawable.ic_cursor_up);

        holder.binding.upOrDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyDataSetChanged();
                listener.onItemClick(list.get(position).getId());
                Log.e("id",list.get(position).getId()+"");
            }
        });

        holder.binding.background.setOnClickListener(view -> {
            Intent intent = new Intent(context, CropsHistoryActivity.class);
            intent.putExtra(AppContent.CROPS_ID, list.get(position).getId());
            intent.putExtra(AppContent.CROPS_NAME, list.get(position).getName());
            context.startActivity(intent);
        });

    }
    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }


    public void setData(List<CropsData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addToList(List<CropsData> myList){
        list.addAll(myList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        ItemCropsBinding binding;
        public ViewHolder(ItemCropsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

}
