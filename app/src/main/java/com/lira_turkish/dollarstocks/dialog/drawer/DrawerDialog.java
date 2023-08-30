package com.lira_turkish.dollarstocks.dialog.drawer;


import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.LangKey;

import android.app.Dialog;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;


import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.databinding.DialogDrawerBinding;
import com.lira_turkish.dollarstocks.dialog.drawer.adapter.DrawersAdapter;
import com.lira_turkish.dollarstocks.models.Drawer;
import com.lira_turkish.dollarstocks.utils.AppContent;
import com.lira_turkish.dollarstocks.utils.AppController;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.language.BaseActivity;

import java.util.ArrayList;
import java.util.Objects;

public class DrawerDialog extends Dialog {

    private DialogDrawerBinding binding;
    private BaseActivity baseActivity;

    public DrawerDialog(@NonNull BaseActivity baseActivity) {
        super(baseActivity);
        this.baseActivity = baseActivity;
        //drawer
        binding = DialogDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String lang = AppPreferences.getInstance(getContext()).prefs.getString(LangKey, "en");


        WindowManager.LayoutParams params = Objects.requireNonNull(getWindow()).getAttributes();
        params.width = (int) (baseActivity.getResources().getDisplayMetrics().widthPixels * 0.8);
        params.height = (int) (baseActivity.getResources().getDisplayMetrics().heightPixels *5);
        getWindow().setGravity(lang.equals("ar") ? Gravity.END : Gravity.END | Gravity.CENTER_VERTICAL);
        getWindow().setBackgroundDrawableResource(R.drawable.transparent);
        click();
        setData();
    }



    private void click() {

    }

    private void setData() {
        ArrayList<Drawer> list = new ArrayList<>();

        list.add(new Drawer(AppContent.NOTIFICATIONS, baseActivity.getString(R.string.notifications), R.drawable.ic_notification_bing_svgrepo_com));
        if (AppPreferences.getInstance(getContext()).isAdmin()){
            list.add(new Drawer(AppContent.ADMIN_NOTIFICATIONS, baseActivity.getString(R.string.admin_notifications), R.drawable.ic_notification_bing_svgrepo_com));
            list.add(new Drawer(AppContent.CLOSE_APP, baseActivity.getString(R.string.close_app), R.drawable.ic_baseline_close_24));
        }
        list.add(new Drawer(AppContent.CHANGE_LANGUAGE, baseActivity.getString(R.string.change_language), R.drawable.ic_languageminor_svgrepo_com));
        list.add(new Drawer(AppContent.SHARE, baseActivity.getString(R.string.share), R.drawable.ic_share_svgrepo_com));
        list.add(new Drawer(AppContent.RATE, baseActivity.getString(R.string.rate), R.drawable.ic_star_solid_svgrepo_com));
        list.add(new Drawer(AppContent.PROBLEM, baseActivity.getString(R.string.problem), R.drawable.ic_warning_filled_svgrepo_com));
        list.add(new Drawer(AppContent.PRIVACY, baseActivity.getString(R.string.privacy), R.drawable.ic_privacy));
        DrawersAdapter adapter = new DrawersAdapter(baseActivity, list, this);
        binding.rvDrawer.setItemAnimator(new DefaultItemAnimator());
        binding.rvDrawer.setAdapter(adapter);
    }
}