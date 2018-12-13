package com.fanxin.android.autotextdemo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class ColorfulFontActivity extends AppCompatActivity {


    NenoTextview nenoTextview;

    OnDoubleClickListener onDoubleClickListener;
    ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*隐藏系统状态栏*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_colorful_font);

//        View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        nenoTextview = (NenoTextview)findViewById(R.id.id_neno_tv);

        nenoTextview.setText("下班了，去大吃一顿啊！");


        nenoTextview.isFocused();
        //nenoTextview.setSelected(true);


        //双击弹出设定界面
        scrollView = (ScrollView)findViewById(R.id.id_scrollView);
        scrollView.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
            @Override
            public void onDoubleClick() {
                Toast.makeText(ColorfulFontActivity.this,"Hello",Toast.LENGTH_SHORT).show();
                Intent SettingIntent = new Intent(ColorfulFontActivity.this,SettingActivity.class);
                startActivity(SettingIntent);
            }
        }));





    }
}
