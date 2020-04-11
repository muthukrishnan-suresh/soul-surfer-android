package com.soulsurfer.android.model.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.soulsurfer.android.PageInfo;
import com.soulsurfer.android.PageInfoListener;
import com.soulsurfer.android.helper.MetaHelper;
import com.soulsurfer.android.model.network.RequestHelper;
import com.soulsurfer.android.utils.AppExecutors;
import com.soulsurfer.android.utils.Constants;
import com.soulsurfer.android.utils.StringUtils;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageInfoLoader {
    private PageInfoListener listener;
    private String pageUrl;
    private HashMap<String, String> providerSchemaMap;

    private boolean oembedLoaded = false;
    private boolean metaLoaded = false;
    private JSONObject dataFromOEmbed;
    private JSONObject dataFromMeta;

    private long completeLoadTime;
    private long oembedLoadTime;
    private long metaLoadTime;

    public PageInfoLoader(String pageUrl, PageInfoListener listener, HashMap<String, String> providerSchemaMap) {
        this.pageUrl = pageUrl;
        this.listener = listener;
        this.providerSchemaMap = providerSchemaMap;
    }

    public void load() {
        completeLoadTime = System.currentTimeMillis();
        AppExecutors.runOnNetworkThread(new Runnable() {
            @Override
            public void run() {
                oembedLoadTime = System.currentTimeMillis();
                loadFromOembed();
            }
        });
        AppExecutors.runOnNetworkThread(new Runnable() {
            @Override
            public void run() {
                metaLoadTime = System.currentTimeMillis();
                loadFromMeta();
            }
        });
    }

    private void updateResult() {
        if (oembedLoaded && metaLoaded) {
            if (listener == null) {
                return;
            }

            PageInfo pageInfo = null;
            JSONObject mergedData = mergeData();
            if (mergedData == null) {
                publishResult(pageUrl, pageInfo);
                return;
            }
            try {
                String KEY_TYPE = "type";
                String KEY_URL = "url";
                String KEY_WIDTH = "width";
                String KEY_HEIGHT = "height";
                if (mergedData.has(KEY_TYPE) && "photo".equals(mergedData.get(KEY_TYPE))) {
                    if (mergedData.has(KEY_URL)) {
                        mergedData.put("thumbnail_url", mergedData.getString(KEY_URL));
                    }
                    if (mergedData.has(KEY_WIDTH)) {
                        mergedData.put("thumbnail_width", mergedData.getString(KEY_WIDTH));
                    }
                    if (mergedData.has(KEY_HEIGHT)) {
                        mergedData.put("thumbnail_height", mergedData.getString(KEY_HEIGHT));
                    }
                }
                mergedData.put("url", pageUrl);
                pageInfo = ((new Gson()).fromJson(mergedData.toString(), PageInfo.class));
            } catch (Exception e) {
                Log.e(Constants.TAG, e.toString());
            } finally {
                publishResult(pageUrl, pageInfo);
            }
        }
    }

    private void publishResult(final String pageUrl, final PageInfo pageInfo) {
        AppExecutors.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (pageInfo != null) {
                    listener.onPageInfoLoaded(pageInfo);
                } else {
                    listener.onError(pageUrl);
                }
                completeLoadTime = System.currentTimeMillis() - completeLoadTime;
                Log.i(Constants.TAG, "------ " + pageUrl + " ---------");
                Log.i(Constants.TAG, "Oembed load time - " + oembedLoadTime);
                Log.i(Constants.TAG, "Meta load time - " + metaLoadTime);
                Log.i(Constants.TAG, "Complete load time - " + completeLoadTime);
                Log.i(Constants.TAG, "--------------------------------");
            }
        });
    }

    private void loadFromOembed() {
        String oembedUrl = null;
        if (providerSchemaMap != null && providerSchemaMap.size() != 0) {
            for (Map.Entry<String, String> entry : providerSchemaMap.entrySet()) {
                try {
                    String p = entry.getKey();
                    p = p.replace("*", ".+");
                    Pattern pattern = Pattern.compile(p);
                    Matcher matcher = pattern.matcher(pageUrl);

                    if (matcher.matches()) {
                        Log.d(Constants.TAG, "Has match " + pageUrl);
                        oembedUrl = entry.getValue();
                        break;
                    }
                } catch (Exception e) {
                    Log.e(Constants.TAG, entry.getKey());
                }
            }
        }

        if (StringUtils.isNotEmpty(oembedUrl)) {
            dataFromOEmbed = RequestHelper.getOEmbedData(oembedUrl, pageUrl);
        }
        oembedLoaded = true;
        oembedLoadTime = System.currentTimeMillis() - oembedLoadTime;
        updateResult();
    }

    private void loadFromMeta() {
        try {
            Document document = Jsoup.connect(pageUrl).get();
            dataFromMeta = MetaHelper.getMetaData(pageUrl, document);
        } catch (Exception e) {
            Log.e("SoulSurfer", e.toString());
        } finally {
            metaLoaded = true;
            metaLoadTime = System.currentTimeMillis() - metaLoadTime;
            updateResult();
        }
    }

    private JSONObject mergeData() {
        try {
            JSONObject mergedData = null;
            if (dataFromMeta == null) {
                mergedData = new JSONObject();
            } else {
                mergedData = new JSONObject(dataFromMeta.toString());
            }

            if (dataFromOEmbed == null) {
                return mergedData;
            }

            Iterator<String> iterator = dataFromOEmbed.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                mergedData.put(key, dataFromOEmbed.get(key));
            }
            return mergedData;
        } catch (Exception e) {
            Log.e(Constants.TAG, e.toString());
        }
        return null;
    }
}
