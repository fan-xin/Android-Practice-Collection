package com.example.vediodemo;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

public class VideoViewActivity extends AppCompatActivity {
    private VideoView videoView;
    private MediaController mediaController;

    //记录当前的位置信息
    private int currentPosition;
    private boolean isPause;

    private static final String KEY_CUR_POS = "key_cur_pos";
    private static final String KEY_IS_PAUSE = "key_is_pause";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        videoView = (VideoView) findViewById(R.id.id_videoview);

        File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(),"/123.mp4");
        videoView.setVideoPath(file.getAbsolutePath());

        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.start();
    }

    //推出程序，再次回来之后，会执行resume，然后重新开启
    @Override
    protected void onResume() {
        super.onResume();

        videoView.seekTo(currentPosition);
        if (!isPause){
            videoView.start();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        currentPosition = videoView.getCurrentPosition();
        isPause = !videoView.isPlaying();
    }

    public static void start(Context context){
        Intent intent = new Intent(context,VideoViewActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //存储状态和进度
        outState.putInt(KEY_CUR_POS,currentPosition);
        outState.putBoolean(KEY_IS_PAUSE,isPause);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        currentPosition= savedInstanceState.getInt(KEY_CUR_POS);
        isPause=savedInstanceState.getBoolean(KEY_IS_PAUSE);

    }
}
