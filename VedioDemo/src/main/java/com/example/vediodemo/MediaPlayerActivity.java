package com.example.vediodemo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import java.io.File;
import java.io.IOException;

public class MediaPlayerActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;

    private boolean isPrepared;

    private boolean isPause;

    private SeekBar seekBar;
    private Button buttonPlay;


    private float mRatioHW;


    private Handler mHander = new Handler();

    private Runnable mUpdateProgressRunnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer == null){
                return;
            }

            int currentPosition = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();

            if (seekBar != null & duration > 0){
                int progress = (int) (currentPosition * 1.0f / duration * 1000);
                seekBar.setProgress(progress);
                if (mediaPlayer.isPlaying()){
                    mHander.postDelayed(mUpdateProgressRunnable,1000);
                }
            }

        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
        mHander.removeCallbacks(mUpdateProgressRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);


        initViews();

        initMediaPlayer();
        
        initEvents();

    }

    private void initEvents() {
        //绑定surfaceView
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            //将mediaPlayer的内容展示在surfaceView上
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mediaPlayer.setDisplay(holder);

                if (!isPrepared){
                    return;
                }

                //如果是暂停的状态
                if (isPause){
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                    return;
                }

                if (!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    mHander.post(mUpdateProgressRunnable);
                    buttonPlay.setText("暂停");
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        //给seekbar设置监听器，当进度有变化的时候
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHander.removeCallbacks(mUpdateProgressRunnable);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //获取到视频的总长度
                long duration = mediaPlayer.getDuration();

                //获取进度
                int targetPos = (int)(seekBar.getProgress() * 1.0f /1000 * duration);

                //设置进度
                mediaPlayer.seekTo(targetPos);

                if (mediaPlayer.isPlaying()){
                    mHander.post(mUpdateProgressRunnable);
                }

            }
        });

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    buttonPlay.setText("播放");
                    //不再更新seekbar
                    mHander.removeCallbacks(mUpdateProgressRunnable);

                    isPause = true;


                }else {
                    mediaPlayer.start();
                    buttonPlay.setText("暂停");
                    mHander.post(mUpdateProgressRunnable);
                    isPause = false;
                }
            }
        });



    }

    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(),"/123.mp4");

        try {
            mediaPlayer.setDataSource(file.getAbsolutePath());
            //异步方法进行准备，准备好以后，触发回调函数
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    isPrepared = true;
                    buttonPlay.setEnabled(true);
                    if (!mediaPlayer.isPlaying()){
                        mediaPlayer.start();
                        buttonPlay.setText("暂停");
                        mHander.post(mUpdateProgressRunnable);
                    }

                }
            });

            //获取视频的宽度和高度
            mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                @Override
                public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                    //获取布局的属性 layoutParams是一个Layout的信息包，封装了Layout的位置，高，宽等信息
                    ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
                    mRatioHW = height *1.0f/width;

                    layoutParams.height =(int)( relativeLayout.getWidth()*1.0f/width * height);
                    relativeLayout.setLayoutParams(layoutParams);

                }
            });

            //播放完成的监听器
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mHander.removeCallbacks(mUpdateProgressRunnable);
                    seekBar.setProgress(1000);
                    buttonPlay.setText("播放");
                    isPause = true;



                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //初始化控件
    private void initViews() {

        surfaceView = (SurfaceView)findViewById(R.id.id_surface_view);
        relativeLayout = (RelativeLayout)findViewById(R.id.id_relative_container);
        buttonPlay = (Button)findViewById(R.id.id_btn_play);
        seekBar = (SeekBar)findViewById(R.id.id_seekbar);
        seekBar.setMax(1000);


    }


    //当屏幕尺寸发生变化，不会走生命周期，会调用这个方法
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //手动设置高度
        ViewTreeObserver observer = relativeLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                relativeLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                ViewGroup.LayoutParams lp = relativeLayout.getLayoutParams();
                lp.height = (int)(mRatioHW*relativeLayout.getWidth());
                relativeLayout.setLayoutParams(lp);
            }
        });


    }

    public static void start(Context context){
        Intent intent = new Intent(context, MediaPlayerActivity.class);
        context.startActivity(intent);
    }
}
