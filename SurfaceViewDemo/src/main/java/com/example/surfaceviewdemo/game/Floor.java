package com.example.surfaceviewdemo.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

/*地板类*/

public class Floor extends DrawablePart   {
    /*
    * x坐标
    * */
    private int x;
    /*
    * y坐标
    * */
    private int y;

    private Paint mPaint;

    private BitmapShader mBitmapShader;

    private static final float RADIO_Y_POS = 5/6f;


    public Floor(Context context, int gameW, int gameH, Bitmap bitmap) {
        super(context, gameW, gameH, bitmap);

        y = (int)(gameH * RADIO_Y_POS);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        //横向重复，纵向拉伸
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);


    }

    /*
    * 绘制自己
    * */
    @Override
    public void draw(Canvas canvas){
        canvas.save();

        if (-x > mGmaeWidth){
            x = x % mGameHeight;
        }


        //移动到指定的位置
        canvas.translate(x,y);
        mPaint.setShader(mBitmapShader);
        canvas.drawRect(x,0,-x+mGmaeWidth, mGameHeight -y,mPaint);

        canvas.restore();
        mPaint.setShader(null);

        //BitmapShader bitmapShader = new BitmapShader();

    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setX(int x){
        this.x = x;
        if (-x >mGmaeWidth){
            this.x = x % mGmaeWidth;
        }


    }

}
