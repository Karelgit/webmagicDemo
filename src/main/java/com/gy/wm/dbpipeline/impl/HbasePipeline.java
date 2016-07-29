package com.gy.wm.dbpipeline.impl;

import com.gy.wm.dbpipeline.dbclient.HbaseClient;
import com.gy.wm.model.CrawlData;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by TianyuanPan on 5/18/16.
 */

/**
 * Hbase Pipeline 类
 */
public class HbasePipeline extends BaseDBPipeline {


    private HbaseClient hbaseClient;
    private Lock  mylock;


    public HbasePipeline() {

        this.hbaseClient = new HbaseClient();
        this.mylock = new ReentrantLock();
    }

    @Override
    public int insertRecord(Object obj) {
        return 0;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {

        try {

            mylock.lock();

            System.out.println("HbasePipeline resultItems size: " + resultItems.getAll().size() +
                    "\n\tTask uuid: " + task.getUUID());

            logger.debug("HbasePipeline resultItems size: " + resultItems.getAll().size() +
                    "\n\tTask uuid: " + task.getUUID());

            CrawlData crawlerData = resultItems.get("crawlerData");

            if (crawlerData != null) {

                this.hbaseClient.add(crawlerData);
                int i = this.hbaseClient.doSetInsert();
                System.out.println("HbasePipeline doInsert Successful number: " + i);
                logger.debug("HbasePipeline doInsert Successful number: " + i);
                return;
            }

            System.out.println("at HbasePipeline, crawler data IS NULL !!!");
            logger.debug("at HbasePipeline, crawler data IS NULL !!!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            mylock.unlock();
        }

    }

}
