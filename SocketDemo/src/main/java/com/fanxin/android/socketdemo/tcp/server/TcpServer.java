package com.fanxin.android.socketdemo.tcp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 18/12/03  20:29
 */
public class TcpServer {

    public void start(){
        ServerSocket serverSocket = null;
        try {

            serverSocket = new ServerSocket(9090);

            MsgPool.getsInstance().start();

            while (true){
                //等待TCP连接的到来
                //socket表示连接上的客户端
                Socket socket = serverSocket.accept();
                System.out.println("ip: "+socket.getInetAddress().getHostAddress()+
                        ", port = "+socket.getPort()+" is online.");

                //等待请求的到来，每次接收到一个请求，就新建一个线程
                //每一个client task都是一个监听者
                ClientTask clientTask = new ClientTask(socket);
                MsgPool.getsInstance().addMsgComingListener(clientTask);
                clientTask.start();



//                //获取输入流
//                socket.getInputStream();
//                //写入客户端
//                socket.getOutputStream();
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        new TcpServer().start();
    }
}
