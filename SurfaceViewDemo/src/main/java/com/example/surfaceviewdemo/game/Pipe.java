package com.example.surfaceviewdemo.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

/*管道类*/

public class Pipe {
    private static final float RADIO_BETWEEN_UP_DOWN = 1/5F;


    private static final float RADIO_MAX_HEIGHT = 2/5F;

    private static final float RADIO_MIN_HEIGHT = 1/5F;

    private int x;

    private int height;

    private int margin;

    /*
    * 上管道的图片
    * */
    private Bitmap mTop;

    private Bitmap mBottom;

    /*
    * 获取随机数
    * */
    private static Random random = new Random();

    public Pipe(Context context, int gameWidth, int gameHeight, Bitmap top, Bitmap bottom){
        margin = (int)(gameHeight * RADIO_BETWEEN_UP_DOWN);

        x= gameWidth;

        mTop = top;
        mBottom = bottom;

        randomHeight(gameHeight);
    }

    /*
    * 随机生成一个高度
    * */
    private void randomHeight(int gameHeight) {
        height = random.nextInt((int)(gameHeight * (RADIO_MAX_HEIGHT - RADIO_MIN_HEIGHT)));
        height = (int)(height + gameHeight * RADIO_MIN_HEIGHT);
    }

    public void draw(Canvas canvas, RectF rect){
        canvas.save();
        canvas.translate(x,-(rect.bottom - height));
        //canvas.translate(x,100);
        canvas.drawBitmap(mTop,null,rect,null);
        canvas.translate(0,(rect.bottom -height)+height+margin);
        canvas.drawBitmap(mBottom,null,rect,null);
        canvas.restore();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public boolean touchBird(Bird mBird) {
        /**
         * 如果鸟在管道的范围内，并且不在管道的空隙中则为true
         * */
        if (mBird.getX() + mBird.getmWidth() > x && (mBird.getY()<height || mBird.getY() + mBird.getmHeight() > height + margin)){
            return  true;
        }
        return false;
    }
}
