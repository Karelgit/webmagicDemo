package com.gy.wm.dbpipeline.dbclient;

/**
 * Created by TianyuanPan on 5/4/16.
 */

/**
 * 数据库客户端接口
 */
public interface DBClient {

    /**
     * 获取数据库连接
     * @return  Object 连接对象
     */
    public  Object  getConnection();

    /**
     * 关闭数据库连接
     */
    public  void    closeConnection();

    /**
     * 插入数据集
     * @return 返回受影响的行数
     */
    public  int     doSetInsert();

    /**
     * 获取客户端的连接状态
     * @return true 或 false
     */
    public  boolean isConnOpen();
}
