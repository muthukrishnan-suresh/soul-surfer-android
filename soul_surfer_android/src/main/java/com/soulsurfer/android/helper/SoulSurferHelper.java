package com.soulsurfer.android.helper;

import android.app.Application;
import android.content.IntentFilter;
import android.util.Log;

import com.soulsurfer.android.listener.ApplicationStateListener;
import com.soulsurfer.android.receiver.SoulSurferReceiver;
import com.soulsurfer.android.utils.Constants;

public class SoulSurferHelper {

    private static SoulSurferHelper soulSurferHelper;

    private Application application;

    private SoulSurferHelper(Application application) {
        this.application = application;
    }

    public static synchronized SoulSurferHelper getInstance(Application application) {
        if (soulSurferHelper == null) {
            synchronized (SoulSurferHelper.class) {
                soulSurferHelper = new SoulSurferHelper(application);
            }
        }
        return soulSurferHelper;
    }

    public void init() {
        registerReceiver(application);
        ApplicationStateListener.listen(application);
    }

    private void registerReceiver(Application application) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_APP_STATE_CHANGED);
        application.registerReceiver(new SoulSurferReceiver(), intentFilter);
    }

    public void onAppStateChanged() {
        boolean isForeground = ApplicationStateListener.isForeground();
        Log.d(Constants.TAG, "Application foreground - " + isForeground);
    }
}
