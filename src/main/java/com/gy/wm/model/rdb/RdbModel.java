package com.gy.wm.model.rdb;

import com.gy.wm.model.CrawlData;
import com.gy.wm.model.InsertSqlModel;

/**
 * Created by TianyuanPan on 6/1/16.
 */

/**
 * 数据关系模型接口
 * 说明：
 *     此接口主要用于生成数据插入SQL
 */
public interface RdbModel {

    /**
     * 设置本模型的字段
     * @param data 数据对象
     * @return 对象
     */
    public Object setThisModelFields(CrawlData data);



    /**
     * 构建插入SQL语句模型对象
     * @param tableName 数据库表名
     * @return 插入SQL语句模型对象
     */
    public InsertSqlModel insertSqlModelBuilder(String tableName);
}
