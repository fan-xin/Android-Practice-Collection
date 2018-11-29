package com.fanxin.android.thread3demo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;

    private Handler mainHandler;
    private Handler childHandler;

    private static final String TAG = "MainActivity-app";

    //MainActivity是主线程

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.id_btn);
        textView = findViewById(R.id.id_text_view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"click",Toast.LENGTH_SHORT).show();
                //按下按钮以后，主线程给子线程发消息
                Message msg = new Message();
                //msg可以携带参数
                msg.what = 9;
                //发送给子线程
                childHandler.sendMessage(msg);

            }
        });
        //运行子线程；
        new ChildThread().start();
    }

    //自己写子线程
    private class ChildThread extends Thread{
        @Override
        public void run() {
            super.run();

            //初始化消息队列
            Looper.prepare();

            //取出主线程的消息，并且处理
            childHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Log.d(TAG,"接收到子线程发过来的 what"+msg.what);
                }
            };


            //开启消息队列
            Looper.loop();
        }
    }
}
