package com.lira_turkish.dollarstocks.feature.currency.fragments.expectation.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.lira_turkish.dollarstocks.databinding.ItemExpectedBinding;
import com.lira_turkish.dollarstocks.feature.currency.fragments.expectation.ExpectationFragment;
import com.lira_turkish.dollarstocks.models.Expectation;
import com.lira_turkish.dollarstocks.utils.util.ResourceUtil;
import com.lira_turkish.dollarstocks.utils.util.ToolUtil;

import java.util.ArrayList;

public class ExpectationsAdapter extends RecyclerView.Adapter<ExpectationsAdapter.ExpectationHolder> {

    private ArrayList<Expectation> items;
    private ExpectationFragment fragment;

    public ExpectationsAdapter(ExpectationFragment fragment, ArrayList<Expectation> items) {
        this.fragment = fragment;
        this.items = items;
    }

    @NonNull
    @Override
    public ExpectationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //expectation
        return new ExpectationHolder(ItemExpectedBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExpectationHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Expectation item) {
        this.items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(ArrayList<Expectation> items) {
        if (items != null) {
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void deleteItem(Expectation item) {
        int i = this.items.indexOf(item);
        this.items.remove(item);
        notifyItemRemoved(i);
    }

    public void deleteAll() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public class ExpectationHolder extends RecyclerView.ViewHolder {

        private ItemExpectedBinding binding;
        private Expectation expectation;

        public ExpectationHolder(ItemExpectedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
//            binding.getRoot().setOnClickListener(view -> fragment.showExpectationDialog(expectation, true));
        }

        public void setData(Expectation item) {
            this.expectation = item;
            new ResourceUtil().loadImage(fragment.requireContext(), item.getImg(), binding.image);
            binding.name.setText(item.getTitle());
            binding.details.setText(item.getContent());
            binding.date.setText(ToolUtil.getDate(item.getTimestamp().toDate().getTime()));
        }
    }
}
