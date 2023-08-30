package com.lira_turkish.dollarstocks.models;

import com.lira_turkish.dollarstocks.IosNotification;

public class MessageNotification {

    private String to;
    private String collapse_key;
    private String priority;
    private NotificationData notification;
    private IosNotification iosNotification;

    private Data data;

    public MessageNotification(NotificationData notification, String link,IosNotification iosNotification) {
        this.to = "/topics/sp";///topics/dei
        this.collapse_key = "type_a";
        this.priority = "high";
        this.notification = notification;
        this.data = new Data(link);
        this.iosNotification = iosNotification;
    }

    @Override
    public String toString() {
        return "{" +
                "to='" + to + '\'' +
                ", collapse_key='" + collapse_key + '\'' +
                ", priority='" + priority + '\'' +
                ", notification=" + notification +
                ", data=" + data +
                ", iosNotification=" + iosNotification +
                '}';
    }
}