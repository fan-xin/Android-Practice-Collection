package com.example.surfaceviewdemo.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.example.surfaceviewdemo.MainActivity;
import com.example.surfaceviewdemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*游戏主类*/

public class FlappyBird extends SurfaceView implements Runnable {
    /**
     * 用于绘制的线程
     * */
    private Thread mThread;
    /*
    * 线程的控制开关
    * */
    private volatile boolean isRunning;
    //引入资源
    private Bitmap mBg;
    private Bitmap mBirdBm;
    private Bitmap mFloorBm;

    /*定义实例*/
    private Floor mFloor;
    private Bird mBird;

    private RectF mDestRect;

    private int mSpeed;
    /*
    * 触摸上升的距离，因为是上升，所以是负值
    * */
    private static final int TOUCH_UP_SIZE = -16;
    private  int mBirdUpDis;
    /*
    * 鸟自由下落的距离
    * */
    private static final int SIZE_AUTO_DOWN = 2;
    private int mAutoDownDis;

    private int mTmpBirdDis;
    /**
     * 管道相关
     */
    /*上部的管道*/
    private Bitmap mPipeTop;
    /*
    * 下面的管道
    * */
    private Bitmap mPipeBottom;
    private RectF mPipeRect;
    /*管道宽度*/
    private int mPipeWidth;
    /*
    * 管道宽度 60dp
    * */
    private static final int PIPE_WIDTH = 60;
    /*管道数组*/
    private List<Pipe> mPipes = new ArrayList<Pipe>();

    /*屏幕的高和宽*/
    private int mHeight;
    private int mWidth;
    /*
    * 两个管道的距离
    * */
//    private final int PIPE_DIS_BETWEEN_TWO = Utils.dp2px(getContext(),50);
    private final int PIPE_DIS_BETWEEN_TWO = Utils.dp2px(getContext(),30+(int)(Math.random()*(150-50+1)));
    /*
    * 记录移动的距离
    *
    * */
    private int mTmpMoveDistance;
    /*
    * 分数相关
    *
    * */
    private final int[] mNums = new int[]{
            R.drawable.n0,
            R.drawable.n1,
            R.drawable.n2,
            R.drawable.n3,
            R.drawable.n4,
            R.drawable.n5,
            R.drawable.n6,
            R.drawable.n7,
            R.drawable.n8,
            R.drawable.n9
    };

    private Bitmap[] mNumBitmap;
    //private int mGrade = 100;
    private int mGrade = 0;
    //mGrade = 0;

    /*
    * 单个数字的高度为屏幕的1/15
    * */

    private static final float RADIO_SINGLE_NUM_HEIGHT = 1/15F;

    /*单个数字的宽度*/
    private int mSingleGradeWidth;

    /*单个数字的高度*/
    private int mSingleGradeHeight;

    /*
    * 单个数字的范围
    * */
    private RectF mSingleNumRecfF;

    /*
    * 游戏的状态
    * */
    private enum GameStatus{
        WAITING, RUNNING, OVER
    }

    /*
    * 记录游戏的状态
    * */
    private GameStatus mStatus = GameStatus.WAITING;

    //记录需要移除的管道
    private List<Pipe> mNeedRemovePipe = new ArrayList<Pipe>();



    private int mRemovedPipe = 0;

    private TextView textView;







    //实现两个参数的构造函数
    public FlappyBird(Context context, AttributeSet attrs) {
        super(context, attrs);


        SurfaceHolder surfaceHolder = getHolder();
        //添加回调函数
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            //开启线程
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //监听到surface创建完毕
                isRunning = true;
                //Thread函数要接收runnable对象，所以类要实现runnable接口
                mThread = new Thread(FlappyBird.this);
                //调用start时，执行SurfaceView的run方法
                mThread.start();

                //再次进入画面时，重置鸟的位置
                mBird.reset();
                mTmpBirdDis = 0;


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
        //设置屏幕常亮
        setKeepScreenOn(true);

        mSpeed = Utils.dp2px(getContext(),2);
        mBirdUpDis = Utils.dp2px(getContext(),TOUCH_UP_SIZE);
        mAutoDownDis = Utils.dp2px(getContext(),SIZE_AUTO_DOWN);

        mPipeWidth = Utils.dp2px(getContext(),PIPE_WIDTH);

        initRes();

        textView = (TextView)findViewById(R.id.id_textvview);
        //textView.setText("hello");


    }

