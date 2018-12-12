package com.example.surfaceviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

public class SurfaceViewTemplate extends SurfaceView implements Runnable, View.OnTouchListener {


    private Thread mThread;
    //volatile 多线程同步，线程间的可见性
    private volatile boolean isRunning;


    private Paint mPaint;

    private int mMinRadius;
    private int mMaxRadius;
    private int mRadius;
    //mFlag用来控制绘制大圆还是小圆，控制半径的变化
    private int mFlag;

    private boolean mDrawable = false;

    //实现两个参数的构造函数
    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);


        SurfaceHolder surfaceHolder = getHolder();
        //添加回调函数
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //监听到surface创建完毕

                isRunning = true;


                //Thread函数要接收runnable对象，所以类要实现runnable接口
                mThread = new Thread(SurfaceViewTemplate.this);
                //调用start时，执行SurfaceView的run方法
                mThread.start();


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                //surface销毁后，没有必要再进行绘制
                isRunning = false;
            }
        });

        //聚焦 可触摸
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
        
        initPaint();



    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        mPaint.setColor(Color.GREEN);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mMaxRadius = Math.min(w,h)/2 -20;
        mRadius = mMinRadius = 30;
    }

    @Override
    public void run() {
        while (isRunning){
            drawSelf();
        }

    }

    private void drawSelf() {
        Canvas canvas = null;

        try {
            //给canvas上锁
            canvas = getHolder().lockCanvas();
            if (canvas != null){
                //进行绘制工作
                //canvas.draw()
                drawCircle(canvas);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if (canvas != null){
                //释放
                getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }

    private void drawCircle(Canvas canvas) {
        //绘制背景
        canvas.drawColor(Color.WHITE);
        //画圆
        canvas.drawCircle(getWidth()/2,getHeight()/2,mRadius,mPaint);

        if (mRadius >= mMaxRadius){
            mFlag = -1;
        }else if (mRadius <= mMinRadius){
            mFlag = 1;
        }
        mRadius += mFlag * 2;
    }

    //点击屏幕暂停绘制
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //Toast.makeText(MainActivity.class,"touch!",Toast.LENGTH_SHORT).show();

        Log.d("hello","touch");

        if (mDrawable == false){
            isRunning = false;
            mDrawable = true;
            return false;
        } else {
            isRunning = true;
            mDrawable = false;
            return true;
        }

//        return false;
    }
}
