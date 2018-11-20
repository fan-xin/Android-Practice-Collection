package com.example.animationdemo.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.example.animationdemo.R;

public class ViewAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.renew,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.id_renew:
                //重建整个activity
                recreate();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view){

        switch (view.getId()){
            case R.id.id_textview_alpha:
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
                view.startAnimation(animation);
                break;
            case R.id.id_textview_scale:
                Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.scale);
                view.startAnimation(animation1);
                break;
            case R.id.id_textview_translate:
                Animation animation2 = AnimationUtils.loadAnimation(this,R.anim.translate);
                view.startAnimation(animation2);
                break;
            case R.id.id_textview_rotate:
                Animation animation3 = AnimationUtils.loadAnimation(this,R.anim.rotate);
                view.startAnimation(animation3);
                break;
            case R.id.id_textview_set:
                Animation animation4 = AnimationUtils.loadAnimation(this,R.anim.set);
                view.startAnimation(animation4);
                break;

            case R.id.id_view_Accelerate:
            case R.id.id_view_Linear:
                View viewAccelerate = findViewById(R.id.id_view_Accelerate);
                View viewLinear = findViewById(R.id.id_view_Linear);

                Animation animationAccelerate = AnimationUtils.loadAnimation(this, R.anim.translate);
                Animation animationLinear = AnimationUtils.loadAnimation(this, R.anim.translate);

                //设置变化率
                animationAccelerate.setInterpolator(new AccelerateInterpolator());
                animationLinear.setInterpolator(new LinearInterpolator());

                viewAccelerate.startAnimation(animationAccelerate);
                viewLinear.startAnimation(animationLinear);

                break;

        }
    }
}
