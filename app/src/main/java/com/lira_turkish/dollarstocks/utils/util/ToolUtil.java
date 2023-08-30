package com.lira_turkish.dollarstocks.utils.util;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.utils.AppContent;
import com.lira_turkish.dollarstocks.utils.AppController;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import okhttp3.ResponseBody;

public class ToolUtil {
    public static boolean checkTheInternet() {
        ConnectivityManager cm = (ConnectivityManager) AppController.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void hideSoftKeyboard(Context context, View view) {
        if (view == null)
            return;
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showLongToast(String s, Context context) {
///        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }


    public static String showError(Context context, ResponseBody s) {
        String message = context.getString(R.string.error);
        try {
            JSONObject jObjError = new JSONObject(s.string());
            message = jObjError.getString(AppContent.FIREBASE_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    public static String getDate(long timeInMillis) {
        Locale locale = new Locale("en");
        Calendar cal = Calendar.getInstance(locale);
        cal.setTimeInMillis(timeInMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
        return dateFormat.format(cal.getTime());
    }

    public static String getDate(String date) {
        Locale locale = new Locale("en");
        Calendar cal = Calendar.getInstance(locale);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", locale);
        try {
            cal.setTimeInMillis(Objects.requireNonNull(sdf.parse(date)).getTime());
        } catch (ParseException e) {
            cal.setTimeInMillis(System.currentTimeMillis());
        }
        return sdf.format(cal.getTime());
    }

    public static String getDayOfWeek(long update) {
        Locale locale = new Locale("en");
        Calendar cal = Calendar.getInstance(locale);
        cal.setTimeInMillis(update);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", locale);
        return sdf.format(cal.getTime());
    }

    public static String getTimeForScreen(long time) {
        Locale locale = new Locale("en");
        Calendar cal = Calendar.getInstance(locale);
        cal.setTimeInMillis(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("K:mm aaa", locale);
        return dateFormat.format(cal.getTime());
    }

    public static String getTime(long time) {
        Locale locale = new Locale("en");
        Calendar cal = Calendar.getInstance(locale);
        cal.setTimeInMillis(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", locale);
        return dateFormat.format(cal.getTime());
    }

    public static String compareTwoTimes(Context context, String updateTime) {
        Locale locale = new Locale("en");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
        try {
            long update = Objects.requireNonNull(sdf.parse(updateTime)).getTime() + (3 * 60 * 60 * 1000);
            long current = System.currentTimeMillis() - (2 * 60 * 60 * 1000);
//            if (update > current) {
//                current = System.currentTimeMillis() - (2 * 60 * 60 * 1000);
//            }
            Log.e("timedefferent", current + " -> " + update + " = " + (current - update));
            long result = current - update;
            int h = Integer.parseInt(getTime(result).split(":")[0]);
            int m = Integer.parseInt(getTime(result).split(":")[1]);
            int s = Integer.parseInt(getTime(result).split(":")[2]);

            if (h > 0) {
                return context.getString(R.string.updated_before_hour, String.valueOf(h));
            } else if (m > 0) {
                return context.getString(R.string.updated_before_minute, String.valueOf(m));
            } else {
                return context.getString(R.string.updated_before_second, String.valueOf(s));
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void openTelegramContact(Context context, String url) {
        Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(telegram);
    }

    public static View getCurrentViewFromViewPager(ViewPager viewPager) {
        try {
            final int currentItem = viewPager.getCurrentItem();
            for (int i = 0; i < viewPager.getChildCount(); i++) {
                final View child = viewPager.getChildAt(i);
                final ViewPager.LayoutParams layoutParams = (ViewPager.LayoutParams) child.getLayoutParams();

                Field f = layoutParams.getClass().getDeclaredField("position"); //NoSuchFieldException
                f.setAccessible(true);
                int position = (Integer) f.get(layoutParams); //IllegalAccessException

                if (!layoutParams.isDecor && currentItem == position) {
                    return child;
                }
            }
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            //Log.e(TAG, e.toString());
        }
        return null;
    }

    public static boolean editTextsFilled(EditText[] editTexts, Context context) {
        for (EditText editText : editTexts) {
            if (editText.getText().toString().trim().equals("")) {
                editText.setError(context.getString(R.string.field_required));
                return false;
            }
        }
        return true;
    }
}
