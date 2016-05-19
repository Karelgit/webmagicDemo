package com.gy.wm.dbpipeline.impl;

import com.alibaba.fastjson.JSON;
import com.gy.wm.dbpipeline.DatabasePipeline;
import com.gy.wm.dbpipeline.dbclient.EsClient;
import com.gy.wm.dbpipeline.dbclient.HbaseClient;
import com.gy.wm.model.CrawlData;
import com.gy.wm.util.RandomUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TianyuanPan on 5/18/16.
 */
public class HbaseEsPipeline implements DatabasePipeline{

    private EsClient esClient;
    private HbaseClient hbaseClient;

    private List<CrawlData> dataList;


    public HbaseEsPipeline() {

        this.esClient = new EsClient();
        this.hbaseClient = new HbaseClient();
        dataList = new ArrayList<>();
    }

    @Override
    public int insertRecord(Object obj) {

        String rowkey = RandomUtils.getRandomString(50) + "_" + new Date().getTime();

        try {

            this.esClient.doPut(this.esClient.getRequestUrl() + rowkey, JSON.toJSONString(obj));

        }catch (Exception ex){

            ex.printStackTrace();
        }

        try {

           this.hbaseClient.insertRecord(HbaseClient.getTableName(),
                   rowkey, HbaseClient.getColumnFamilyName(), (CrawlData)obj);

        }catch (Exception ex){

            ex.printStackTrace();
        }


        return 1;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {

        System.out.println("HbaseEsPipeline resultItems size: " + resultItems.getAll().size() +
                "\n\tTask uuid: " + task.getUUID());

        CrawlData crawlerData = resultItems.get("crawlerData");

        if (crawlerData != null) {

           int i = this.insertRecord(crawlerData);
            System.out.println("HbaseEsPipeline doSetInser return code: " + i);
            return;
        }

        System.out.println("crawler data IS NULL !!!");

    }
}
