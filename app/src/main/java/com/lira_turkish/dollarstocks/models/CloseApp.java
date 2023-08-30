package com.lira_turkish.dollarstocks.models;

public class CloseApp {
    String title;
    String url;
    int ActiveValue;

    public CloseApp(String title, String url, int activeValue) {
        this.title = title;
        this.url = url;
        ActiveValue = activeValue;
    }

    public CloseApp() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getActiveValue() {
        return ActiveValue;
    }

    public void setActiveValue(int activeValue) {
        ActiveValue = activeValue;
    }
}
