package com.fanxin.android.socketdemo.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 18/12/03  15:03
 */
public class UdpClient {

    private String mServerIp = "127.0.1.1";
    private int mServerPort = 7777;
    private InetAddress mServerAddress;
    private DatagramSocket mSocket;
    private Scanner mScanner;

    public UdpClient(){

        try {
            mServerAddress = InetAddress.getByName(mServerIp);
            mSocket = new DatagramSocket();
            mScanner = new Scanner(System.in);
            mScanner.useDelimiter("\n");


        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }


    public void start(){
        while (true){
            try {
                //获取数据
                String clientMsg = mScanner.next();

                byte[] clientMsgBytes = clientMsg.getBytes();

                DatagramPacket clientPacket =
                        new DatagramPacket(clientMsgBytes,
                                clientMsgBytes.length,
                                mServerAddress,
                                mServerPort);
                //向服务器端发送数据
                mSocket.send(clientPacket);

                //接收服务端的数据
                byte[] buf = new byte[1024];
                DatagramPacket serverMsgPacket =
                        new DatagramPacket(buf,buf.length);
                mSocket.receive(serverMsgPacket);

                //已经接收到信息
                InetAddress address = serverMsgPacket.getAddress();
                int port = serverMsgPacket.getPort();
                String serverMsg = new String(serverMsgPacket.getData(),0,
                        serverMsgPacket.getLength());
                System.out.println(" message is "+serverMsg);




            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public static void main(String[] args){
        new UdpClient().start();
    }

}
