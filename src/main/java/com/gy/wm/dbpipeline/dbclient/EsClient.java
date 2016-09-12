package com.gy.wm.dbpipeline.dbclient;

import com.alibaba.fastjson.JSON;
import com.gy.wm.model.CrawlData;
import com.gy.wm.util.ConfigUtils;
import com.gy.wm.util.HttpUtils;
import com.gy.wm.util.RandomUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TianyuanPan on 5/9/16.
 */

/**
 * ES索引数据客户端
 */
public class EsClient {

    private String hostname;  // ES服务器主机
    private int port;         // ES服务器端口号
    private String indexName; // ES索引名
    private String typeName;  // ES类型名


    private List<CrawlData> dataList;

    private String requestUrl;


    public EsClient() {

        this.hostname = ConfigUtils.getResourceBundle().getString("ES_HOSTNAME");
        this.port = Integer.parseInt(ConfigUtils.getResourceBundle().getString("ES_PORT"));
        this.indexName = ConfigUtils.getResourceBundle().getString("ES_INDEX_NAME");
        this.typeName = ConfigUtils.getResourceBundle().getString("ES_TYPE_NAME");

        this.requestUrl = "http://" + this.hostname + ":" +
                this.port + "/" + this.indexName + "/" + this.typeName + "/";

        this.dataList = new ArrayList<>();

    }


    public Object getConnection() {

        return null;
    }

    public void closeConnection() {


    }


    /**
     * 数据集插入
     * @return int 受影响的函数
     */
    public int doSetInsert() {

        int count = 0;

        for (int i = 0; i < dataList.size(); ++i) {

            String dataJson = JSON.toJSONString(dataList.get(i));

            try {

                //this.doPut(this.requestUrl + RandomUtils.getRandomString(50) + "_" + new Date().getTime(), dataJson);
                HttpUtils.doPut(this.requestUrl + RandomUtils.getRandomString(50) + "_" + new Date().getTime(), dataJson);
                ++count;

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        this.dataList.clear();

        return count;
    }

    /**
     * 数据集插入
     * @param url  ES服务器数据插入接口 URL
     * @param data 待插入的 json 数据
     * @return int  成功返回 1， 否则返回 0。
     */
    public int doSetInsert(String url, String data) {

        try {


            HttpUtils.doPut(url, data);


        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }

        return 1;
    }

    /**
     * 添加数据到待插入数据列表
     * @param data 爬取的数据对象
     */
    public void add(CrawlData data) {

        this.dataList.add(data);
    }

    /**
     * 获取ES服务器的请求URL
     * @return string 请求URL
     */
    public String getRequestUrl() {

        return requestUrl;
    }

}

