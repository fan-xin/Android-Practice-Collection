package com.example.audiodemo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

/*
* 在这个服务中处理音乐的播放
*
* */

public class MusicService extends Service {
    MediaPlayer player;

    public MusicService() {
    }

    public void play(final int index){


        String path = MusicActivity.pathList.get(index);
        try {
            if(player.isPlaying()){
                player.stop();
            }
            player.reset();
            player.setDataSource(path);
            player.prepare();
            player.start();


            //当一首歌曲播放完毕的时候
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //player(index+1);
                    
                }
            });

            //获取当前歌曲的长度，设置为音乐播放器的最大值
            MusicActivity.musicPro.setMax(player.getDuration());



        }catch (IOException e){
            e.printStackTrace();
        }

    }


    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();

        new Thread(){
            @Override
            public void run() {
                super.run();
                while (true){
                    try {
                        sleep(200);
                        if (player!= null){
                            //将当前位置设置为进度条的进度
                            MusicActivity.musicPro.setProgress(player.getCurrentPosition());


                        }
                        } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        }.start();
    }

    int isStarting = -1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String tag = intent.getStringExtra("tag");
        if (tag.equals("play")){
            int index = intent.getIntExtra("index",0);
            play(index);
            isStarting = 1;

        }else if (tag.equals("pause")){
            player.pause();
            isStarting = 1;

        }else if (tag.equals("start")){
            if (isStarting == -1){
                play(0);
            }else {
                player.start();
            }
        }

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
        isStarting = -1;
        player = null;
    }
}
