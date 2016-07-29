package com.gy.wm.dbpipeline;

import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by TianyuanPan on 5/4/16.
 */

/**
 * 数据库 Pipeline 接口，继承自 WebMagic 的 Pipeline。
 * @param <T>
 */
public interface DatabasePipeline<T> extends Pipeline {

    public int insertRecord(T obj);
}
