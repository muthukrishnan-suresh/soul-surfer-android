package com.soulsurfer.android;

import com.soulsurfer.android.model.repository.SoulSurferRepository;
import com.soulsurfer.android.utils.StringUtils;

public class RequestCreator {

    private String url;

    RequestCreator(String url) {
        this.url = url;
    }

    public void load(PageInfoListener listener) {
        if (StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException("url can not be empty while loading a page");
        }

        if (listener == null) {
            throw new IllegalArgumentException("listener can not be null while loading a page");
        }

        SoulSurferRepository.load(url, listener);
    }
}
