package com.gy.wm.model;

import java.util.Date;

/**
 * Created by TianyuanPan on 5/20/16.
 */

/**
 * 蜂鸟系统中，栏目更新表的数据结构模型类
 */
public class FengBirdModel {

    private String topicTaskID;
    private String title;
    private Date   labelTime;
    private String url;
    private String fromUrl;
    private String rootUrl;
    private long   crawlTime;
    private int    deleteflag;

    public String getTopicTaskID() {
        return topicTaskID;
    }

    public void setTopicTaskID(String topicTaskID) {
        this.topicTaskID = topicTaskID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getLabelTime() {
        return labelTime;
    }

    public void setLabelTime(Date labelTime) {
        this.labelTime = labelTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFromUrl() {
        return fromUrl;
    }

    public void setFromUrl(String fromUrl) {
        this.fromUrl = fromUrl;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public long getCrawlTime() {
        return crawlTime;
    }

    public void setCrawlTime(long crawlTime) {
        this.crawlTime = crawlTime;
    }

    public int getDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(int deleteflag) {
        this.deleteflag = deleteflag;
    }
}
