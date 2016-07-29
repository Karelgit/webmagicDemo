package com.gy.wm.heartbeat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by TianyuanPan on 6/2/16.
 */

/**
 * 对象序列化和反序列化工具类
 */
public class ObjectSerializeUtils {

    /**
     * 对象序列化为 字节数组
     * @param model 要序列化的对象
     * @return 序列化后的字节数组，异常时返回 null
     */
    public static byte[] serializeToBytes(Object model) {

        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(model);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception ex) {

            ex.printStackTrace();

        }

        return null;
    }


    /**
     * 字节数组装载成对象
     * @param bytes 对象的字节数组
     * @return 装载完成的对象，异常时返回 null
     */
    public static Object getEntityFromBytes(byte[] bytes) {

        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();

        } catch (Exception ex) {

            ex.printStackTrace();
        }
        return null;
    }

}
