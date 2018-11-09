package com.fanxin.android.bluetoothchatdemo;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 10;

    private static final String TAG = "MainActivity-app";

    private Button pairedButton,scanButton;

    private BluetoothAdapter bluetoothAdapater;

    //接受系统发出的广播
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG,"ACTION: "+action);
            if (action.equals(BluetoothDevice.ACTION_FOUND)){
                //找到可以匹配打设备
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d(TAG,"New Device name: "+device.getName());
                Log.d(TAG,"New Device addr: "+device.getAddress());


            }else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
                //搜索结束
                Log.d(TAG,"Discovery Done! ");

            }else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED)){
                //搜索开始
                Log.d(TAG,"Start Discovery! ");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //判断是否有权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
            //        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            //可以判断是否向用户说明，申请该权限原因
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
            }
        }

        bluetoothAdapater = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapater == null){
            Log.e(TAG,"Device not support bluetooth");

        }else {
            Log.e(TAG,"Device support bluetooth");
        }


        pairedButton = (Button)findViewById(R.id.id_button_paired_devices);
        scanButton = (Button)findViewById(R.id.id_button_scan_devices);

        //获取绑定过打设备列表
        pairedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<BluetoothDevice> pairedDevices =  bluetoothAdapater.getBondedDevices();
                for (BluetoothDevice device: pairedDevices){
                    Log.d(TAG,"Device name: "+device.getName());
                    Log.d(TAG,"Device addr: "+device.getAddress());

                }

            }
        });

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果正在扫描，则停止扫描
                if(bluetoothAdapater.isDiscovering()){
                    bluetoothAdapater.cancelDiscovery();
                }
                bluetoothAdapater.startDiscovery();

            }
        });

        IntentFilter filter = new IntentFilter();
        //添加广播类型
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(broadcastReceiver,filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //如果蓝牙没有打开，则请求打开蓝牙
        if (!bluetoothAdapater.isEnabled()){
            //向系统请求开启蓝牙
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            //打开新的Activity，在新的Activity关闭后向前面的Activity传回数据
            //使用onActivityResult获取传回打数据
            //第二个参数为请求码，可以根据业务自己编号
            startActivityForResult(enableIntent,REQUEST_ENABLE_BT);

        }else {
            //已经开启了蓝牙
            Toast.makeText(this, "蓝牙已经开启",Toast.LENGTH_SHORT).show();
        }

    }

    /**
    * 为了得到传回打数据，重写onActivityResult方法
     * requestCode 请求码 调用Activity时传过去的值
     * resultCode 结果码 判断用户的输入
    *@author Fan Xin <fanxin.hit@gmail.com>
    *@time
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断是不是想要判断的开启蓝牙的内容
        if (requestCode == REQUEST_ENABLE_BT ){
            //判断开启的结果
            if (resultCode == RESULT_OK){
                Toast.makeText(this,"蓝牙已经开启！",Toast.LENGTH_SHORT).show();
            }else if (resultCode == RESULT_CANCELED){
                Toast.makeText(this,"蓝牙开启被取消！",Toast.LENGTH_SHORT).show();

            }

        }

    }
}
