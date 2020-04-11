package com.soulsurfer.android;

import com.google.gson.annotations.SerializedName;

public class PageInfo {
    private String url;
    private String title;
    private String description;

    @SerializedName("thumbnail_url")
    private String imageUrl;

    @SerializedName("provider_name")
    private String providerName;

    @SerializedName("provider_icon")
    private String providerIcon;
    private PageInfo.Type type;

    @SerializedName("thumbnail_height")
    private int imageHeight;

    @SerializedName("thumbnail_width")
    private int imageWidth;
    private String html;

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getProviderIcon() {
        return providerIcon;
    }

    public Type getType() {
        return type;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public String getHtml() {
        return html;
    }

    public static final class PageInfoBuilder {
        private String url;
        private String title;
        private String description;
        private String imageUrl;
        private String providerName;
        private String providerIcon;
        private PageInfo.Type type;
        private int imageHeight;
        private int imageWidth;
        private String html;

        private PageInfoBuilder() {
        }

        public static PageInfoBuilder getBuilder() {
            return new PageInfoBuilder();
        }

        public PageInfoBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        public PageInfoBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public PageInfoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public PageInfoBuilder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public PageInfoBuilder withProviderName(String providerName) {
            this.providerName = providerName;
            return this;
        }

        public PageInfoBuilder withProviderIcon(String providerIcon) {
            this.providerIcon = providerIcon;
            return this;
        }

        public PageInfoBuilder withType(Type type) {
            this.type = type;
            return this;
        }

        public PageInfoBuilder withImageHeight(int imageHeight) {
            this.imageHeight = imageHeight;
            return this;
        }

        public PageInfoBuilder withImageWidth(int imageWidth) {
            this.imageWidth = imageWidth;
            return this;
        }

        public PageInfoBuilder withHtml(String html) {
            this.html = html;
            return this;
        }

        public PageInfo build() {
            PageInfo pageInfo = new PageInfo();
            pageInfo.description = this.description;
            pageInfo.url = this.url;
            pageInfo.imageUrl = this.imageUrl;
            pageInfo.title = this.title;
            pageInfo.providerName = this.providerName;
            pageInfo.providerIcon = this.providerIcon;
            pageInfo.type = this.type;
            pageInfo.imageHeight = this.imageHeight;
            pageInfo.imageWidth = this.imageWidth;
            pageInfo.html = this.html;
            return pageInfo;
        }
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", providerName='" + providerName + '\'' +
                ", providerIcon='" + providerIcon + '\'' +
                ", type=" + type +
                ", imageHeight=" + imageHeight +
                ", imageWidth=" + imageWidth +
                ", html='" + html + '\'' +
                '}';
    }

    public enum Type {
        /**
         * Page of type "photo" will contain url, width & height
         */
        @SerializedName("photo")
        PHOTO,

        /**
         * Page of type "video" will contain html, width & height
         */
        @SerializedName("video")
        VIDEO,

        /**
         * Page of type "rich" will contain html, width & height
         */
        @SerializedName("rich")
        RICH,

        /**
         * Page of type "link" will contain html
         */
        @SerializedName("link")
        LINK;
    }
}
