package com.gy.wm.heartbeat;


import com.gy.wm.heartbeat.handler.SocketHandler;
import com.gy.wm.util.ConfigUtils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by TianyuanPan on 6/15/16.
 */
public class Heartbeart implements Runnable {

    private int checkInterval;
    private SchedulerClientSocket clientSocket;
    private SocketHandler handler;
    private String hostname;
    private int port;
    private boolean finish;
    private Lock myLock;


    public Heartbeart(SocketHandler handler) {


        try {

            this.hostname = ConfigUtils.getResourceBundle().getString("SECHDULER_HOST");
            this.port = Integer.parseInt(ConfigUtils.getResourceBundle().getString("SECHDULER_PORT"));
            this.checkInterval = Integer.parseInt(ConfigUtils.getResourceBundle().getString("HEARTBEAT_CHECKINTERVAL"));

        } catch (Exception e) {

            e.printStackTrace();

        }

        this.finish = false;
        this.myLock = new ReentrantLock();

        this.handler = handler;
//        ((ClientHeartbeatHandler) this.handler).setHeartbeatMsg(heartbeatMsg);
        this.clientSocket = SchedulerClientSocket.getClientSocket(this.hostname, this.port);
        this.clientSocket.registerHandler(this.handler);


    }

    public void setFinish(boolean finish) {
        myLock.lock();
        this.finish = finish;
//        System.out.println("finish = " + this.finish );
        myLock.unlock();
    }


    public void run() {

        while (true) {

            try {

                clientSocket.doAction();

            } catch (Exception e) {

                e.printStackTrace();
            }

            myLock.lock();
            if (finish) {
                myLock.unlock();
//                System.out.println("break =====>>> ");
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
