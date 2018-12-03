package com.fanxin.android.socketdemo.udp;

import android.os.SystemClock;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 18/12/03  14:44
 */
public class UdpServer {
    private InetAddress mInetAddress;
    private int mPort = 7777;

    private DatagramSocket mSocket;

    private Scanner scanner;

    public UdpServer(){
        try {
            mInetAddress = InetAddress.getLocalHost();
            mSocket = new DatagramSocket(mPort,mInetAddress);

            scanner = new Scanner(System.in);
            //按换行键结束输入
            scanner.useDelimiter("\n");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    public void start(){
        while (true){
            //接收数据
            try {

                System.out.println("ip address is"+mInetAddress);

                byte[] buf = new byte[1024];
                DatagramPacket receivedPacket = new DatagramPacket(buf,buf.length);
                mSocket.receive(receivedPacket);//此方法会阻塞

                //已经接收到信息
                InetAddress address = receivedPacket.getAddress();
                int port = receivedPacket.getPort();
                String clientMsg = new String(receivedPacket.getData(),0,
                        receivedPacket.getLength());
                System.out.println(address+"port = "+port+" , message is "+clientMsg);
                //监听用户输入，阻塞
                String returnedMsg = scanner.next();

                //用户已经输入
                //第三个参数是客户端的地址和端口信息
                DatagramPacket sendPacket = new DatagramPacket(returnedMsg.getBytes(),
                        returnedMsg.getBytes().length,receivedPacket.getSocketAddress());
                //发送给客户端
                mSocket.send(sendPacket);




            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args){
        new UdpServer().start();
    }

}
