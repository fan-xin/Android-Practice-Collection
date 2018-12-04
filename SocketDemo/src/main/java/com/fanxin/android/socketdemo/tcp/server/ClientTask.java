package com.fanxin.android.socketdemo.tcp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 18/12/03  20:48
 */
public class ClientTask extends Thread implements MsgPool.MsgComingListener {

    private Socket mSocket;

    private InputStream inputStream;
    private OutputStream outputStream;

    public ClientTask(Socket socket){

        try {
            mSocket = socket;
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //等待客户端写信息，读取客户端的信息，并且发送给客户端信息
    @Override
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        //等待客户端发送消息，如果没有消息，则在readline的地方阻塞
        try {
            while ((line = br.readLine())!= null){
                //如果读取到客户端的信息
                System.out.print("read = "+line);
                //转发消息至其他客户端socket
                //把消息传送给MsgPool
                MsgPool.getsInstance().sendMsg(mSocket.getPort()+" : "+line);

            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //回调函数
    //实现接口的方法
    @Override
    public void onMsgComing(String msg) {
        //获取到新的消息
        //将消息输出
        try {
            outputStream.write(msg.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
