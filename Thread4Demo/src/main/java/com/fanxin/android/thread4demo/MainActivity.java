package com.fanxin.android.thread4demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ProgressBar progressBar;
    private ImageView imageView;

    //初始化
    private static final String ImageURL = "https://i.ytimg.com/vi/ktlQrO2Sifg/maxresdefault.jpg";
//    private static final String ImageURL = "https://i.ytimg.com/vi/Pb9lPVI2e4o/hqdefault.jpg";
//    private static final String ImageURL = "http://culture.followcn.com/wp-content/uploads/2017/08/timg457645-e1503658131756.jpg";

    public String filename = "local_temp_image";

    //调试用TAG
    private static final String TAG = "MainActivity-app";

    //handler
    private Handler handler;

    //定义消息变量
    public final int UPDATE_PROGRESS = 1000;
    public final int UPDATE_DONE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化UI元素
        button = (Button)findViewById(R.id.id_btn);
        progressBar = (ProgressBar)findViewById(R.id.id_progressbar);
        imageView = (ImageView)findViewById(R.id.id_image_view);

        //添加事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击按钮，清空上一次显示的图片
                imageView.setImageResource(R.drawable.image);
                //点击按钮，执行下载线程
                new ImageDownloader().start();
            }
        });

        progressBar.setProgress(0);
        progressBar.setMax(100);

        //在主线程中接收消息
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int what = msg.what;
                //判断消息的内容
                if (what == UPDATE_PROGRESS){
                    //进度通过参数1传递
                    progressBar.setProgress(msg.arg1);
                }else if (what == UPDATE_DONE){
                    Toast.makeText(MainActivity.this,"下载完成",Toast.LENGTH_SHORT).show();

                    Bitmap bitmap = BitmapFactory.decodeFile(getFileStreamPath(filename).getAbsolutePath());

                    imageView.setImageBitmap(bitmap);

                    //更新按钮的显示文字
                    button.setText("重新下载图片");

                }
            }
        };

    }

    //新建一个线程，执行下载工作
    class ImageDownloader extends Thread{
        //重写run方法
        @Override
        public void run() {
            super.run();

            URL url = null;
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                url = new URL(ImageURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(false);
                //设置timeout的时间
                connection.setConnectTimeout(20*1000);

                inputStream = connection.getInputStream();
                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                byte[] data = new byte[1024];
                int seg = 0;
                //获取内容的长度，用于计算进度
                long total = connection.getContentLength();
                long current = 0;

                while ((seg = inputStream.read(data))!= -1){
                    outputStream.write(data,0,seg);
                    current += seg;
                    int progress = (int)((float)current/total*100);

                    //在控制台输出当前进度
                    Log.d(TAG,"当前的下载进度是"+progress+"%");

                    //将进度的数据通过消息传给主线程
                    handler.obtainMessage(UPDATE_PROGRESS,progress,-1).sendToTarget();

                    //系统休眠30毫秒
                    SystemClock.sleep(30);
                }

                if (connection != null){
                    connection.disconnect();
                }
                if (inputStream != null){
                    inputStream.close();
                }
                if (outputStream != null){
                    outputStream.close();
                }

                if (handler !=null){
                    handler.obtainMessage(UPDATE_DONE).sendToTarget();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }



}
