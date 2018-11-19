package com.fanxin.android.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

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

    public void Remoteoperate(View view){
        switch (view.getId()){
            case R.id.id_button_1:
                //远程启动服务
//                Intent it = new Intent("com.fanxin.myservice");
                Intent it = new Intent();
                it.setAction("com.fanxin.myservice");
                it.setPackage("com.fanxin.android");
                startService(it);
                Toast.makeText(MainActivity.this,"hello",Toast.LENGTH_SHORT).show();
                break;

            case R.id.id_button_2:
                //远程停止服务
                Intent it2 = new Intent();
                it2.setAction("com.fanxin.myservice");
                it2.setPackage("com.fanxin.android");
                stopService(it2);

                break;

            case R.id.id_button_3:
                //远程绑定服务
                Intent it3 = new Intent();
                it3.setAction("com.fanxin.myservice");
                it3.setPackage("com.fanxin.android");
                bindService(it3,conn,BIND_AUTO_CREATE);
                break;

            case R.id.id_button_4:
                //远程解绑服务
                unbindService(conn);
                break;
        }
    }
}
