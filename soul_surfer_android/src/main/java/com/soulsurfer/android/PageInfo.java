package com.soulsurfer.android;

public class PageInfo {
    private String url;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String providerName;
    private String providerIcon;

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getProviderIcon() {
        return providerIcon;
    }

    public static final class PageInfoBuilder {
        private String url;
        private String title;
        private String description;
        private String thumbnailUrl;
        private String providerName;
        private String providerIcon;

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

        public PageInfoBuilder withThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
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

        public PageInfo build() {
            PageInfo pageInfo = new PageInfo();
            pageInfo.description = this.description;
            pageInfo.url = this.url;
            pageInfo.thumbnailUrl = this.thumbnailUrl;
            pageInfo.title = this.title;
            pageInfo.providerName = this.providerName;
            pageInfo.providerIcon = this.providerIcon;
            return pageInfo;
        }
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", providerName='" + providerName + '\'' +
                ", providerIcon='" + providerIcon + '\'' +
                '}';
    }
}
