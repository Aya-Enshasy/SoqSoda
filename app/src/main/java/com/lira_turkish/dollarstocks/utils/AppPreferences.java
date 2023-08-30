package com.lira_turkish.dollarstocks.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.lira_turkish.dollarstocks.models.Response;

public class AppPreferences {

    public static AppPreferences sessionInstance;
    public static String PREF_NAME = "Preferences";
    public SharedPreferences prefs;
    public final static String KEY_APP_LANGUAGE = "app_language";
    public final static String KEY_FIRST_RUN = "first_run";
    public final static String KEY_ADMIN = "admin";
    public final static String KEY_NOTIFICATION = "notification";
    public final static String KEY_RESPONSE = "response";
    public SharedPreferences.Editor editor;
    private static final String KEY_DATE = "date";


    public static AppPreferences getInstance(Context context) {
        if (sessionInstance == null) {
            sessionInstance = new AppPreferences(context);
        }
        return sessionInstance;
    }

    private AppPreferences(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public boolean setStringPreferences(String key, String value) {
        return prefs.edit().putString(key, value).commit();
    }

    public String getStringPreferences(String key) {

        return prefs.getString(key, "");
    }

    public boolean setIntegerPreferences(String key, int value) {
        return prefs.edit().putInt(key, value).commit();
    }

    public int getIntegerPreferences(String key) {

        return prefs.getInt(key, -1);
    }

    public boolean setBooleanPreferences(String key, boolean value) {
        return prefs.edit().putBoolean(key, value).commit();
    }

    public boolean getBooleanPreferences(String key) {

        return prefs.getBoolean(key, false);
    }

    public boolean setLongPreferences(String key, double value) {
        return prefs.edit().putLong(key, (long) value).commit();
    }

    public Long getLongPreferences(String key) {

        return prefs.getLong(key, -1);
    }

    public void setFirstRun() {
        editor.putBoolean(KEY_FIRST_RUN, false);
        editor.apply();
    }

    public boolean isFirstRun() {
        return prefs.getBoolean(KEY_FIRST_RUN, true);
    }

    public void setAppLanguage(String appLanguage) {
        editor.putString(KEY_APP_LANGUAGE, appLanguage);
        editor.apply();
    }

    public void setAdmin() {
        editor.putBoolean(KEY_ADMIN, true);
        editor.apply();
    }

    public boolean isAdmin() {
        return prefs.getBoolean(KEY_ADMIN, false);
    }

    public void setNotification(boolean notification) {
        editor.putBoolean(KEY_NOTIFICATION, notification);
        editor.apply();
    }

    public boolean isNotificationOn() {
        return prefs.getBoolean(KEY_NOTIFICATION, true);
    }

    public boolean isNotificationOff() {
        return prefs.getBoolean(KEY_NOTIFICATION, false);
    }

    public void setDate(String date) {
        editor.putString(KEY_DATE, date);
        editor.apply();
    }

    public String getDate() {
        return prefs.getString(KEY_DATE, "2022-10-1");
    }

    public void setDataForStorage(Response data) {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        editor.putString(KEY_RESPONSE, json);
        editor.apply();
    }

    public Response getDataForStorage() {
        Gson gson = new Gson();
        String json = prefs.getString(KEY_RESPONSE, "");
        Response response = gson.fromJson(json, Response.class);
        return response != null ? response : new Response();
    }

    public void clean() {
        editor.clear();
        editor.apply();
    }
}