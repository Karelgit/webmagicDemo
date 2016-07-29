package com.gy.wm.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TianyuanPan on 5/18/16.
 */

/**
 * 爬取数据实体类的处理工具类
 */
public class CrawlerDataUtils {

    private List<Map<String, Object>> fieldList; // 字段列表

    private CrawlerDataUtils() {

        fieldList = new ArrayList();
    }


    /**
     * 获取本类的对象。
     * 此方法中，把 data 实体中的每个字段
     * 映射为：
     *       type：数据类型
     *       name：字段名称
     *       value: 字段值
     * 的键值对，多个字段组成一个映射的字段列表，fieldList。
     * 注意：要映射的实体对象 data 中，每个字段要有 get 和 set 方法。
     * @param data 要映射的数据实体对象
     * @return 本类的一个对象
     */
    public static CrawlerDataUtils getCrawlerDataUtils(Object data) {

        CrawlerDataUtils utils = new CrawlerDataUtils();
        Map<String, Object> infoMap = null;

        Field[] fields = data.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            infoMap = new HashMap();
            infoMap.put("type", fields[i].getType().toString());
            infoMap.put("name", fields[i].getName());

            if (fields[i].getType().toString().equals("boolean"))
                infoMap.put("value", utils.getBooleanFieldValueByName(fields[i].getName(), data));
            else
                infoMap.put("value", utils.getFieldValueByName(fields[i].getName(), data));

            utils.fieldList.add(infoMap);
        }

        return utils;
    }


    private Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
//            System.out.println("getter is: " + getter);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Object getBooleanFieldValueByName(String fieldName, Object o) {

        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "is" + firstLetter + fieldName.substring(1);
//            System.out.println("getter is: " + getter);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * 获取映射号键值对的列表。
     * @return 字段列表 LIST
     */
    public List<Map<String, Object>> getAttributeInfoList() {

        return this.fieldList;
    }

}
