package com.example.surfaceviewdemo.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/*各个部件的父类*/

public abstract class DrawablePart {

    protected Context mContext;
    protected int mGmaeWidth;
    protected int mGameHeight;
    protected Bitmap mBitmap;

    //构造函数
    public DrawablePart(Context context, int gameW, int gameH,Bitmap bitmap){

        mContext = context;
        mGameHeight = gameH;
        mGmaeWidth = gameW;
        mBitmap = bitmap;

    }

    //绘图函数
    public  abstract  void  draw(Canvas canvas);

}
