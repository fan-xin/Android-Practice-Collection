package com.fanxin.android.applicationtest;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TimeUtils;

import java.util.concurrent.TimeUnit;

public class MyService extends IntentService {
    private static final String TAG = "MyService-app";
    public MyService() {
        super("MyService");
    }

    //在单独的线程中被调用
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG,"onHandleIntent: "+ getApplication() +", "+this);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"My Servicve: on Destroy"+getApplication());

    }
}
