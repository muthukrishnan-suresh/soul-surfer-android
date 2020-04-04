package com.soulsurfer.android.provider;

import android.app.Application;
import android.util.Log;

import com.soulsurfer.android.helper.SoulSurferHelper;
import com.soulsurfer.android.utils.Constants;

public class SoulSurferInitProvider extends EmptyContentProvider {

    @Override
    public boolean onCreate() {
        Log.d(Constants.TAG, "SoulSurferInitProvider::onCreate()");
        SoulSurferHelper.getInstance((Application) getContext()).init();
        return super.onCreate();
    }
}
