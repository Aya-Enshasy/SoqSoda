package com.lira_turkish.dollarstocks.models;

public class NotificationData {

    private String title;
    private String body;
    private String click_action;

    public NotificationData(String title, String body) {
        this.title = title;
        this.body = body;
        click_action = "OPEN_URL";
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getClick_action() {
        return click_action;
    }

    @Override
    public String toString() {
        return "{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", click_action='" + body + '\'' +
                '}';
    }
}