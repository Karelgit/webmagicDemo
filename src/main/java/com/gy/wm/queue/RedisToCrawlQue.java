package com.gy.wm.queue;

import com.gy.wm.model.CrawlData;
import com.gy.wm.util.JedisPoolUtils;
import com.gy.wm.util.JsonUtil;
import com.gy.wm.util.LogManager;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/17.
 */
public class RedisToCrawlQue {
    private transient static LogManager logger = new LogManager(RedisToCrawlQue.class);

    private static final int batchsize = 100;

    public List<CrawlData> nextBatch(JedisPoolUtils jedisPoolUtils, String taskid) throws IOException {
        List<CrawlData> result = new ArrayList<>();
        List<String> redisData =new ArrayList<>();
        Jedis jedis = jedisPoolUtils.getJedisPool().getResource();

        for (int i = 0; i < batchsize; i++) {
            String dat = jedis.lpop("sparkcrawler::ToCrawl::" + taskid);
            if (dat != null) {
                redisData.add(dat);
            }
        }
        for (String crawlDataJson : redisData) {
            CrawlData crawlData = JsonUtil.toObject(crawlDataJson, CrawlData.class);
            result.add(crawlData);
        }
        return result;
    }


    public boolean hasMoreUrls(String taskid,JedisPoolUtils jedisPoolUtils) {

        Jedis jedis = jedisPoolUtils.getJedisPool().getResource();
        jedis.select(0);
        try {
            return jedis.llen("webmagicCrawler::ToCrawl::"+taskid) != 0;
        } finally {
            jedisPoolUtils.getJedisPool().returnResource(jedis);
        }
    }

    public void putNextUrls(List<CrawlData> nextUrls,JedisPoolUtils jedisPoolUtils, String tid ) {
        Jedis jedis = jedisPoolUtils.getJedisPool().getResource();
        jedis.select(0);
        for (CrawlData nextUrl : nextUrls) {
            jedis.rpush("sparkcrawler::ToCrawl::" + tid, nextUrl.getUrl());
        }
    }

}
