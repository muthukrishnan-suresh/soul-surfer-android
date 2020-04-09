package com.soulsurfer.android.model.repository;

import android.app.Application;
import android.content.IntentFilter;
import android.util.Log;

import com.google.gson.Gson;
import com.soulsurfer.android.PageInfo;
import com.soulsurfer.android.PageInfoListener;
import com.soulsurfer.android.helper.MetaHelper;
import com.soulsurfer.android.listener.ApplicationStateListener;
import com.soulsurfer.android.model.bean.Provider;
import com.soulsurfer.android.model.bean.response.ConfigResponse;
import com.soulsurfer.android.model.network.RequestHelper;
import com.soulsurfer.android.receiver.SoulSurferReceiver;
import com.soulsurfer.android.utils.AppExecutors;
import com.soulsurfer.android.utils.Constants;
import com.soulsurfer.android.utils.StringUtils;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static void load(final String url, final PageInfoListener listener) {
        (new PageInfoLoader(url, listener, repo.providerSchemaToOEmbedUrlMap)).load();
    }
}
