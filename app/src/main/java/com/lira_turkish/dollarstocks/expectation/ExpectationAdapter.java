package com.lira_turkish.dollarstocks.expectation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import com.lira_turkish.dollarstocks.databinding.ItemExpectedBinding;

import java.util.List;

public class ExpectationAdapter extends RecyclerView.Adapter<ExpectationAdapter.ViewHolder> {
    private List<ExpectationData> list  ;
    Context context;

    public ExpectationAdapter(Context context,List<ExpectationData> list){
        this.context= context;
        this.list= list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemExpectedBinding binding = ItemExpectedBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.name.setText(list.get(position).getContent());
        holder.binding.date.setText(list.get(position).getUpdatedAt());
        holder.binding.details.setVisibility(View.GONE);

    }
    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }


    public void setData(List<ExpectationData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addToList(List<ExpectationData> myList){
        list.addAll(myList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        ItemExpectedBinding binding;
        public ViewHolder(ItemExpectedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

}
