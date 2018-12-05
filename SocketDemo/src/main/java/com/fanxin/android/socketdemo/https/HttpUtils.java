package com.fanxin.android.socketdemo.https;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 18/12/05  14:50
 */
public class HttpUtils {
    public interface HttpListener{
        void onSuccess(String content);
        void onFail(Exception ex);
    }

    private static Handler mUIHander = new Handler(Looper.getMainLooper());




    public static void doGet(final String urlStr, final HttpListener listener){
        //get请求
        //内部的异步处理
        new Thread(){
            @Override
            public void run() {
                InputStream inputStream = null;
                try {
                    URL url = new URL(urlStr);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);
                    //进行连接
                    connection.connect();

                    //获取input stream
                    inputStream = connection.getInputStream();
                    byte[] buf = new byte[2048];
                    int len = -1;
                    final StringBuilder content = new StringBuilder();
                    while ((len = inputStream.read(buf))!= -1){
                        content.append(new String(buf,0,len));
                    }

                    mUIHander.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onSuccess(content.toString());

                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    listener.onFail(e);
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (inputStream!=null){
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        }.start();
    }
}
