package com.gy.wm.dbpipeline.impl;

import com.gy.wm.dbpipeline.dbclient.EsClient;
import com.gy.wm.model.CrawlData;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by TianyuanPan on 5/9/16.
 */
public class EsPipeline extends BaseDBPipeline {

    private EsClient esClient;
    private Lock myLock;


    public EsPipeline() {

        this.esClient = new EsClient();
        this.myLock = new ReentrantLock();

    }


    @Override
    public int insertRecord(Object obj) {
        return 0;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {


        try {
            myLock.lock();
            System.out.println("EsPipeline resultItems size: " + resultItems.getAll().size() +
                    "\n\tTask uuid: " + task.getUUID());

            logger.debug("EsPipeline resultItems size: " + resultItems.getAll().size() +
                    "\n\tTask uuid: " + task.getUUID());

            CrawlData crawlerData = resultItems.get("crawlerData");

            if (crawlerData != null) {

                this.esClient.add(crawlerData);
                int i = this.esClient.doSetInsert();
                System.out.println("EsPipeline doInsert Successful number: " + i);
                logger.debug("EsPipeline doInsert Successful number: " + i);
                return;
            }

            System.out.println("at EsPipeline, crawler data IS NULL !!!");
            logger.debug("at EsPipeline, crawler data IS NULL !!!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            myLock.unlock();
        }

    }

}
