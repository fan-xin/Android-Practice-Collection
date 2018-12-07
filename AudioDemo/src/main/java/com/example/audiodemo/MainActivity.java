package com.example.audiodemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //申请权限
        boolean isAllGranted = checkPermissionAllGranted(
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET
                }
        );

        if (isAllGranted){
            Toast.makeText(this, "if",Toast.LENGTH_SHORT).show();
            PlayMusic();
            return;
        }

        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET
                },
                1
        );


//        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if(permission != PackageManager.PERMISSION_GRANTED){
//            //在之前获取权限不成功的情况下，动态的申请权限
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//
//        }
    }

    private void PlayMusic() {
        Toast.makeText(this, "PlayMusic",Toast.LENGTH_SHORT).show();

        startService(new Intent(this,MyService.class));



        //播放源，存在于资源文件夹
        //通过create方法创建的MediaPlayer对象不需要再次调用prepare方法
        //否则会出错，因为在create中已经调用过这个方法了

//        player = MediaPlayer.create(this, R.raw.qwer );
//
//        player.start();

        /*
        //创建MediaPlayer对象，此时是idle状态
        MediaPlayer player = new MediaPlayer();
        //重置，重回idle状态
        player.reset();

        //Environment.getExternalStorageDirectory() 存储根路径的文件夹
        try {

            //adb push qwer.mp3 /storage/emulated/0
            //Environment.getExternalStorageDirectory().getAbsolutePath()的输出结果是/storage/emulated/0
            //Toast.makeText(this, Environment.getExternalStorageDirectory().getAbsolutePath(),Toast.LENGTH_SHORT).show();
            //设置播放源,Initialized状态
            //播放本地音乐
//            player.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath()+"/qwer.mp3");
            //播放网络音乐
            player.setDataSource("https://www.dropbox.com/s/wi4cgkcdvqcz1uz/123.mp3");

            String path = Environment.getExternalStorageDirectory().getAbsolutePath();

            Log.d("TAG",path);

            //在播放之前，进入准备状态
            player.prepare();
            //播放音乐
            player.start();

        } catch (IOException e) {

            e.printStackTrace();
        }*/

    }

    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission: permissions){
            if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Toast.makeText(this, "onRequestPermissionsResult",Toast.LENGTH_SHORT).show();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            boolean isAllGranted = true;

            for (int grant: grantResults){
                if (grant != PackageManager.PERMISSION_GRANTED){
                    isAllGranted = false;
                    break;
                }
            }
            //******
            if (isAllGranted){
                PlayMusic();
            }else {
                Toast.makeText(this, "no permission",Toast.LENGTH_SHORT).show();
            }



        }
    }



    //程序进入后台
//    @Override
//    protected void onStop() {
//        super.onStop();
//        player.pause();
//    }
//
//    //程序从后台回到前台，音乐继续
//    @Override
//    protected void onStart() {
//        super.onStart();
//        player.start();
//    }
//
//    //程序结束的时候，调用ondestory函数
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        player.pause();
//        player.pause();
//        player.stop();
//        player.release();
//    }
}