    //初始化图片
    private void initRes() {
        mBg = BitmapFactory.decodeResource(getResources(), R.drawable.bg1);
        //mBirdBm = BitmapFactory.decodeResource(getResources(), R.drawable.helicopter);
        mBirdBm = BitmapFactory.decodeResource(getResources(), R.drawable.b1);
        mFloorBm = BitmapFactory.decodeResource(getResources(), R.drawable.floor_bg);
        mPipeTop = BitmapFactory.decodeResource(getResources(),R.drawable.g2);
        mPipeBottom = BitmapFactory.decodeResource(getResources(),R.drawable.g1);

        /*初始化分数的图片*/
        mNumBitmap = new Bitmap[mNums.length];
        for (int i = 0; i < mNumBitmap.length; i++) {
            mNumBitmap[i] = BitmapFactory.decodeResource(getResources(),mNums[i]);
        }

    }

    //初始尺寸发生了变化
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        mWidth = w;
        mHeight = h;

        mDestRect = new RectF(0,0,w,h);
        /*
        * 重新绘制地板和鸟
        * */
        mFloor = new Floor(getContext(),w,h,mFloorBm);
        mBird = new Bird(getContext(),w,h,mBirdBm);

        //管道的矩形
        mPipeRect = new RectF(0,0,mPipeWidth,h);

        //管道动态生成，没必要在这里生成
//        Pipe pipe = new Pipe(getContext(),w,h, mPipeTop,mPipeBottom);
//        mPipes.add(pipe);

