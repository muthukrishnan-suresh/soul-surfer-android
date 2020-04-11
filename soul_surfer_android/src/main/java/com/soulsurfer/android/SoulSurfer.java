package com.soulsurfer.android;

public class SoulSurfer {

    public static RequestCreator get(String url) {
        return new RequestCreator(url);
    }
}
