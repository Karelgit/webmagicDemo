package com.gy.wm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TianyuanPan on 6/1/16.
 */

/**
 * 插入SQL模型类
 */
public class InsertSqlModel {

    private String tableName;  // 数据库表名
    private Map<String, Object> keyValuePair; // 字段的键值对
    private List<String> keys; // 字段（键）列表，即数据库表的字段集

    /**
     * 无参数构造方法
     */
    public InsertSqlModel() {
        this.keyValuePair = new HashMap<>();
        this.keys = new ArrayList<>();
        this.tableName = "";
    }

    /**
     * 有参数构造方法
     * @param tableName 数据库表名
     */
    public InsertSqlModel(String tableName) {
        this.keyValuePair = new HashMap<>();
        this.keys = new ArrayList<>();
        this.tableName = tableName;
    }

    /**
     * 添加键值对
     * @param key   键名
     * @param value 键值
     * @return  返回键值
     */
    public Object addKeyValue(String key, Object value) {

        keys.add(key);
        return this.keyValuePair.put(key, value);
    }

    /**
     * 删除一个键值对
     * @param key  键名
     * @return 返回删除的值
     */
    public Object deleKeyValue(String key) {
        int i = 0;
        for (String k : keys) {
            if (k.equals(key)) {
                keys.remove(i);
                break;
            }
            ++i;
        }
        return this.keyValuePair.remove(key);
    }

    /**
     * 获取键的值
     * @param key  键名
     * @return 键值
     */
    public Object getKeyValue(String key) {

        return this.keyValuePair.get(key);
    }

    /**
     * 获取插入SQL语句
     * @return sql语句
     */
    public String getInsertSql() {

        String prefix = "INSERT INTO " + this.tableName + " ";
        String attr = new String();
        String value = new String();

        int i = 0;
        for (String k : keys) {
            ++i;
            attr += k;
            value += keyValuePair.get(k);

            if (i != keys.size()) {
                attr += ",";
                value += ",";
            }
        }

        return prefix + "(" + attr + ")" + " VALUES (" + value + ")";
    }

    /**
     * 获取数据库表名
     * @return 返回表名
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 设置数据库表名
     * @param tableName  表名
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
