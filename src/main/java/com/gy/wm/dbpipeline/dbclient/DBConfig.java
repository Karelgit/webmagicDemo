package com.gy.wm.dbpipeline.dbclient;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by TianyuanPan on 5/4/16.
 */
public class DBConfig {


//    private final static ResourceBundle dbconfig = PropertyResourceBundle.getBundle("dbconfig");

    private final static String hostname = "108.108.108.8"; //dbconfig.getString("HOSTNAME");
    private final static int port = 3306;//Integer.parseInt(dbconfig.getString("PORT"));
    private final static String DBName = "wifi_ac";//dbconfig.getString("DBNAME");
    private final static String user = "builder";//dbconfig.getString("USER");
    private final static String password = "builder";//dbconfig.getString("PASSWORD");


    public static String getHostname() {
        return hostname;
    }

    public static int getPort() {
        return port;
    }

    public static String getDBName() {
        return DBName;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }
}
