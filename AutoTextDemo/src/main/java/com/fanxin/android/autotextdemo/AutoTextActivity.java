package com.fanxin.android.autotextdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AutoTextActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_text);


        textView = (TextView)findViewById(R.id.id_tv);

        textView.setText("乔静好棒！ 乔静好棒！乔静好棒！ 乔静好棒！乔静好棒！ 乔静好棒！");


        textView.setSelected(true);


    }



}
