package com.gy.wm.dbpipeline.impl;

import com.gy.wm.dbpipeline.DatabasePipeline;
import com.gy.wm.dbpipeline.dbclient.HbaseClient;
import com.gy.wm.model.CrawlData;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

/**
 * Created by TianyuanPan on 5/18/16.
 */
public class HbasePipeline implements DatabasePipeline {


    private HbaseClient hbaseClient;


    public HbasePipeline() {

        this.hbaseClient = new HbaseClient();
    }

    @Override
    public int insertRecord(Object obj) {
        return 0;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {

        System.out.println("HbasePipeline resultItems size: " + resultItems.getAll().size() +
                "\n\tTask uuid: " + task.getUUID());

        CrawlData crawlerData = resultItems.get("crawlerData");

        if (crawlerData != null) {

            this.hbaseClient.add(crawlerData);
            int i = this.hbaseClient.doSetInsert();
            System.out.println("HbasePipeline doSetInser return code: " + i);
            return;
        }

        System.out.println("crawler data IS NULL !!!");

    }

}
