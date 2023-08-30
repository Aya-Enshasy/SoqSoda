package com.lira_turkish.dollarstocks.utils.util;


import static com.lira_turkish.dollarstocks.utils.util.ToolUtil.checkTheInternet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.models.Currency;
import com.lira_turkish.dollarstocks.models.CurrentCurrency;
import com.lira_turkish.dollarstocks.models.History;


import java.util.ArrayList;

public class ResourceUtil {

    public void loadImage(Context context, String url, ImageView imgView) {
        if (url == null) {
            return;
        }
        if (checkTheInternet()) {
            imgView.setClickable(false);

            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .placeholder(R.drawable.ic_logo)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model
                                , Target<Bitmap> target, boolean isFirstResource) {
                            imgView.setClickable(true);
                            Log.e(getClass().getName() + ":imageload", e.getLocalizedMessage());
                            e.printStackTrace();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model
                                , Target<Bitmap> target, DataSource dataSource
                                , boolean isFirstResource) {
                            imgView.setClickable(true);
                            return false;
                        }
                    })
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource
                                , @Nullable Transition<? super Bitmap> transition) {
                            imgView.setImageBitmap(resource);
                            imgView.setClickable(true);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                            imgView.setClickable(true);
                        }
                    });
        } else {
            Toast.makeText(context, R.string.no_connection, Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<CurrentCurrency> getCurrencies(Context context, Currency currency) {
        ArrayList<CurrentCurrency> cur = new ArrayList<>();
        cur.add(new CurrentCurrency("TRY", context.getString(R.string.turkish_lira), currency.getTry()));
        cur.add(new CurrentCurrency("EUR", context.getString(R.string.euro), currency.getEur()));
        cur.add(new CurrentCurrency("GBP", context.getString(R.string.pound_sterling), currency.getGbp()));
        cur.add(new CurrentCurrency("BTC", context.getString(R.string.bitcoin), currency.getBtc()));
        cur.add(new CurrentCurrency("AUD", context.getString(R.string.australian_dollar), currency.getAud()));
        cur.add(new CurrentCurrency("SAR", context.getString(R.string.saudi_arabia_real), currency.getSar()));
        cur.add(new CurrentCurrency("AED", context.getString(R.string.emirates_dirham), currency.getAed()));
        cur.add(new CurrentCurrency("KWD", context.getString(R.string.kuwaiti_dinar), currency.getKwd()));
        cur.add(new CurrentCurrency("JOD", context.getString(R.string.jordan_dinar), currency.getJod()));
        cur.add(new CurrentCurrency("QAR", context.getString(R.string.qatari_riyal), currency.getQar()));
        cur.add(new CurrentCurrency("BHD", context.getString(R.string.bahraini_dinar), currency.getBhd()));
        cur.add(new CurrentCurrency("CAD", context.getString(R.string.canadian_dollar), currency.getCad()));
        cur.add(new CurrentCurrency("RUB", context.getString(R.string.russian_ruble), currency.getRub()));
        cur.add(new CurrentCurrency("JPY", context.getString(R.string.japanese_yen), currency.getJpy()));
        cur.add(new CurrentCurrency("CNH", context.getString(R.string.renminbi), currency.getCnh()));
        cur.add(new CurrentCurrency("DZD", context.getString(R.string.algerian_dinar), currency.getDzd()));
        cur.add(new CurrentCurrency("ARS", context.getString(R.string.argentine_peso), currency.getArs()));
        cur.add(new CurrentCurrency("CHF", context.getString(R.string.swiss_franc), currency.getChf()));
        cur.add(new CurrentCurrency("INR", context.getString(R.string.indian_rupee), currency.getInr()));
        cur.add(new CurrentCurrency("MAD", context.getString(R.string.moroccan_dirham), currency.getMad()));
        cur.add(new CurrentCurrency("IRR", context.getString(R.string.iranian_rial), currency.getIrr()));
        cur.add(new CurrentCurrency("LYD", context.getString(R.string.libyan_dinar), currency.getLyd()));
        cur.add(new CurrentCurrency("EGP", context.getString(R.string.egyptian_pound), currency.getEgp()));
        cur.add(new CurrentCurrency("TND", context.getString(R.string.tunisian_dinar), currency.getTnd()));
        return cur;
    }

    public History getMaxValue(ArrayList<History> histories) {
        if (histories != null && !histories.isEmpty()) {
            History max = histories.get(0);
            for (History history : histories) {
                if (Double.parseDouble(history.getOption1()) > Double.parseDouble(max.getOption1())) {
                    max = history;
                }
            }
            return max;
        }
        return new History();
    }

    public History getMinValue(ArrayList<History> histories) {
        if (histories != null && !histories.isEmpty()) {
            History min = histories.get(0);
            for (History history : histories) {
                if (Double.parseDouble(history.getOption1()) < Double.parseDouble(min.getOption1())) {
                    min = history;
                }
            }
            return min;
        }
        return new History();
    }
}
