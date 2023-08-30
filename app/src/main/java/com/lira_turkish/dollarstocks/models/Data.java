package com.lira_turkish.dollarstocks.models;

public class Data {
    private String link;

    public Data(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "{" +
                "link='" + link + '\'' +
                '}';
    }
}
