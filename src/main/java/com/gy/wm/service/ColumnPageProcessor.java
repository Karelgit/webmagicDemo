package com.gy.wm.service;

import com.gy.wm.entry.InstanceFactory;
import com.gy.wm.model.CrawlData;
import com.gy.wm.parser.analysis.TextAnalysis;
import com.gy.wm.queue.RedisCrawledQue;
import com.gy.wm.queue.RedisToCrawlQue;
import com.gy.wm.schedular.RedisBloomFilter;
import com.gy.wm.util.BloomFilter;
import com.gy.wm.util.JedisPoolUtils;
import com.gy.wm.util.JsonUtil;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;
import java.util.List;

/**
 * 网页抓取器。
 *
 * @author yinlei
 *         2014-3-5 下午4:27:51
 */
public class ColumnPageProcessor implements PageProcessor {
    private String tid;
    private TextAnalysis textAnalysis;

    public ColumnPageProcessor(String tid, TextAnalysis textAnalysis) {
        this.tid = tid;
        this.textAnalysis = textAnalysis;
    }

    private Site site = Site.me().setDomain("http://www.gog.cn").setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        JedisPoolUtils jedisPoolUtils = null;
        Jedis jedis = null;
        String url = page.getRequest().getUrl();

        try {
            jedisPoolUtils = new JedisPoolUtils();
            jedis = jedisPoolUtils.getJedisPool().getResource();
            String json_crawlData = jedis.hget("webmagicCrawle::ToCrawl::" + tid, page.getRequest().getUrl());
            CrawlData page_crawlData = JsonUtil.toObject(json_crawlData, CrawlData.class);

            int statusCode = page.getStatusCode();
            String html = page.getHtml().toString();

            //对源码和访问状态码进行赋值
            page_crawlData.setHtml(html);
            page_crawlData.setStatusCode(statusCode);

            //解析过程
            List<CrawlData> perPageCrawlDateList = this.getTextAnalysis().analysisHtml(page_crawlData);

            for (CrawlData crawlData : perPageCrawlDateList) {
                if (crawlData.isFetched() == false) {
                    //栏目分析fetched为false,即导航页
                    BloomFilter bloomFilter = new BloomFilter(jedis, 1000, 0.001f, (int) Math.pow(2, 31));

                    //bloomFilter判断待爬取队列没有记录
                    if (RedisBloomFilter.notExistInBloomHash(url, jedis, bloomFilter)) {
                        RedisToCrawlQue nextQueue = InstanceFactory.getRedisToCrawlQue();
                        //加入到待爬取队列
                        nextQueue.putNextUrls(crawlData, jedisPoolUtils, tid);
                        page.addTargetRequest(crawlData.getUrl());
                    }
                } else {
                    //栏目分析fetched为true,即文章页，添加到redis的已爬取队列
                    new RedisCrawledQue().putCrawledQue(crawlData, jedisPoolUtils, this.tid);
                    page.putField("crawlerData", crawlData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public Site getSite() {
        return site;
    }

    public static CrawlData initCrawlData(String tid, String url, String html, int statusCode) {
        CrawlData crawlData = new CrawlData();
        crawlData.setTid(tid);
        crawlData.setUrl(url);
        crawlData.setHtml(html);
        crawlData.setStatusCode(statusCode);
        crawlData.setFetched(false);
        return crawlData;
    }

    public TextAnalysis getTextAnalysis() {
        return textAnalysis;
    }

    public void setTextAnalysis(TextAnalysis textAnalysis) {
        this.textAnalysis = textAnalysis;
    }


}
