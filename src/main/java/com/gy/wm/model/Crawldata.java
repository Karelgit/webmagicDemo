package com.gy.wm.model;

/**
 * Created by Administrator on 2016/4/29.
 */
public class CrawlData {
    private String url;
    private String title;
    private Long publicTme;
    private String html;
    private String text;
    private boolean fetched;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Long getPublicTme() {
        return publicTme;
    }

    public void setPublicTme(Long publicTme) {
        this.publicTme = publicTme;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public boolean isFetched() {
        return fetched;
    }

    public void setFetched(boolean fetched) {
        this.fetched = fetched;
    }
}
