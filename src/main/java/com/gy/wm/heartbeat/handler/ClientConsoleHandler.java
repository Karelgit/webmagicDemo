package com.gy.wm.heartbeat.handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by TianyuanPan on 6/4/16.
 */
public class ClientConsoleHandler implements SocketHandler {

    BufferedReader in;
    PrintWriter out;
    char[] charBuffer;
    String ioString;

    public ClientConsoleHandler() {

        charBuffer = new char[65536];
    }

    @Override
    public byte[] doHandle(Socket socket) {

        try {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);


            System.out.printf("InputMsg >> ");

            String message = new BufferedReader(new InputStreamReader(System.in)).readLine();


            System.out.println("==============================================");
            System.out.println("Send message to server:\n\t" + message);

            out.println(message);
            out.flush();
            int size = in.read(charBuffer, 0, 65536);
            if (size > 0) {
                ioString = new String(charBuffer);
                ioString = ioString.substring(0, size);
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("Back message from server:\n\t" + ioString);
            }


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
