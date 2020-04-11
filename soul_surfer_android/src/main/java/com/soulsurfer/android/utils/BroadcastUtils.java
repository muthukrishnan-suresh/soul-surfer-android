package com.soulsurfer.android.utils;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class BroadcastUtils {

    private Application application;

    private BroadcastUtils(Application application) {
        this.application = application;
    }

    public static Builder newInstance(Application application) {
        return new Builder(application);
    }

    public void sendBroadcast(String action) {
        sendBroadcast(action, null);
    }

    public void sendBroadcast(String action, Bundle bundle) {
        Log.d(Constants.TAG, "BroadcastUtils::sendBroadcast() " + action);
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        application.sendBroadcast(intent);
    }

    public static final class Builder {
        private Application application;

        private Builder(Application application) {
            this.application = application;
        }

        public BroadcastUtils build() {
            if (application == null) {
                throw new IllegalArgumentException("application can not be null while broadcasting");
            }

            return new BroadcastUtils(application);
        }
    }
}
