package com.fanxin.android.applicationtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity-app";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"on Create: "+getApplication());
        setTitle("mainActivity");

        button = (Button)findViewById(R.id.id_button);


//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "点击按钮",Toast.LENGTH_SHORT).show();
//                switch (v.getId()){
//                    case R.id.id_button:
//                        startActivity(new Intent(MainActivity.this, Main2Activity.class));
//                        break;
//                    case R.id.id_button_start_service:
//                        startService(new Intent(MainActivity.this,MyService.class));
//                        break;
//                    case R.id.id_button_set_username: {
//                        MyApp app = (MyApp) getApplication();
//                        app.setUsername("fanxinshr");
//                        Toast.makeText(MainActivity.this, "Set username: " + app.getUsername(), Toast.LENGTH_SHORT).show();
//                        break;
//                    }
//                    case R.id.id_button_get_username: {
//                        MyApp app = (MyApp) getApplication();
//                        Toast.makeText(MainActivity.this, "get username: " + app.getUsername(), Toast.LENGTH_SHORT).show();
//                        break;
//                    }
//
//                }
//
//            }
//        });


    }


    public void clickbtn(View view){
        Toast.makeText(MainActivity.this, "点击按钮",Toast.LENGTH_SHORT).show();
        switch (view.getId()){
            case R.id.id_button:
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                break;
            case R.id.id_button_start_service:
                Toast.makeText(MainActivity.this, "启动服务",Toast.LENGTH_SHORT).show();
                startService(new Intent(MainActivity.this,MyService.class));
                break;
            case R.id.id_button_set_username: {
                MyApp app = (MyApp) getApplication();
                app.setUsername("fanxinshr");
                Toast.makeText(MainActivity.this, "Set username: " + app.getUsername(), Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.id_button_get_username: {
                MyApp app = (MyApp) getApplication();
                Toast.makeText(MainActivity.this, "get username: " + app.getUsername(), Toast.LENGTH_SHORT).show();
                break;
            }

        }

    }

}
