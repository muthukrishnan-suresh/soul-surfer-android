package com.soulsurfer.android.model.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soulsurfer.android.PageInfo;
import com.soulsurfer.android.model.bean.Provider;
import com.soulsurfer.android.model.bean.response.ConfigResponse;
import com.soulsurfer.android.utils.Constants;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.List;

public class RequestHelper {

    private static final String CONFIG = "https://soul-surfer-app.firebaseapp.com/config";

    public static ConfigResponse getConfig() {
        try {
            Response response = Request.builder(CONFIG, HttpMethod.GET).response();
            return (new Gson()).fromJson(response.getData(), ConfigResponse.class);
        } catch (Exception e) {
            Log.e(Constants.TAG, e.toString());
        }
        return null;
    }

    public static List<Provider> getProviders(String endPoint) {
        try {
            Response response = Request.builder(endPoint, HttpMethod.GET).response();
            Type listType = new TypeToken<List<Provider>>() {
            }.getType();
            return (new Gson()).fromJson(response.getData(), listType);
        } catch (Exception e) {
            Log.e(Constants.TAG, e.toString());
        }
        return null;
    }

    public static JSONObject getOEmbedData(String oembedUrl, String pageUrl) {
        try {
            String url = new StringBuilder(oembedUrl)
                    .append("?")
                    .append("format=json")
                    .append("&")
                    .append("url=")
                    .append(URLEncoder.encode(pageUrl, "UTF-8"))
                    .toString();
            Response response = Request.builder(url, HttpMethod.GET).response();
            JSONObject jsonObject = new JSONObject(response.getData());
            return jsonObject;
        } catch (Exception e) {
            Log.e(Constants.TAG, e.toString());
        }
        return null;
    }
}
