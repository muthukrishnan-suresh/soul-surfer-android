package com.soulsurfer.android.helper;

import android.util.Log;

import com.soulsurfer.android.utils.Constants;
import com.soulsurfer.android.utils.MetaAttr;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

public class MetaHelper {

    private static HashMap<MetaAttr, String> extractMetaDetails(Elements elements, HashMap<String, MetaAttr> metaAttrMap) {
        HashMap<String, String> dataMap = new HashMap<>();
        for (Element element : elements) {
            String key = null;
            if (element.hasAttr("name")) {
                key = element.attr("name");
            } else if (element.hasAttr("property")) {
                key = element.attr("property");
            }

            if (key == null) {
                continue;
            }

            String content = element.attr("content");
            if (content == null) {
                continue;
            }
            dataMap.put(key, content);
        }

        HashMap<MetaAttr, String> metaDataMap = new HashMap<>();
        for (String key : metaAttrMap.keySet()) {
            if (dataMap.containsKey(key)) {
                metaDataMap.put(metaAttrMap.get(key), dataMap.get(key));
            }
        }
        return metaDataMap;
    }

    private static String getStringMetaData(HashMap<MetaAttr, String> metaDataMap, MetaAttr.Type type) {
        Object result = getMetaData(metaDataMap, type);
        return result == null ? null : String.valueOf(result);
    }

    private static int getIntMetaData(HashMap<MetaAttr, String> metaDataMap, MetaAttr.Type type) {
        String result = getStringMetaData(metaDataMap, type);
        return result == null ? 0 : Integer.parseInt(result);
    }

    private static Object getMetaData(HashMap<MetaAttr, String> metaDataMap, MetaAttr.Type type) {
        List<MetaAttr> metaAttrs = MetaAttr.getAttrForType(type);
        for (MetaAttr metaAttr : metaAttrs) {
            if (metaDataMap.containsKey(metaAttr)) {
                return metaDataMap.get(metaAttr);
            }
        }
        return null;
    }

    private static String getSiteIcon(Elements elements) {
        for (Element element : elements) {
            if ("icon".equalsIgnoreCase(element.attr("rel"))) {
                String iconUrl = element.attr("href");
                if (iconUrl != null && !iconUrl.endsWith(".svg")) {
                    return iconUrl;
                }
            }
        }
        return null;
    }

    public static JSONObject getMetaData(String url, Document document) {
        HashMap<MetaAttr, String> metaDataMap = extractMetaDetails(document.select("meta"), MetaAttr.getMetaAttrMap());

        String title = getStringMetaData(metaDataMap, MetaAttr.Type.TITLE);
        String description = getStringMetaData(metaDataMap, MetaAttr.Type.DESCRIPTION);
        String thumbnailUrl = getStringMetaData(metaDataMap, MetaAttr.Type.IMAGE);
        int thumbnailWidth = getIntMetaData(metaDataMap, MetaAttr.Type.WIDTH);
        int thumbnailHeight = getIntMetaData(metaDataMap, MetaAttr.Type.HEIGHT);
        String providerName = getStringMetaData(metaDataMap, MetaAttr.Type.SITE_NAME);
        String providerIcon = getSiteIcon(document.select("link"));

        if (title == null || title.length() == 0) {
            try {
                title = document.select("title").get(0).childNodes().get(0).toString();
            } catch (Exception e) {
                Log.e("SoulSurfer", "Title error - " + e.toString());
            }
        }

        if (providerName == null || providerName.length() == 0) {
            try {
                URI uri = new URI(url);
                providerName = uri.getAuthority();
            } catch (Exception e) {
                Log.e("SoulSurfer", "SiteName error - " + e.toString());
            }
        }

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", title);
            jsonObject.put("description", description);
            jsonObject.put("thumbnail_url", thumbnailUrl);
            if (thumbnailWidth > 0) {
                jsonObject.put("thumbnail_width", thumbnailWidth);
            }

            if (thumbnailHeight > 0) {
                jsonObject.put("thumbnail_height", thumbnailHeight);
            }

            jsonObject.put("provider_name", providerName);
            jsonObject.put("provider_icon", providerIcon);
            return jsonObject;
        } catch (JSONException e) {
            Log.e(Constants.TAG, e.toString());
        }

        return null;
    }
}
