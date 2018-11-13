package com.fanxin.android.bledemo;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity-app";
    //蓝牙适配器
    private BluetoothAdapter bluetoothAdapter;
    private Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions();

        Toast.makeText(MainActivity.this, "hello",Toast.LENGTH_SHORT).show();

        toast = Toast.makeText(MainActivity.this, " ",Toast.LENGTH_SHORT);

        final BluetoothManager bluetoothManager =
                (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        Log.d(TAG,"开始检查手机");

        if (bluetoothAdapter != null){
            showToast("手机支持蓝牙！");
            Log.d(TAG,"手机支持蓝牙");
        }else {
            finish();
        }


        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)){
            showToast("手机不支持蓝牙BLE功能");
            finish();
        }else {
            showToast("手机支持蓝牙BLE功能");
            Log.d(TAG,"手机支持蓝牙BLE功能");
        }
        Log.d(TAG,"检查手机结束");

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bluetoothAdapter!=null&&bluetoothAdapter.isEnabled()){
            //申请打开蓝牙功能
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableIntent);
        }
    }

    private void showToast(String msg){
        toast.setText(msg);
        toast.show();
    }
    
    /**
    *动态申请权限
    *@author Fan Xin <fanxin.hit@gmail.com>
    *@time
    */

    private void requestPermissions() {
        if (PackageManager.PERMISSION_GRANTED ==
                ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)){
            //已经有权限，直接做操作

        }else {
            //没有权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)){

            }else {
                //申请权限
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},0);

            }
        }
    }
}
