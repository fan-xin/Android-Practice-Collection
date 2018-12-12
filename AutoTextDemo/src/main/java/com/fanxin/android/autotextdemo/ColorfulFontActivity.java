package com.fanxin.android.autotextdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class ColorfulFontActivity extends AppCompatActivity {


    NenoTextview nenoTextview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colorful_font);

        nenoTextview = (NenoTextview)findViewById(R.id.id_neno_tv);

        nenoTextview.setText("乔静好棒！ 乔静好棒！乔静好棒！ 乔静好棒！乔静好棒！ 乔静好棒！");


        nenoTextview.setSelected(true);
    }
}
