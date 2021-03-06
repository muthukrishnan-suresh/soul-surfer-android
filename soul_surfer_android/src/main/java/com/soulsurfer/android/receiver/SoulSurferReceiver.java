package com.soulsurfer.android.receiver;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.soulsurfer.android.model.repository.SoulSurferRepository;
import com.soulsurfer.android.utils.Constants;

public class SoulSurferReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(Constants.TAG, "SoulSurferReceiver::onReceive()");
        Log.d(Constants.TAG, "SoulSurferReceiver.ACTION " + intent.getAction());

        String action = intent.getAction();
        if (action == null) {
            return;
        }

        switch (action) {
            case Constants.ACTION_APP_STATE_CHANGED:
                SoulSurferRepository.getInstance((Application) context).onAppStateChanged();
                break;
            case Constants.ACTION_CACHE_LOADED:
                Log.d(Constants.TAG, "Url Schema Mapping loaded");
                break;
        }
    }
}
