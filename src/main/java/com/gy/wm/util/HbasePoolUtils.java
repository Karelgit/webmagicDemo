package com.gy.wm.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;

import java.io.IOException;

/**
 * Created by TianyuanPan on 5/25/16.
 */
public class HbasePoolUtils {

    private static String hostnames;
    private static String port;
    private static Configuration conf = null;
    private static HConnection hConnection = null;


    static {
        conf = HBaseConfiguration.create();
        hostnames = ConfigUtils.getResourceBundle().getString("HBASE_HOSTNAMES");
        port = ConfigUtils.getResourceBundle().getString("HBASE_PORT");
        conf.set("hbase.zookeeper.quorum", hostnames);
        conf.set("hbase.zookeeper.property.clientPort", port);
//        myPool = new HTablePool(conf, 100);
    }

    public static synchronized Configuration getConfiguration() {

        return conf;
    }

    public static synchronized HConnection getHConnection() throws IOException {

        if (hConnection == null) {

       /*
       * 创建一个HConnection
       * HConnection connection = HConnectionManager.createConnection(conf);
       * HTableInterface table = connection.getTable("mytable");
       * table.get(...); ...
       * table.close();
       * connection.close();
       **/

            hConnection = HConnectionManager.createConnection(getConfiguration());

        }

        return hConnection;
    }

}
