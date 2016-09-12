package com.gy.wm.util;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by TianyuanPan on 5/4/16.
 */

/**
 * 配置工具类
 */
public class ConfigUtils {

    /**
     * 下面这五个字段，是数据库的配置字段(MySQL数据库)
     * 字段分别为： 数据库主机名，端口，数据库名，数据库用户名，数据库用户密码。
     */
    private String hostname;
    private int port;
    private String dbName;
    private String user;
    private String password;


    private static ResourceBundle rb;

    ConfigUtils() {

    }

    /**
     * 获取配置连接工具对象
     * @param prefix  配置项的前缀
     * @return 返回配置工具对象
     */
    public static ConfigUtils getConfigUtils(String prefix) {

        ResourceBundle rb;

        ConfigUtils configUtils = new ConfigUtils();

        rb = PropertyResourceBundle.getBundle("dbconfig");

        configUtils.hostname = rb.getString(prefix + "HOSTNAME");
        configUtils.port = Integer.parseInt(rb.getString(prefix + "PORT"));
        configUtils.dbName = rb.getString(prefix + "DBNAME");
        configUtils.user = rb.getString(prefix + "USER");
        configUtils.password = rb.getString(prefix + "PASSWORD");


        return configUtils;
    }

    /**
     * 获取配置连接工具对象
     * @param prefix  配置项前缀
     * @param configName  配置文件名
     * @return  返回配置工具对象
     */
    public static ConfigUtils getConfigUtils(String prefix, String configName) {

        ResourceBundle rb;

        ConfigUtils configUtils = new ConfigUtils();

        rb = PropertyResourceBundle.getBundle(configName);

        configUtils.hostname = rb.getString(prefix + "HOSTNAME");
        configUtils.port = Integer.parseInt(rb.getString(prefix + "PORT"));
        configUtils.dbName = rb.getString(prefix + "DBNAME");
        configUtils.user = rb.getString(prefix + "USER");
        configUtils.password = rb.getString(prefix + "PASSWORD");


        return configUtils;
    }


    /**
     * 获取 ResourceBundle 对象
     * @return  ResourceBundle 对象
     */
    public static ResourceBundle getResourceBundle() {

        rb = PropertyResourceBundle.getBundle("config");
        return rb;
    }


    public static ResourceBundle getResourceBundle(String configName) {


        rb = PropertyResourceBundle.getBundle(configName);

        return rb;
    }


    public String getHostname() {
        return this.hostname;
    }

    public int getPort() {
        return this.port;
    }

    public String getDbName() {
        return this.dbName;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

}
