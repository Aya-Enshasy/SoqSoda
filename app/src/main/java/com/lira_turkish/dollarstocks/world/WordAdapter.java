package com.lira_turkish.dollarstocks.world;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.databinding.ItemWorldCurrencyBinding;
import com.lira_turkish.dollarstocks.models.CurrentCurrency;
import com.lira_turkish.dollarstocks.world.model.Rate;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {
    private List<Rate> list  ;
    Context context;
    CurrentCurrency item;
    ArrayList<CurrentCurrency> cur = new ArrayList<>();



    public WordAdapter(Context context,List<Rate> list){
        this.context= context;
        this.list= list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemWorldCurrencyBinding binding = ItemWorldCurrencyBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.currencySymbol.setText(list.get(position).getCode());
        holder.binding.value.setText(list.get(position).getValue()+"");
        for (int i=0;i<list.size();i++) {
            switch (list.get(position).getCode()){
                case "TRY":
                    holder.binding.name.setText(context.getString(R.string.turkish_lira));
                    break;
                case "EUR":
                    holder.binding.name.setText(context.getString(R.string.euro));
                    break;
                case "GBP":
                    holder.binding.name.setText(context.getString(R.string.pound_sterling));
                    break;
                case "BTC":
                    holder.binding.name.setText(context.getString(R.string.bitcoin));
                    break;
                case "AUD":
                    holder.binding.name.setText(context.getString(R.string.australian_dollar));
                    break;
                case "SAR":
                    holder.binding.name.setText(context.getString(R.string.saudi_arabia_real));
                    break;
                case "AED":
                    holder.binding.name.setText(context.getString(R.string.emirates_dirham));
                    break;
                case "KWD":
                    holder.binding.name.setText(context.getString(R.string.kuwaiti_dinar));
                    break;
                case "JOD":
                    holder.binding.name.setText(context.getString(R.string.jordan_dinar));
                    break;
                case "QAR":
                    holder.binding.name.setText(context.getString(R.string.qatari_riyal));
                    break;
                case "BHD":
                    holder.binding.name.setText(context.getString(R.string.bahraini_dinar));
                    break;
                case "CAD":
                    holder.binding.name.setText(context.getString(R.string.canadian_dollar));
                    break;
                case "RUB":
                    holder.binding.name.setText(context.getString(R.string.russian_ruble));
                    break;
                case "JPY":
                    holder.binding.name.setText(context.getString(R.string.japanese_yen));
                    break;
                case "CNH":
                    holder.binding.name.setText(context.getString(R.string.renminbi));
                    break;
                case "DZD":
                    holder.binding.name.setText(context.getString(R.string.algerian_dinar));
                    break;
                case "ARS":
                    holder.binding.name.setText(context.getString(R.string.argentine_peso));
                    break;
                case "CHF":
                    holder.binding.name.setText(context.getString(R.string.swiss_franc));
                    break;
                case "INR":
                    holder.binding.name.setText(context.getString(R.string.indian_rupee));
                    break;
                case "MAD":
                    holder.binding.name.setText(context.getString(R.string.moroccan_dirham));
                    break;
                case "IRR":
                    holder.binding.name.setText(context.getString(R.string.iranian_rial));
                    break;
                case "LYD":
                    holder.binding.name.setText(context.getString(R.string.libyan_dinar));
                    break;
                case "EGP":
                    holder.binding.name.setText(context.getString(R.string.egyptian_pound));
                    break;
                case "TND":
                    holder.binding.name.setText(context.getString(R.string.tunisian_dinar));
                    break;
                case "ZWL":
                    holder.binding.name.setText(context.getString(R.string.zimbabwean_dollar));
                    break;
            }

        }


    }
    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }


    public void deleteItem(CurrentCurrency item) {
        int i = this.list.indexOf(item);
        this.list.remove(item);
        notifyItemRemoved(i);
    }

    public void deleteAll() {
        this.list.clear();
        notifyDataSetChanged();
    }
    public void setData(List<Rate> list) {
        this.list = list;
        notifyDataSetChanged();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder  {
        ItemWorldCurrencyBinding binding;
        public ViewHolder(ItemWorldCurrencyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }}
