package com.soulsurfer.android.Utils;

import android.util.Log;

import com.soulsurfer.android.PageInfo;
import com.soulsurfer.android.PageInfoListener;

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
                            PageInfo pageInfo = MetaHelper.getMetaData(url, document);
                            listener.onPageInfoLoaded(pageInfo);
                            return;
                        } catch (Exception e) {
                            Log.e("SoulSurfer", e.toString());
                        }
                        listener.onError(url);
                    }
                });
    }
}
