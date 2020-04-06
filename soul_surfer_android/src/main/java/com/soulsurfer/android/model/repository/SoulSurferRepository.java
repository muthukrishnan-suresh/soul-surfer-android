package com.soulsurfer.android.model.repository;

import android.app.Application;
import android.content.IntentFilter;
import android.util.Log;

import com.soulsurfer.android.listener.ApplicationStateListener;
import com.soulsurfer.android.model.bean.Provider;
import com.soulsurfer.android.model.bean.response.ConfigResponse;
import com.soulsurfer.android.model.network.RequestHelper;
import com.soulsurfer.android.receiver.SoulSurferReceiver;
import com.soulsurfer.android.utils.AppExecutors;
import com.soulsurfer.android.utils.Constants;

import java.util.HashMap;
import java.util.List;

public class SoulSurferRepository {

    private static SoulSurferRepository repo;

    private ConfigResponse configResponse;
    private List<Provider> providers;
    private HashMap<String, String> providerSchemaToOEmbedUrlMap = new HashMap<>();

    public SoulSurferRepository(Application application) {
        registerReceiver(application);
        ApplicationStateListener.listen(application);
    }

    public static synchronized SoulSurferRepository getInstance(Application application) {
        if (repo == null) {
            synchronized (SoulSurferRepository.class) {
                repo = new SoulSurferRepository(application);
            }
        }
        return repo;
    }

    private void registerReceiver(Application application) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_APP_STATE_CHANGED);
        intentFilter.addAction(Constants.ACTION_CACHE_LOADED);
        application.registerReceiver(new SoulSurferReceiver(), intentFilter);
    }

    public void onAppStateChanged() {
        boolean isForeground = ApplicationStateListener.isForeground();
        Log.d(Constants.TAG, "Application foreground - " + isForeground);

        if (isForeground) {
            updateCache();
        }
    }

    private void updateCache() {
        AppExecutors.runOnNetworkThread(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                if (configResponse == null) {
                    configResponse = RequestHelper.getConfig();
                }

                if (providers == null && configResponse != null) {
                    providers = RequestHelper.getProviders(configResponse.getOembedConfig().getEndPoint());
                }

                if (providers != null) {
                    providerSchemaToOEmbedUrlMap.clear();
                    for (Provider provider : providers) {
                        if (provider.getEndpoints() != null) {
                            for (Provider.Endpoint endpoint : provider.getEndpoints()) {
                                if (endpoint.getSchemes() != null) {
                                    for (String schema : endpoint.getSchemes()) {
                                        providerSchemaToOEmbedUrlMap.put(schema, endpoint.getUrl());
                                    }
                                }
                            }
                        }
                    }
                }

                Log.d(Constants.TAG, "Cache loaded in " + (System.currentTimeMillis() - startTime));
            }
        });
    }
}
