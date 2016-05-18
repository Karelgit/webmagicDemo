package com.gy.wm.entry;

import com.gy.wm.dbpipeline.impl.EsPipeline;
import com.gy.wm.dbpipeline.impl.MysqlPipeline;
import com.gy.wm.model.CrawlData;
import com.gy.wm.parser.analysis.TextAnalysis;
import com.gy.wm.queue.RedisCrawledQue;
import com.gy.wm.queue.RedisToCrawlQue;
import com.gy.wm.schedular.RedisScheduler;
import com.gy.wm.service.ColumnPageProcessor;
import com.gy.wm.util.JedisPoolUtils;
import com.gy.wm.util.LogManager;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.Spider;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/5/18.
 */
public class CrawlerWorkflowManager {
    private LogManager logger = new LogManager(CrawlerWorkflowManager.class);
    //初始化爬虫工厂是，用于解析的模板文件
    private TextAnalysis textAnalysis= InstanceFactory.getTextAnalysis();

    //待爬取队列
    private RedisToCrawlQue nextQueue = InstanceFactory.getRedisToCrawlQue();
    //已爬取队列
    private RedisCrawledQue crawledQueue =InstanceFactory.getRedisCrawledQue();

    private String taskid;

    private String appname;

    public CrawlerWorkflowManager(String taskid,String appname)  {
        this.taskid = taskid;
        this.appname = appname;
    }

    public void crawl(List<CrawlData> seeds,String taskid,String starttime,int pass) throws IOException{

        JedisPoolUtils jedisPoolUtils = new JedisPoolUtils();
        Jedis jedis = jedisPoolUtils.getJedisPool().getResource();
        nextQueue.putNextUrls(seeds, jedisPoolUtils, taskid);


        //初始化webMagic的Spider程序
        initSpider(seeds,textAnalysis);

        while(shouldContinue(jedisPoolUtils))   {
            List<CrawlData> currentBatch = nextQueue.nextBatch(jedisPoolUtils,taskid);
            //
        }


    }

    protected boolean shouldContinue(JedisPoolUtils jedisPoolUtils) throws IOException {
        boolean rs = nextQueue.hasMoreUrls(taskid,jedisPoolUtils);
        logger.logInfo("should continue: " + rs);
        return rs;
    }

    protected  void initSpider(List<CrawlData> seeds,TextAnalysis textAnalysis)   {
        String urls;/*
        for (CrawlData seed : seeds) {
            urls+=seed+",";
        }*/
        Spider.create(new ColumnPageProcessor(textAnalysis))
                .setScheduler(new RedisScheduler())
                        //从seed开始抓
                .addUrl()
                        //存入mysql
                .addPipeline(new MysqlPipeline("tb_crawler"))
                        //存入elasticSearch
                .addPipeline(new EsPipeline())
                        //开启5个线程抓取
                .thread(5)
                        //启动爬虫
                .run();
    }
}
