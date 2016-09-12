package com.gy.wm.dbpipeline.impl;

import com.gy.wm.dbpipeline.dbclient.EsClient;
import com.gy.wm.model.CrawlData;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by TianyuanPan on 5/9/16.
 */
public class EsPipeline implements Pipeline {

    private EsClient esClient;
    private Lock myLock;

    @Override
    public void process(ResultItems resultItems, Task task) {
        try {
            myLock.lock();
            System.out.println("EsPipeline resultItems size: " + resultItems.getAll().size() +
                    "\n\tTask uuid: " + task.getUUID());

            CrawlData crawlerData = resultItems.get("crawlerData");

            if (crawlerData != null) {

                this.esClient.add(crawlerData);
                int i = this.esClient.doSetInsert();
                System.out.println("EsPipeline doInsert Successful number: " + i);
                return;
            }
            System.out.println("at EsPipeline, crawler data IS NULL !!!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myLock.unlock();
        }

    }

}
