package com.soulsurfer.android.model.bean.response;

import com.google.gson.annotations.SerializedName;
import com.soulsurfer.android.model.bean.Config;

public class ConfigResponse {

    private Config config;

    @SerializedName("oembed_config")
    private Config oembedConfig;

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Config getOembedConfig() {
        return oembedConfig;
    }

    public void setOembedConfig(Config oembedConfig) {
        this.oembedConfig = oembedConfig;
    }
}
