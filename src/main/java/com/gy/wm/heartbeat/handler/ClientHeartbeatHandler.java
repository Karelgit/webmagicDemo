package com.gy.wm.heartbeat.handler;

import com.alibaba.fastjson.JSON;
import com.gy.wm.heartbeat.model.HeartbeatMsgModel;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by TianyuanPan on 6/15/16.
 */
public class ClientHeartbeatHandler implements SocketHandler {

    private PrintWriter out;
    private String ioString;
    private HeartbeatMsgModel heartbeatMsg;


    public ClientHeartbeatHandler(HeartbeatMsgModel heartbeatMsg) {

        this.heartbeatMsg = heartbeatMsg;

    }

    public void setHeartbeatMsg(HeartbeatMsgModel heartbeatMsg) {

        this.heartbeatMsg = heartbeatMsg;
    }

    @Override
    public byte[] doHandle(Socket socket) {

        try {

            this.heartbeatMsg.setTime(System.currentTimeMillis());
            ioString = JSON.toJSONString(this.heartbeatMsg);
            out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("==============================================");
            System.out.println("Send message to server:\n\t" + ioString);

            out.print(ioString);
            out.flush();
//            int size = in.read(charBuffer, 0, 65536);
//            if (size > 0) {
//                ioString = new String(charBuffer);
//                ioString = ioString.substring(0, size);
//                System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
//                System.out.println("Back message from server:\n\t" + ioString);
//            }


        } catch (Exception ex) {

        } finally {

            try {

                socket.close();

            } catch (Exception ex) {

                ex.printStackTrace();
            }
        }

        return ioString.getBytes();
    }
}
