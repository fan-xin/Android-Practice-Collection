package com.fanxin.android.applicationtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Main2Activity extends AppCompatActivity {

    //添加标签
    private static final String TAG = "Main2Activity-app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.d(TAG,"on Main2Activity: "+getApplication());
        setTitle("main2activity");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"on Destory: "+getApplication());
    }
}
