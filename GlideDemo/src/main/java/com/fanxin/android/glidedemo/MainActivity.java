package com.fanxin.android.glidedemo;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.id_iv);
        imageView.setImageResource(R.drawable.p22);
    }

    public void load(View view) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.p11)
                .error(R.drawable.p22)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

//        RequestBuilder requestBuilder = new RequestBuilder();

        Glide.with(this)
                .load("https://media.giphy.com/media/ASd0Ukj0y3qMM/giphy.gif")
                //.load("https://vignette.wikia.nocookie.net/fantendo/images/1/13/Blue_Toad_NSMBW2-BB.png/revision/latest/scale-to-width-down/277?cb=20120728032415")
//                .load("https://vignette.wikia.nocookie.net/fantendo/images/1/13/Blue_Toad_NSMBW2-BB.png/revision/latest/scale-to-width-down/277?cb=20120728032415")
                //.load("https://vignette.wikia.nocookie.net/meep-city/images/f/fe/Abstract_Square_Art.png/revision/latest?cb=20161209192346")
                //.load("https://personal.psu.edu/xqz5228/jpg.jpg")
                //.transition(new DrawableTransitionOptions().crossFade(500))
                //.thumbnail(requestBuilder, )
                .apply(options)
                .into(imageView);

        //.load("https://vignette.wikia.nocookie.net/meep-city/images/f/fe/Abstract_Square_Art.png/revision/latest?cb=20161209192346")


    }
}
