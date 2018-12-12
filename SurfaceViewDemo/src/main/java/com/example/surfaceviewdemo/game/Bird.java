package com.example.surfaceviewdemo.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;


/*小鸟类*/

public class Bird extends DrawablePart {

    /*
    * 鸟的横坐标
    *
    * */
    private int x;
    /*
    * 鸟的纵坐标
    *
    * */
    private int y;
    /*
    * 鸟在屏幕上的位置
    * */
    private static final float RADIO_Y_POS = 2/5f;
    //30dp
    /*
    * 每次下降30个像素
    * */
    private static final int WIDTH_BIRD = 30;
    /*
    * 鸟的宽度
    * */
    private int mWidth;
    /*
    * 鸟的高度
    *
    * */
    private int mHeight;
    /*
    * 鸟的绘制范围
    * */
    private RectF mRect = new RectF();

    /**
     * 构造函数
     * */
    public Bird(Context context, int gameW, int gameH, Bitmap bitmap) {
        super(context, gameW, gameH, bitmap);

        /*
        * 鸟的位置
        * */
        /**
         * y坐标要根据用户的交互进行变化，初始位置在屏幕的1/2的位置
         * */
        y = (int)(gameH * RADIO_Y_POS);
        mWidth = Utils.dp2px(context,WIDTH_BIRD);
        mHeight = (int)(mWidth * 1.0f /bitmap.getWidth() * bitmap.getHeight());

        /**
         * 鸟一直在屏幕的水平位置上，x坐标不变化
         * */
        x = gameW / 2 - mWidth / 2;

    }


    /*
    * 绘制鸟自己
    * */
    @Override
    public void draw(Canvas canvas) {

        //动态设置mRect的值
        mRect.set(x,y,x+mWidth,y+mHeight);

        canvas.drawBitmap(mBitmap,null,mRect,null);

    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void reset() {
        y = (int)(mGameHeight * RADIO_Y_POS);
    }

    public int getmHeight() {
        return mHeight;
    }

    public int getX() {
        return x;
    }

    public int getmWidth() {
        return mWidth;
    }
}
