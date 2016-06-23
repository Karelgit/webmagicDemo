package com.gy.wm.heartbeat;


import com.gy.wm.heartbeat.handler.SocketHandler;

import java.net.Socket;

/**
 * Created by TianyuanPan on 6/4/16.
 */
public class SchedulerClientSocket {

    private Socket socket;
    private int port;
    private String hostname;
    private SocketHandler handler;


    private SchedulerClientSocket(String hostname, int port) {

        this.hostname = hostname;
        this.port = port;
    }

    public SchedulerClientSocket registerHandler(SocketHandler handler) {

        this.handler = handler;
        return this;
    }

    public static SchedulerClientSocket getClientSocket(String hostname, int port) {

        return new SchedulerClientSocket(hostname, port);
    }

    public byte[] doAction() {


        try {

            socket = new Socket(hostname, port);

            return this.handler.doHandle(socket);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
