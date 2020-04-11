package com.soulsurfer.android.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Provider {

    @SerializedName("provider_name")
    private String providerName;

    @SerializedName("provider_url")
    private String providerUrl;

    private List<Endpoint> endpoints;

    public String getProviderName() {
        return providerName;
    }

    public String getProviderUrl() {
        return providerUrl;
    }

    public List<Endpoint> getEndpoints() {
        return endpoints;
    }

    public class Endpoint {
        private List<String> schemes;
        private String url;

        public List<String> getSchemes() {
            return schemes;
        }

        public String getUrl() {
            return url;
        }
    }
}
