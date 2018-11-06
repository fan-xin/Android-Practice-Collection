package com.fanxin.android.servicedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    //IBinder
    //ServiceConnection用于绑定客户端和service


    //ServiceConnection
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("TAG-app","bind!!!");
            MyService.MyBinder mb = (MyService.MyBinder)service;
            int step = mb.getProcess();
            Log.e("TAG","current progress is: "+step);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void operate(View v){
        switch (v.getId()){
            case R.id.id_button_1:
                //启动服务
                //如果服务已经创建，后续重复启动，操作的都是同一个服务，不会再重新创建
                //除非销毁之后，再重新创建
                Intent it1 = new Intent(this,MyService.class);
                startService(it1);
                break;
            case R.id.id_button_2:
                //停止服务，将服务销毁
                Intent it2 = new Intent(this,MyService.class);
                stopService(it2);
                break;
            case R.id.id_button_3:
                //绑定服务: 用来实现对service执行打任务进行进度监控
                //如果服务不存在，onCreate---onBind----onUnbind----onDestory
                //此时服务没有在后台运行，并且会随着activity的摧毁而解绑销毁
                //如果服务已经存在，那么bindService方法只能使onBind方法被调用
                //而unbindService方法只能使onUnbind被调用
                Intent it3 = new Intent(this,MyService.class);
                bindService(it3, conn, BIND_AUTO_CREATE);
                break;
            case R.id.id_button_4:
                //解绑服务
                unbindService(conn);

                break;
        }

    }
}
