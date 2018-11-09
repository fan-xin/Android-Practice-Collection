package com.fanxin.android.applicationtest;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

/**
 * 练习使用Application
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 18/11/01  21:45
 */
public class MyApp extends Application {
    private static final String TAG = "MyApp-app";
    private String username;



    public static String getTAG() {
        return TAG;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
    *先创建的Application,然后再创建Activity
    *@author Fan Xin <fanxin.hit@gmail.com>
    *@time
    */

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate: "+ getApplicationInfo());
        Log.d(TAG, "onCreate: "+Thread.currentThread());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: called with: "+"newConfig = { " + newConfig +" }");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory: ");
    }
}
