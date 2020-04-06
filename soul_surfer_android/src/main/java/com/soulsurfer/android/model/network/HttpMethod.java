package com.soulsurfer.android.model.network;

enum HttpMethod {
    GET("GET");

    private String str;

    HttpMethod(String str) {
        this.str = str;
    }

    public String asString() {
        return str;
    }
}
