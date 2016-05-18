package com.gy.wm.entry;

import com.gy.wm.model.CrawlData;

import java.util.List;

/**
 * Created by Administrator on 2016/5/18.
 */
public class Crawl {
    public static void kick(int depth, int pass, String tid, String starttime, String seedpath, String protocolDir,
                            String postregexDir, String type, int recalldepth, String templatesDir, String clickregexDir,String configpath)  throws Exception  {
        //tid_startTime作为appname，即作为这个爬虫的任务名称
        InitCrawlerConfig crawlerConfig = new InitCrawlerConfig(tid+starttime,recalldepth,templatesDir,clickregexDir,protocolDir,postregexDir);
        InstanceFactory.getInstance(crawlerConfig);

        ConfigLoader configLoader = new ConfigLoader();
        List<CrawlData> crawlDataList = configLoader.load(depth,tid,starttime,pass,seedpath,type);

        CrawlerWorkflowManager workflow = new CrawlerWorkflowManager(tid,"appname");
        workflow.crawl(crawlDataList,tid,starttime,pass);
    }
}
