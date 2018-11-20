package com.example.animationdemo.property;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;

import com.example.animationdemo.R;

public class PropertyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);
    }

    public void  onClick(View v){
        switch (v.getId()){
            case R.id.id_btn_valueAnimator:
                final ValueAnimator valueAnimator = ValueAnimator.ofInt(0,100);
                valueAnimator.setDuration(1000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        //变化完成度
                        float animatedFraction = animation.getAnimatedFraction();
                        //变化的值
                        int animatedValue = (int) animation.getAnimatedValue();
                        //打印
                        Log.d("PROPERTY","onAnimationUpdate: "+ String.format("%.3f %d",animatedFraction, animatedValue));
                    }
                });

                valueAnimator.setInterpolator(new LinearInterpolator());

                valueAnimator.start();

                break;

            case R.id.id_textview_alpha:
                Animator alphAnimator = AnimatorInflater.loadAnimator(this, R.animator.animator);
                alphAnimator.setTarget(v);
                alphAnimator.start();
                break;

            case R.id.id_textview_scale:
                ObjectAnimator.ofFloat(v, "scaleX",1.0f,3.0f).start();
                break;

            case R.id.id_textview_translate:
                ViewPropertyAnimator viewPropertyAnimator = v.animate();
                viewPropertyAnimator.translationX(500);
                viewPropertyAnimator.start();
                break;

            case R.id.id_textview_set:
                Animator rotateAnimator = ObjectAnimator.ofFloat(v,"rotation",0,720);
                rotateAnimator.setDuration(1000);

                Animator moveAnimator = ObjectAnimator.ofFloat(v,"x",0,500);
                moveAnimator.setDuration(1000);

                AnimatorSet set = new AnimatorSet();
                //set.playTogether(rotateAnimator,moveAnimator);
                set.playSequentially(rotateAnimator,moveAnimator);
                set.start();

        }
    }
}
