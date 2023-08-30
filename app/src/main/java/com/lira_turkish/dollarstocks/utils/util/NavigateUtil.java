package com.lira_turkish.dollarstocks.utils.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.feature.currency.CurrencyActivity;
import com.lira_turkish.dollarstocks.feature.data.CurrencyDataActivity;
import com.lira_turkish.dollarstocks.models.CurrencyData;
import com.lira_turkish.dollarstocks.models.Service;
import com.lira_turkish.dollarstocks.utils.AppContent;

public class NavigateUtil {

    public void replaceFragment(FragmentManager fragmentManager, Fragment fragment, int layout) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layout, fragment);
        fragmentTransaction.commit();
    }

    public void activityIntent(Context from, Class to, boolean back) {
        Intent intent = new Intent(from, to);
        if (!back) intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        from.startActivity(intent);
    }

    public void activityIntentWithPage(Context from, Class to, boolean back, int pageNum) {
        Intent intent = new Intent(from, to);
        intent.putExtra(AppContent.PAGE, pageNum);
        if (!back) intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        from.startActivity(intent);
    }

    public void openServices(Context from, Service service) {
        Intent intent = new Intent(from, CurrencyActivity.class);
        intent.putExtra(AppContent.SERVICES, service);
        from.startActivity(intent);
    }

    public void openCurrencyData(Context from, CurrencyData data) {
        Intent intent = new Intent(from, CurrencyDataActivity.class);
        intent.putExtra(AppContent.CURRENCY_DATA, data);
        from.startActivity(intent);
    }


    public void openLink(Context context, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }


    public void makeCall(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    public void openWhatsApp(Context context, String number, String message) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("https://wa.me/" + number + "/?text=" + message);
        i.setData(uri);
        context.startActivity(Intent.createChooser(i, ""));
    }

    @SuppressLint("StringFormatInvalid")
    public void share(Context context, String message) {
        Intent S = new Intent(Intent.ACTION_SEND);
        S.setType("text/plain");
        S.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        S.putExtra(Intent.EXTRA_TEXT, message);
        context.startActivity(Intent.createChooser(S, "choose one"));
    }
}