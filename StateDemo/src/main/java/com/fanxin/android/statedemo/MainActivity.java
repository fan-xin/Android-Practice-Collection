package com.fanxin.android.statedemo;

import android.content.res.Configuration;
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

        if (savedInstanceState != null){
            createTime = savedInstanceState.getLong("createTime");
            Log.d(TAG,"onCreate:bundle:"+Integer.toHexString(savedInstanceState.hashCode()));
        }else {
            //真实的项目，时间要从服务器获取
            createTime = System.currentTimeMillis();
        }

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

    //当系统配置放生变化时，触发的函数
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.d(TAG,"onConfigurationChanged: "+this);
    }

    //保存Activity对象的状态
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG,"onSaveInstanceState: "+this);

        Log.d(TAG,"outState1 is : "+outState);

        outState.putLong("createTime",this.createTime);


        Log.d(TAG,"outState2 is : "+outState);

        Log.d(TAG,"on onSaveInstanceState, bundle is "+Integer.toHexString(outState.hashCode()));



    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d(TAG,"onRestoreInstanceState: "+this);

        Log.d(TAG,"onRestoreInstanceState, bundle is "+Integer.toHexString(savedInstanceState.hashCode()));

        }
}
