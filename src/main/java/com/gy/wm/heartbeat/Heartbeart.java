package com.gy.wm.heartbeat;


import com.gy.wm.heartbeat.model.HeartbeatMsgModel;
import com.gy.wm.heartbeat.redisQueue.HeartbeatMsgQueue;
import com.gy.wm.util.ConfigUtils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by TianyuanPan on 6/15/16.
 */

/**
 * 心跳类，实现 Runnable 接口，用于开启心跳线程
 */
public class Heartbeart implements Runnable {

    private int checkInterval; // 心跳间隔
    private HeartbeatMsgModel heartbeatMsgModel; // 心跳消息
    private HeartbeatMsgQueue heartbeatMsgQueue; // 心跳消息队列
    private boolean finish;  // 完成标识
    private Lock myLock;     // 同步锁


    public Heartbeart(HeartbeatMsgModel heartbeatMsgModel) {

        this.heartbeatMsgModel = heartbeatMsgModel;
        this.finish = false;
        this.heartbeatMsgQueue = new HeartbeatMsgQueue();
        this.myLock = new ReentrantLock();

        try {
            this.checkInterval = Integer.parseInt(ConfigUtils.getResourceBundle().getString("HEARTBEAT_CHECKINTERVAL"));
        } catch (NumberFormatException e) {
            this.checkInterval = 10;
            e.printStackTrace();
        }
    }

    public void setFinish(boolean finish) {
        myLock.lock();
        this.finish = finish;
//        System.out.println("finish = " + this.finish );
        myLock.unlock();
    }


    /**
     * 心跳线程循环
     */
    public void run() {

        while (true) {

            try {

                this.heartbeatMsgModel.setTime(System.currentTimeMillis()); // 设置心跳消息时间
                this.heartbeatMsgQueue.setMessage(this.heartbeatMsgModel);  // 设置消息
                this.heartbeatMsgQueue.pushMessage(); // 把消息压入队列

            } catch (Exception e) {

                e.printStackTrace();
            }

            myLock.lock();
            if (finish) {
                myLock.unlock();
//                System.out.println("break =====>>> ");
                this.heartbeatMsgQueue.pushMessage();
                break;
            }
            myLock.unlock();

            try {

                Thread.sleep(this.checkInterval * 1000);

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }
}
