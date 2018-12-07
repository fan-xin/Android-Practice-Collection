package com.fanxin.android.glidedemo;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.id_iv);
    }

    public void load(View view) {
        Glide.with(this)
                .load("https://vignette.wikia.nocookie.net/meep-city/images/f/fe/Abstract_Square_Art.png/revision/latest?cb=20161209192346")
                .into(imageView);


    }
}
