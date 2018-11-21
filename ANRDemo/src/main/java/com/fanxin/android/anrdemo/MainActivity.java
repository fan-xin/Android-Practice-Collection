package com.fanxin.android.anrdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity-app";

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button)findViewById(R.id.id_btn);
        //按键按下的返回调函数
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"button clicked!");
                try {
                    //线程休眠10秒
                    Thread.sleep(10000);
                    Log.d(TAG,"button clicked end!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



            }
        });
    }
}
