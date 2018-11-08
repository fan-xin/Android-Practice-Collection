package com.fanxin.android.bluetoothchatdemo;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
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

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG,"ACTION: "+action);
            if (action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d(TAG,"New Device name: "+device.getName());
                Log.d(TAG,"New Device addr: "+device.getAddress());


            }else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
                Log.d(TAG,"Discovery Done! ");

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapater = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapater == null){
            Log.e(TAG,"Device not support bluetooth");

        }else {
            Log.e(TAG,"Device support bluetooth");
        }


        pairedButton = (Button)findViewById(R.id.id_button_paired_devices);
        scanButton = (Button)findViewById(R.id.id_button_scan_devices);

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
        if (!bluetoothAdapater.isEnabled()){
            //向系统请求开启蓝牙
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent,REQUEST_ENABLE_BT);

        }else {
            //已经开启了蓝牙
            Toast.makeText(this, "蓝牙已经开启",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT){
            Toast.makeText(this,"蓝牙已经开启kkkkk",Toast.LENGTH_SHORT).show();
        }

    }
}
