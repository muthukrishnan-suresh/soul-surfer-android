package com.soulsurfer.android.sample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.soulsurfer.android.PageInfo;
import com.soulsurfer.android.PageInfoListener;
import com.soulsurfer.android.SoulSurfer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<PageMeta> pageMetaList = new ArrayList<>();

    public RecyclerViewAdapter(@NonNull List<String> pageUrls) {
        if (pageUrls != null) {
            for (String url : pageUrls) {
                pageMetaList.add(new PageMeta(url));
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final PageMeta pageMeta = pageMetaList.get(position);
        updateUI(holder, pageMeta);

        if (pageMeta.pageInfo == null) {
            final long startTime = System.currentTimeMillis();
            SoulSurfer.get(pageMeta.url)
                    .load(new PageInfoListener() {
                        @Override
                        public void onPageInfoLoaded(PageInfo pageInfo) {
                            long loadTime = System.currentTimeMillis() - startTime;
                            pageMeta.setLoadTime(loadTime);
                            pageMeta.setPageInfo(pageInfo);

                            updateUI(holder, pageMeta);
                        }

                        @Override
                        public void onError(String url) {
                            Log.e("SoulSurferSample", "Error - " + url);
                        }
                    });
        }
    }

    private void updateUI(@NonNull final ViewHolder holder, @Nullable PageMeta pageMeta) {
        Context context = holder.loadTimeView.getContext();
        if (pageMeta == null || pageMeta.pageInfo == null) {
            holder.loadTimeView.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
            holder.loadTimeView.setText("");
            holder.providerNameView.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
            holder.providerNameView.setText("");
            holder.titleView.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
            holder.titleView.setText("");
            holder.descriptionView.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
            holder.descriptionView.setText("");
            holder.pageUrlView.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
            holder.pageUrlView.setText("");
            holder.providerIconView.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
            holder.providerIconView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.empty_bg));
            holder.imageView.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
            holder.imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.empty_bg));
            return;
        } else {
            holder.loadTimeView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
            holder.providerNameView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
            holder.titleView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
            holder.descriptionView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
            holder.pageUrlView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
            holder.providerIconView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
            holder.imageView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        }


        holder.loadTimeView.setText("Load time - " + (pageMeta.loadTime / 1000F) + "s");
        holder.providerNameView.setText(pageMeta.pageInfo.getProviderName());
        holder.titleView.setText(pageMeta.pageInfo.getTitle());
        holder.descriptionView.setText(pageMeta.pageInfo.getDescription());
        holder.pageUrlView.setText(pageMeta.pageInfo.getUrl());

        if (pageMeta.pageInfo.getProviderIcon() != null) {
            Picasso.with(holder.providerIconView.getContext())
                    .load(pageMeta.pageInfo.getProviderIcon())
                    .into(holder.providerIconView);
        }

        if (pageMeta.pageInfo.getImageUrl() != null) {
            Picasso.with(holder.imageView.getContext())
                    .load(pageMeta.pageInfo.getImageUrl())
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return pageMetaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView providerNameView;
        private TextView titleView;
        private TextView descriptionView;
        private TextView pageUrlView;
        private TextView loadTimeView;
        private ImageView providerIconView;
        private ImageView imageView;

        public ViewHolder(@NonNull View layout) {
            super(layout);

            providerNameView = layout.findViewById(R.id.page_info_provider_name);
            titleView = layout.findViewById(R.id.page_info_title);
            descriptionView = layout.findViewById(R.id.page_info_description);
            pageUrlView = layout.findViewById(R.id.page_info_url);
            loadTimeView = layout.findViewById(R.id.page_info_load_time);
            providerIconView = layout.findViewById(R.id.page_info_provider_icon);
            imageView = layout.findViewById(R.id.page_info_image);
        }
    }

    private class PageMeta {
        private String url;
        private PageInfo pageInfo;
        private long loadTime;

        private PageMeta(String url) {
            this.url = url;
        }

        private String getUrl() {
            return url;
        }

        private void setUrl(String url) {
            this.url = url;
        }

        private PageInfo getPageInfo() {
            return pageInfo;
        }

        private void setPageInfo(PageInfo pageInfo) {
            this.pageInfo = pageInfo;
        }

        private long getLoadTime() {
            return loadTime;
        }

        private void setLoadTime(long loadTime) {
            this.loadTime = loadTime;
        }
    }
}
