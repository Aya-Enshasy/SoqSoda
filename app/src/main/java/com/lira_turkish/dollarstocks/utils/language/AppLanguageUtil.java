package com.lira_turkish.dollarstocks.utils.language;//package com.fajer.ls.utils.language;
//
//import android.content.Context;
//import android.content.res.Configuration;
//
//import com.fajer.ls.utils.AppController;
//import com.fajer.ls.utils.AppSharedPreferences;
//
//import java.util.Locale;
//
//public class AppLanguageUtil {
//
//    public static final String ARABIC = "ar";
//    public static final String ENGLISH = "en";
//
//    private static AppLanguageUtil instance = null;
//    private AppSharedPreferences appSettingsPreferences;
//
//    public static synchronized AppLanguageUtil getInstance() {
//        if (instance == null) {
//            instance = new AppLanguageUtil();
//        }
//        return instance;
//    }
//
//    public AppLanguageUtil() {
//        appSettingsPreferences = AppController.getInstance().getSharedPreferences();
//    }
//
//    public void setAppFirstRunLng() {
//        switch (Locale.getDefault().getLanguage()) {
//            case ARABIC:
//                setAppLanguage(getContext(), ARABIC);
//                break;
//            case ENGLISH:
//            default:
//                setAppLanguage(getContext(), ENGLISH);
//                break;
//        }
//        appSettingsPreferences.setFirstRun();
//    }
//
//    private static void setLocale(Context context, String language) {
//        Locale locale = new Locale(language);
//        Locale.setDefault(locale);
//        Configuration config = new Configuration();
//        config.setLocale(locale);
//        context.getResources().updateConfiguration(config,
//                context.getResources().getDisplayMetrics());
//    }
//
//    public void setAppLanguage(Context context, String newLanguage) {
//        setLocale(context, newLanguage);
//        appSettingsPreferences.setAppLanguage(newLanguage);
//    }
//
//    private AppController getContext() {
//        return AppController.getInstance();
//    }
//}
