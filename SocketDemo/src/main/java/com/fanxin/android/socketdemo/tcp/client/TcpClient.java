package com.fanxin.android.socketdemo.tcp.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 18/12/04  12:23
 */
public class TcpClient {

    private Scanner scanner;

    public TcpClient(){
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
    }

    public void start(){
        try {
            //建立socket
            Socket socket = new Socket("192.0.1.1",9090);

            //获取输入流
            InputStream inputStream = socket.getInputStream();

            //获取输出流
            OutputStream outputStream = socket.getOutputStream();

            //封装流
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

            //读取server端的数据和写入server端的数据
            new Thread(){
                @Override
                public void run() {
                    try{
                        String line = null;
                        while ((line = bufferedReader.readLine()) != null){
                            System.out.println(line);
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }.start();

            //给server端发送数据
            while (true){
                //next是阻塞方法，不断的读取
                String msg = scanner.next();
                bufferedWriter.write(msg);
                bufferedWriter.newLine();
                bufferedWriter.flush();

            }





        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        new TcpClient().start();
    }
}
