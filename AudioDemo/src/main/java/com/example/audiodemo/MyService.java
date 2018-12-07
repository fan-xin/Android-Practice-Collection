package com.example.audiodemo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class MyService extends Service {

    private MediaPlayer player;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //创建MediaPlayer对象，此时是idle状态
        player = new MediaPlayer();
        //重置，重回idle状态
        player.reset();

        //Environment.getExternalStorageDirectory() 存储根路径的文件夹
        try {

            //adb push qwer.mp3 /storage/emulated/0
            //Environment.getExternalStorageDirectory().getAbsolutePath()的输出结果是/storage/emulated/0
            //Toast.makeText(this, Environment.getExternalStorageDirectory().getAbsolutePath(),Toast.LENGTH_SHORT).show();
            //设置播放源,Initialized状态
            //播放本地音乐
            player.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath()+"/123.mp3");
            //播放网络音乐
//            player.setDataSource("https://www.dropbox.com/s/wi4cgkcdvqcz1uz/123.mp3");

            String path = Environment.getExternalStorageDirectory().getAbsolutePath();

            Log.d("TAG",path);

            //在播放之前，进入准备状态
            player.prepare();
            //播放音乐
            player.start();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //服务的启动必须有明确的start service才能生效
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
    }
}
