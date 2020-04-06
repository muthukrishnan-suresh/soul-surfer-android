package com.soulsurfer.android.helper;

import android.util.Log;

import com.soulsurfer.android.PageInfo;
import com.soulsurfer.android.PageInfoListener;
import com.soulsurfer.android.utils.AppExecutors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PageInfoHelper {

    public static void load(final String url, final PageInfoListener listener) {
        AppExecutors.getInstance()
                .networkIO()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Document document = Jsoup.connect(url).get();
                            final PageInfo pageInfo = MetaHelper.getMetaData(url, document);
                            AppExecutors.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onPageInfoLoaded(pageInfo);
                                }
                            });
                            return;
                        } catch (Exception e) {
                            Log.e("SoulSurfer", e.toString());
                        }
                        AppExecutors.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onError(url);
                            }
                        });
                    }
                });
    }
}
