package com.example.broadcaststatic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 18/10/23  18:53
 */
public class BatteryLowReceiver extends BroadcastReceiver {
    private String TAG="BatteryLowReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"action = " + intent.getAction());
    }
}
