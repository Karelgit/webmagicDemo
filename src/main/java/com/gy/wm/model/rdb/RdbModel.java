package com.gy.wm.model.rdb;

import com.gy.wm.model.CrawlData;
import com.gy.wm.model.InsertSqlModel;

/**
 * Created by TianyuanPan on 6/1/16.
 */
public interface RdbModel {

    public Object setThisModelFields(CrawlData data);
    public InsertSqlModel insertSqlModelBuilder(String tableName);
}
