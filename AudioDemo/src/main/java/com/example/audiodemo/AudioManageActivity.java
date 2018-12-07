package com.example.audiodemo;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AudioManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_manage);
        startService(new Intent(this,MyService.class));
    }

    public void click(View v){
        //1. 获取声音管理器
        AudioManager audioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
        //2. 操作声音
        switch (v.getId()){
            case R.id.btn_up:
//                AudioManager.STREAM_ALARM 警报 闹钟
//                    AudioManager.STREAM_MUSIC 媒体
//                AudioManager.STREAM_NOTIFICATION 通知
                //操作2 调整方向
                //操作3
                /*
                * FLAT_PLAY_SOUND 播放声音
                * FLAT_SHOW_UI 出现音量条
                * 0            什么都没有
                *
                * */
                audioManager.adjustStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE,
                        AudioManager.FLAG_PLAY_SOUND
                        );
                break;
            case R.id.btn_down:
                audioManager.adjustStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER,
                        AudioManager.FLAG_SHOW_UI
                );
                break;
            case R.id.btn_mute:
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC,true);
                break;
            case R.id.btn_unmute:
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC,false);
                break;
        }
    }
}
