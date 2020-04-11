package com.soulsurfer.android.provider;

import android.app.Application;
import android.util.Log;

import com.soulsurfer.android.model.repository.SoulSurferRepository;
import com.soulsurfer.android.utils.Constants;

public class SoulSurferInitProvider extends EmptyContentProvider {

    @Override
    public boolean onCreate() {
        Log.d(Constants.TAG, "SoulSurferInitProvider::onCreate()");
        SoulSurferRepository.getInstance((Application) getContext());
        return super.onCreate();
    }
}
