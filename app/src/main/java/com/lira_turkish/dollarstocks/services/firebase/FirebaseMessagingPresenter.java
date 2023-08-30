package com.lira_turkish.dollarstocks.services.firebase;

import android.util.Log;

import java.util.ArrayList;
import java.util.Map;

public class FirebaseMessagingPresenter {

    private static FirebaseMessagingPresenter presenter;
    private final ArrayList<Listener> listeners = new ArrayList<>();

    public static FirebaseMessagingPresenter getInstance() {
        if (presenter == null) presenter = new FirebaseMessagingPresenter();
        return presenter;
    }

    private FirebaseMessagingPresenter() {
    }

    protected void notifyNow(Map<String, String> map) {
        for (Listener listener : listeners) {
            if (listener != null)
                listener.onNotification(map);
        }
    }

    public FirebaseMessagingPresenter setListeners(Listener listener) {
        this.listeners.add(listener);
        Log.e(getClass().getName() + "listeners", listeners.size() + " -> " + listeners.toString());
        return this;
    }

    public void removeListener(Listener listener) {
        this.listeners.remove(listener);
        Log.e(getClass().getName() + "listeners", listeners.size() + " -> " + listeners.toString());
    }

    public interface Listener {
        void onNotification(Map<String, String> map);
    }
}
