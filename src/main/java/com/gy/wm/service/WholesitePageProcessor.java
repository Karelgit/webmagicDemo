package com.gy.wm.service;

import com.gy.wm.entry.InstanceFactory;
import com.gy.wm.model.CrawlData;
import com.gy.wm.parser.analysis.TextAnalysis;
import com.gy.wm.queue.RedisToCrawlQue;
import com.gy.wm.queue.RedisCrawledQue;
import com.gy.wm.schedular.RedisBloomFilter;
import com.gy.wm.util.BloomFilter;
import com.gy.wm.util.JSONUtil;
import com.gy.wm.util.JedisPoolUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 网页抓取器。
 *
 * @author yinlei
 *         2014-3-5 下午4:27:51
 */
public class WholesitePageProcessor implements PageProcessor {
    private String tid;
    private TextAnalysis textAnalysis;

    public WholesitePageProcessor(String tid, TextAnalysis textAnalysis) {
        this.tid = tid;
        this.textAnalysis = textAnalysis;
    }

    private Site site = Site.me().setDomain("www.gygov.gov.cn").setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        JedisPoolUtils jedisPoolUtils = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            jedisPoolUtils = new JedisPoolUtils();
            pool = jedisPoolUtils.getJedisPool();
            jedis = pool.getResource();

            CrawlData page_crawlData = null;

            String json_crawlData = jedis.hget("webmagicCrawler::ToCrawl::" + tid, page.getRequest().getUrl());
            System.out.println("page.getRequest.getUrl ==> url: " + page.getRequest().getUrl());
            page_crawlData = (CrawlData) JSONUtil.jackson2Object(json_crawlData, CrawlData.class);
            jedis.hdel("webmagicCrawler::ToCrawl::" + tid, page.getRequest().getUrl());

            int statusCode = page.getStatusCode();
            String html = page.getHtml().toString();

            //对源码和访问状态码进行赋值
            page_crawlData.setHtml(html);
            page_crawlData.setStatusCode(statusCode);

            //解析过程
            List<CrawlData> perPageCrawlDateList = this.getTextAnalysis().analysisHtml(page_crawlData);

            List<CrawlData> nextCrawlData = new ArrayList<>();
            List<CrawlData> crawledData = new ArrayList<>();

            BloomFilter bloomFilter = new BloomFilter(jedis, 1000, 0.001f, (int) Math.pow(2, 31));
            for (CrawlData crawlData : perPageCrawlDateList) {
                if(linkFilter(crawlData) == true)   {
                    if (crawlData.isFetched() == false) {
                        //链接fetched为false,即导航页,bloomFilter判断待爬取队列没有记录
                        boolean isNew = RedisBloomFilter.notExistInBloomHash(crawlData.getUrl(), jedis, bloomFilter);
                        if (isNew) {
                            nextCrawlData.add(crawlData);
                            page.addTargetRequest(crawlData.getUrl());
                        }
                    } else {
                        //链接fetched为true,即文章页，添加到redis的已爬取队列
                        crawledData.add(crawlData);
                        page.putField("crawlerData", crawlData);
                    }
                }
            }

            RedisToCrawlQue nextQueue = InstanceFactory.getRedisToCrawlQue();

            //加入到待爬取队列
            nextQueue.putNextUrls(nextCrawlData, jedis, tid);
            //加入到已爬取队列
            new RedisCrawledQue().putCrawledQue(crawledData,jedis,tid);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            pool.returnResource(jedis);
        }
    }


    @Override
    public Site getSite() {
        return site;
    }

    public boolean linkFilter(CrawlData crawlData) {
        if(!crawlData.getUrl().endsWith(".css")&&!crawlData.getUrl().endsWith(".js")&&!crawlData.getUrl().endsWith(".png")&&!crawlData.getUrl().endsWith(".jpg")&&!crawlData.getUrl().endsWith(".gif")&&crawlData.getUrl().contains("www.gygov.gov.cn")) {
            return true;
        }else {
            return false;
        }
    }

    public TextAnalysis getTextAnalysis() {
        return textAnalysis;
    }
}

