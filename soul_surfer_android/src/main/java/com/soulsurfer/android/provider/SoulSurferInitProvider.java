package com.soulsurfer.android.provider;

import android.util.Log;

public class SoulSurferInitProvider extends EmptyContentProvider {

    @Override
    public boolean onCreate() {
        Log.d("SoulSurfer", "SoulSurferInitProvider called");
        return super.onCreate();
    }
}
