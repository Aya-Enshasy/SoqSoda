package com.lira_turkish.dollarstocks.models;

import com.google.firebase.Timestamp;

public class CropsHistory {

    private String id;
    private String value;
    private Timestamp timestamp;

    public CropsHistory() {
    }

    public CropsHistory(String id, String value, Timestamp timestamp) {
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
