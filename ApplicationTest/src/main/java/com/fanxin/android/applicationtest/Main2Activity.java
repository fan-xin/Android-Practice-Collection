package com.fanxin.android.applicationtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    //添加标签
    private static final String TAG = "Main2Activity-app";

    private Button btn_get_username;
    private Button btn_set_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.d(TAG,"on Main2Activity: "+getApplication());
        setTitle("main2activity");

        btn_get_username = (Button)findViewById(R.id.id_get_username);
        btn_set_username = (Button)findViewById(R.id.id_set_username);





    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.id_get_username:{
                MyApp app = (MyApp)getApplication();
                Toast.makeText(Main2Activity.this,app.getUsername(),Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.id_set_username:
                break;


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"on Destory: "+getApplication());
    }
}
