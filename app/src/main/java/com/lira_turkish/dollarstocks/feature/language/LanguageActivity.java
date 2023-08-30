package com.lira_turkish.dollarstocks.feature.language;


import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.LangKey;
import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.databinding.ActivityLanguageBinding;
import com.lira_turkish.dollarstocks.feature.splash.SplashActivity;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.language.BaseActivity;

import java.util.Locale;

public class LanguageActivity extends BaseActivity<ActivityLanguageBinding> {

    private LanguagePresenter presenter;
    String currentLanguage;
    Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //language
        setRootView(ActivityLanguageBinding.inflate(getLayoutInflater()));

        setLang();
        changeLang();
    }

    @Override
    protected void setDataInCreate() {
        presenter = new LanguagePresenter(this);
      //  isSelectArabic(language.equals(AppLanguageUtil.ARABIC));
    }

    @Override
    protected void getDataInResume() {

    }

    @Override
    protected void clicks() {

    }

    private void setLang() {
        String lang = AppPreferences.getInstance(getBaseContext()).prefs.getString(LangKey, "");

        if (lang.isEmpty() || lang.equals("en")) {
            currentLanguage = "en";
            getRootView().english.setChecked(false);
        } else {
            getRootView().arabic.setChecked(true);
            currentLanguage = "ar";
        }
    }

    private void changeLang() {
        getRootView().btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getRootView().arabic.isChecked()) {
                    setLocale("ar");
                    AppPreferences.getInstance(getBaseContext()).editor.putString(LangKey,  "ar");
                    AppPreferences.getInstance(getBaseContext()).editor.apply();
                } else {
                    setLocale("en");
                    AppPreferences.getInstance(getBaseContext()).editor.putString(LangKey,  "en");
                    AppPreferences.getInstance(getBaseContext()).editor.apply();
                }
            }
        });

    }

    private void setLocale(String localeName) {
        if (!localeName.equals(currentLanguage)) {
            locale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = locale;
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(getBaseContext(), SplashActivity.class);
            startActivity(refresh);
            finish();

        } else {
            Toast.makeText(getBaseContext(), "Language already selected", Toast.LENGTH_SHORT).show();
        }
    }

}