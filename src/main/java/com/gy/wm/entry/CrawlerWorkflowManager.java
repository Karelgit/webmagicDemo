package com.gy.wm.entry;

import com.gy.wm.dbpipeline.impl.MysqlPipeline;
import com.gy.wm.model.CrawlData;
import com.gy.wm.parser.analysis.TextAnalysis;
import com.gy.wm.queue.RedisCrawledQue;
import com.gy.wm.queue.RedisToCrawlQue;
import com.gy.wm.schedular.RedisScheduler;
import com.gy.wm.service.TopicPageProcessor;
import com.gy.wm.util.JedisPoolUtils;
import com.gy.wm.util.LogManager;
import us.codecraft.webmagic.Spider;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/5/18.
 */
public class CrawlerWorkflowManager {
    private LogManager logger = new LogManager(CrawlerWorkflowManager.class);
    //初始化爬虫工厂是，用于解析的模板文件
    private TextAnalysis textAnalysis = InstanceFactory.getTextAnalysis();

    //待爬取队列
    private RedisToCrawlQue nextQueue = InstanceFactory.getRedisToCrawlQue();
    //已爬取队列
    private RedisCrawledQue crawledQueue = InstanceFactory.getRedisCrawledQue();

    private String tid;

    private String appname;

    public CrawlerWorkflowManager(String tid, String appname) {
        this.tid = tid;
        this.appname = appname;
    }

    public void crawl(List<CrawlData> seeds, String tid, String starttime, int pass) throws IOException {

        JedisPoolUtils jedisPoolUtils = new JedisPoolUtils();

        for (CrawlData seed : seeds) {
            nextQueue.putNextUrls(seed, jedisPoolUtils, tid);
        }
        //初始化webMagic的Spider程序
        initSpider(seeds, textAnalysis);
    }

    protected void initSpider(List<CrawlData> seeds, TextAnalysis textAnalysis) {
        String tempUrl = "";
        for (CrawlData crawlData : seeds) {
            tempUrl += crawlData.getUrl() + ",";
        }
        String urls = tempUrl.substring(0, tempUrl.length() - 1);

        Spider.create(new TopicPageProcessor(tid, textAnalysis))
                .setScheduler(new RedisScheduler())
                        //从seed开始抓
                .addUrl(urls)
                        //存入mysql
                .addPipeline(new MysqlPipeline("tb_crawler"))
                        //存入elasticSearch
//                .addPipeline(new EsPipeline())
//                .addPipeline(new HbaseEsPipeline())
                        //开启5个线程抓取
                .thread(5)
                        //启动爬虫
                .run();
    }

    protected boolean shouldContinue(JedisPoolUtils jedisPoolUtils) throws IOException {
        boolean rs = nextQueue.hasMoreUrls(tid, jedisPoolUtils);
        logger.logInfo("should continue: " + rs);
        return rs;
    }
}
