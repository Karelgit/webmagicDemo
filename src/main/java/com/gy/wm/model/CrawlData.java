package com.gy.wm.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/29.
 */

/**
 * 爬取得到的数据实体类
 */
public class CrawlData implements Serializable {
//    private final static long serialVersionUID = -2344403674643228206L;

    private String tid;     // 任务ID
    private String url;     // 本页面的 URL
    private int statusCode; // 状态码
    private int pass;       // 爬取遍数;
    private String type;    // 类型
    private String rootUrl; // 根 URL，（入口URL）
    private String fromUrl; // 父 URL，（本也的上层URL）
    private String text;    // 不带任何格式标记的文本
    private String html;    // HTML源码
    private String title;   // 标题
    private String startTime; // 开始时间
    private long crawlTime;   // 爬取时间
    private Date publishTime; // 发布时间（对于文章页面而言）
    private long depthfromSeed; // 爬取层数（从入口URL到此页的深度）
    private long count;         // 统计
    private boolean tag;        // 标签； true：文章； false：导航
    private boolean fetched;    // 抽取标识


    public CrawlData() {}

    public CrawlData(String url, int statusCode, int pass, String type, String rootUrl, String fromUrl, String text, String html, String title,
                     String startTime, long crawlTime, Date publishTime, long depthfromSeed, boolean tag, long count, boolean fetched)  {
        this.url = url;
        this.statusCode = statusCode;
        this.pass = pass;
        this.type = type;
        this.rootUrl = rootUrl;
        this.fromUrl = fromUrl;
        this.text = text;
        this.html = html;
        this.title = title;
        this.startTime = startTime;
        this.crawlTime = crawlTime;
        this.publishTime = publishTime;
        this.depthfromSeed = depthfromSeed;
        this.tag = tag;
        this.count = count;
        this.fetched = fetched;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public String getFromUrl() {
        return fromUrl;
    }

    public void setFromUrl(String fromUrl) {
        this.fromUrl = fromUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public long getCrawlTime() {
        return crawlTime;
    }

    public void setCrawlTime(long crawlTime) {
        this.crawlTime = crawlTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public long getDepthfromSeed() {
        return depthfromSeed;
    }

    public void setDepthfromSeed(long depthfromSeed) {
        this.depthfromSeed = depthfromSeed;
    }

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public boolean isFetched() {
        return fetched;
    }

    public void setFetched(boolean fetched) {
        this.fetched = fetched;
    }


}
