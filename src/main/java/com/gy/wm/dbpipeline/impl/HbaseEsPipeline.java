package com.gy.wm.dbpipeline.impl;

import com.alibaba.fastjson.JSON;
import com.gy.wm.dbpipeline.dbclient.EsClient;
import com.gy.wm.dbpipeline.dbclient.HbaseClient;
import com.gy.wm.model.CrawlData;
import com.gy.wm.util.RandomUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by TianyuanPan on 5/18/16.
 */
public class HbaseEsPipeline implements Pipeline {

    private EsClient esClient;
    private HbaseClient hbaseClient;

    private List<CrawlData> dataList;

    private Lock myLock;


    public HbaseEsPipeline() {

        this.esClient = new EsClient();
        this.hbaseClient = new HbaseClient();
        dataList = new ArrayList<>();
        this.myLock = new ReentrantLock();
    }

    public int insertRecord(Object obj) {

        int i = 0, j = 0;

        String rowkey = RandomUtils.getRandomString(50) + "_" + new Date().getTime();

        try {

            this.esClient.doSetInsert(this.esClient.getRequestUrl() + rowkey, JSON.toJSONString(obj));
            ++i;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {

            this.hbaseClient.insertRecord(hbaseClient.getTableName(),
                    rowkey, hbaseClient.getColumnFamilyName(), (CrawlData) obj);
            ++j;

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return (i & j);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {

        try {
            myLock.lock();

            CrawlData crawlerData = resultItems.get("crawlerData");

            if (crawlerData != null) {

                int i = this.insertRecord(crawlerData);
                System.out.println("HbaseEsPipeline doInsert Successful number: " + i);
                return;
            }

            System.out.println("at HbaseEsPipeline, crawler data IS NULL !!!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            myLock.unlock();
        }

    }
}
