package com.example.surfaceviewdemo;

import android.app.ActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


//准备进入游戏的页面

public class FrontActivity extends AppCompatActivity {

    private Button btn_start_game, btn_exit_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        btn_start_game = (Button)findViewById(R.id.id_btn_start_game);
        btn_start_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FrontActivity.this,MainActivity.class));
            }
        });

        btn_exit_game = (Button)findViewById(R.id.id_btn_exit_game);
        btn_exit_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.exit(0);
//                onDestroy();
//                onDestroy();
//                finish();
//                System.exit(0);
//                android.os.Process.killProcess(android.os.Process.myPid());
//                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//                am.restartPackage(getPackageName());
//                onDestroy();

                Toast.makeText(FrontActivity.this,"当前已经是最新版本",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