        //初始化分数
        mSingleGradeHeight = (int)(h*RADIO_SINGLE_NUM_HEIGHT);
        mSingleGradeWidth = (int)(mSingleGradeHeight*1.0F/mNumBitmap[0].getHeight()*mNumBitmap[0].getHeight());
        mSingleNumRecfF = new RectF(0,0,mSingleGradeWidth,mSingleGradeHeight);

    }

    @Override
    public void run() {
        while (isRunning){

            //每隔50毫秒绘制一次
            long start = System.currentTimeMillis();
            drawSelf();
            long end = System.currentTimeMillis();

            if (end - start < 50){
                try {
                    Thread.sleep(50-(end-start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private void drawSelf() {
        Canvas canvas = null;

        try {
            //给canvas上锁
            canvas = getHolder().lockCanvas();

            if (canvas != null){

                //进行绘制工作
                //绘制背景
                drawBg(canvas);

                //绘制鸟
                drawBird(canvas);

                //绘制管道
                drawPipes(canvas);

                //绘制分数
                drawGrades(canvas);

                //绘制地板
                //地板要在管道之后绘制，这样才能遮住管道
                drawFloor(canvas);

                //执行逻辑
                logic();



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

    /**
     * 绘制分数
     *
     * */
    private void drawGrades(Canvas canvas) {
        String grade = mGrade +"";
        canvas.save();
        canvas.translate(mWidth/2 - grade.length()*mSingleGradeWidth/2,1F/8*mHeight);

        /*一个一个绘制单个数字*/
        for (int i = 0; i < grade.length(); i++) {
            String numStr = grade.substring(i,i+1);
            int num = Integer.valueOf(numStr);
            canvas.drawBitmap(mNumBitmap[num],null,mSingleNumRecfF,null);
            canvas.translate(mSingleGradeWidth,0);
        }

        canvas.restore();

    }

    private void drawPipes(Canvas canvas) {
        for (Pipe pipe: mPipes){
            //移动管道的位置
            pipe.setX(pipe.getX() - mSpeed);
            //绘制管道
            pipe.draw(canvas,mPipeRect);
        }
    }

    private void logic() {

        switch (mStatus){
            case WAITING:
                textView.setText("点击任意处开始游戏");




            case RUNNING:



                //更新地板的x坐标，地板移动
                mFloor.setX(mFloor.getX() - mSpeed);

                logicPipe();


                //默认下落，点击时瞬间上升
                mTmpBirdDis += mAutoDownDis;
                mBird.setY(mBird.getY() + mTmpBirdDis);

                //计算分数
                mGrade += mRemovedPipe;

                for (Pipe pipe: mPipes){
                    if (pipe.getX() + mPipeWidth < mBird.getX()){
                        mGrade++;
                    }
                }




                //管道移动
                for (Pipe pipe: mPipes){
                    if (pipe.getX() < -mPipeWidth){
                        mNeedRemovePipe.add(pipe);
                        continue;

                    }

                    pipe.setX(pipe.getX() - mSpeed);
                }
                //移除管道
                mPipes.removeAll(mNeedRemovePipe);

                //管道
                mTmpMoveDistance += mSpeed;

                //生成一个管道
                //if (mTmpMoveDistance >= PIPE_DIS_BETWEEN_TWO){
                if (mTmpMoveDistance >= Utils.dp2px(getContext(),30+(int)(Math.random()*(200-50+1)))){
                    Pipe pipe = new Pipe(getContext(),getWidth(),getHeight(),mPipeTop,mPipeBottom);
                    mPipes.add(pipe);
                    mTmpMoveDistance=0;
                }

                //判断游戏是否结束
                checkGameOver();

                break;

            case OVER:



                if (mBird.getY() < mFloor.getY() - mBird.getmWidth())
                {
                    mTmpBirdDis += SIZE_AUTO_DOWN;
                    mBird.setY(mBird.getY() + mTmpBirdDis);
                }else {
                    /*
                    * 切换状态
                    * */
                    mStatus = GameStatus.WAITING;
                    initPos();

                }

                break;

            default:
                break;
        }



    }

    /**
     * 重置鸟的位置等数据
     *
     * */

    private void initPos() {
        mPipes.clear();
        mNeedRemovePipe.clear();
        /**
         * 重置鸟的位置
         * */
        mBird.setY(mHeight*2/3);
        /**
         * 重置下落速度
         *
         * */
        mTmpBirdDis = 0;

        //分数清零
        mGrade = 0;
    }

    private void logicPipe() {
        for (Pipe pipe:mPipes){
            if (pipe.getX() < -mPipeWidth){
                mNeedRemovePipe.add(pipe);
                mRemovedPipe++;
                continue;
            }
            pipe.setX(pipe.getX() - mSpeed);
        }

    }

    /*
    * 判断游戏是否结束
    * */

    private void checkGameOver() {
        //如果碰到地板
        /**
         * 判断鸟的Y坐标和地板的Y坐标
         * */
        if (mBird.getY() > mFloor.getY() - mBird.getmHeight()){
            mStatus = GameStatus.OVER;
        }

        //如果碰到管道
        for (Pipe wall: mPipes){
            /*
            * 已经穿过的管道则不结束
            * */
            if (wall.getX() + mPipeWidth < mBird.getX()){
                continue;
            }
            /*
            * 调用管道的方法来判断是否重合
            * */
            if (wall.touchBird(mBird)){
                mStatus = GameStatus.OVER;
                break;
            }
        }


    }

    private void drawFloor(Canvas canvas) {

        mFloor.draw(canvas);
    }

    private void drawBird(Canvas canvas) {
        mBird.draw(canvas);

    }

    private void drawBg(Canvas canvas) {
        canvas.drawBitmap(mBg,null,mDestRect, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);

        int action = event.getAction();


        if (action == MotionEvent.ACTION_DOWN){

            switch (mStatus){
                case WAITING:
                    mStatus = GameStatus.RUNNING;
                    break;
                case RUNNING:
                    mTmpBirdDis = mBirdUpDis;
                    break;
                case OVER:
                    break;
            }


        }
        return  true;


    }
}
