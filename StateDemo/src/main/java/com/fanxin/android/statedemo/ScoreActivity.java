package com.fanxin.android.statedemo;


import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ScoreActivity extends AppCompatActivity {

    private static final String TAG = "ScoreActivity-app";

    private android.support.v4.app.Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //fragment = new ScoreFragment();

        FragmentManager fm = getSupportFragmentManager();

        fragment = fm.findFragmentByTag("ScoreFragment");

        if (fragment ==null){
            fragment = new ScoreFragment();
            fm.beginTransaction().replace(android.R.id.content, fragment,"ScoreFragment").commit();

        }

        Log.d(TAG,"onCreate");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }
}
