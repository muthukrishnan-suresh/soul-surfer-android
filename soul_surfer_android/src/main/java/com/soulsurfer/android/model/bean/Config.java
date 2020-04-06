package com.soulsurfer.android.model.bean;

import com.google.gson.annotations.SerializedName;

public class Config {

    @SerializedName("feature_enabled")
    private boolean featureEnabled;

    @SerializedName("block_request")
    private boolean blockRequest;

    @SerializedName("endpoint")
    private String endPoint;

    @SerializedName("sdk_cache_ttl")
    private long sdkCacheTtl;

    public boolean isFeatureEnabled() {
        return featureEnabled;
    }

    public void setFeatureEnabled(boolean featureEnabled) {
        this.featureEnabled = featureEnabled;
    }

    public boolean isBlockRequest() {
        return blockRequest;
    }

    public void setBlockRequest(boolean blockRequest) {
        this.blockRequest = blockRequest;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public long getSdkCacheTtl() {
        return sdkCacheTtl;
    }

    public void setSdkCacheTtl(long sdkCacheTtl) {
        this.sdkCacheTtl = sdkCacheTtl;
    }
}
