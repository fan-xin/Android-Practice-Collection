package com.fanxin.android.socketdemo.biz;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 18/12/03  15:03
 */
public class TcpClientBiz {

    private String mServerIp = "127.0.1.1";
    private int mServerPort = 7777;
    private InetAddress mServerAddress;
    private DatagramSocket mSocket;

    private Handler mUIHander = new Handler(Looper.getMainLooper());


    public void sendMsg(String msg){

    }


    public TcpClientBiz(){

        try {
            mServerAddress = InetAddress.getByName(mServerIp);
            mSocket = new DatagramSocket();

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public interface OnMsgReturnedListener{
        void onMsgReturned(String msg);
        void onError(Exception e);

    }

    public void sendMsg(final String msg, final OnMsgReturnedListener listerner){

        //网络交互操作，要在子线程中进行
        new Thread(){
            @Override
            public void run() {
                try {
                    byte[] clientMsgBytes = msg.getBytes();

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
                    final String serverMsg = new String(serverMsgPacket.getData(),0,
                            serverMsgPacket.getLength());
                    System.out.println(" message is "+serverMsg);

                    mUIHander.post(new Runnable() {
                        @Override
                        public void run() {
                            listerner.onMsgReturned(serverMsg);
                        }
                    });



                } catch (final IOException e) {
                    mUIHander.post(new Runnable() {
                        @Override
                        public void run() {
                            listerner.onError(e);
                        }
                    });
                }
            }

        }.start();

    }

    public void onDestory(){
        if (mSocket!= null){
            mSocket.close();
        }
    }

}
