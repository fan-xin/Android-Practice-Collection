package com.fanxin.android.autotextdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.jar.Attributes;

/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 18/12/12  12:53
 */
public class AutoText extends AppCompatTextView {

    private int width,height;
    private Paint paintText;
    private float posx,posy;
    private float speed = 0.0f;
    private String text = "Hello";
    private float textWidth = 0;
    private float moveDistance = 0.0f;
    private boolean isStarting = false;
    private static final String TAG = "AutoText-app";


    public AutoText(Context context) {
        super(context);
    }

    public AutoText(Context context, AttributeSet attributes){
        super(context,attributes);
    }

    private void initView(){
        
        paintText = new Paint();
        paintText.setTextSize(50.0f);
        paintText.setColor(Color.BLACK);
        paintText.setTypeface(Typeface.DEFAULT);
        paintText.setAntiAlias(true);
        text = getText().toString();
        textWidth = paintText.measureText(text);
        Log.d(TAG,"textWidth = "+textWidth);
        this.speed = textWidth;
        moveDistance = textWidth*2+width;

    }

    public void initDisplayMetrics(WindowManager windowManager){
        /*获取屏幕大小*/
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        this.width = displayMetrics.widthPixels;
        this.height = displayMetrics.heightPixels;

        initView();

        this.posx = width+textWidth;
        Paint.FontMetrics fm = paintText.getFontMetrics();
        float baseline = fm.descent -fm.ascent;
        this.posy = height/2-baseline;
    }

    public void startScroll(){
        isStarting = true;
        invalidate();
    }

    public void stopScroll(){
        isStarting = false;
        invalidate();
    }

    protected void onDraw(Canvas canvas){
        canvas.drawText(text,posx-speed,posy,paintText);
        if (!isStarting){
            return;
        }
        speed += 2.0f;
        if (speed>moveDistance){
            speed = textWidth;
        }
        invalidate();
    }

}
