package com.soulsurfer.android.utils;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class BroadcastUtils {

    public static void sendBroadcast(Application application, String action) {
        sendBroadcast(application, action, null);
    }

    public static void sendBroadcast(Application application, String action, Bundle bundle) {
        Log.d(Constants.TAG, "BroadcastUtils::sendBroadcast() " + action);
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        application.sendBroadcast(intent);
    }
}
