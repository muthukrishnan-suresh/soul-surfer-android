package com.soulsurfer.android;

public interface PageInfoListener {
    void onPageInfoLoaded(PageInfo pageInfo);
    void onError(String url);
}
