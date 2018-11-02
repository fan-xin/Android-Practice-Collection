package com.fanxin.android.applicationtest;

import android.app.Application;
import android.util.Log;

/**
 * 练习使用Application
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 18/11/01  21:45
 */
public class MyApp extends Application {
    private static final String TAG = "MyApp-app";

    /**
    *先创建的Application,然后再创建Activity
    *@author Fan Xin <fanxin.hit@gmail.com>
    *@time
    */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate: "+ getApplicationInfo());
    }
}
