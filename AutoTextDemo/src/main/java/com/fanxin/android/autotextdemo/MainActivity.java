package com.fanxin.android.autotextdemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity-app";

    String str = "";
    String str1 = "Hello world";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate: start scroll");

        scroll();
    }

    void scroll(){
        AutoText auto = findViewById(R.id.id_auto_text);
        auto.setText(str1);
        auto.initDisplayMetrics(getWindowManager());
        Log.d(TAG,"scroll: start scroll");
        auto.startScroll();
    }
}
