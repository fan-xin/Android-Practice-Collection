package com.fanxin.android.statedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity-vv";

    private long createTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //真实的项目，时间要从服务器获取
        createTime = System.currentTimeMillis();

        String formatTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(this.createTime));

        TextView mCreateTimeTextView = (TextView) findViewById(R.id.id_tv_createTime);
        mCreateTimeTextView.setText(formatTime);

        Log.d(TAG,"onCreate: "+this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG,"onDestroy: "+this);

    }
}
