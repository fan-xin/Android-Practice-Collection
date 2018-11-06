package com.example.listviewrefreshdemo;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Measure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 18/10/22  15:05
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener{

    //顶部布局文件
    View header;

    int headerHeight;//顶部布局文件的高度

    int firstVisibleItem;//当前第一个可见的ｉｔｅｍ的位置
    int scrollState;//listview当前滚动状态
    boolean isRemark;//标记，当前是在ｌｉｓｔｖｉｅｗ的最顶端按下的
    int startY;//按下时的Ｙ值


    int state;//当前的状态
    final int NONE = 0;//正常状态
    final int PULL = 1;//提示下拉状态
    final int RELESE = 2;//提示释放状态
    final int REFLASHING = 3;//正在刷新

    IRefreshListener iRefreshListener;//刷新数据的接口



    public RefreshListView(Context context) {
        super(context);
        initView(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    //加载初始界面。添加顶部布局文件到listview
    private void initView(Context context){
        //加载布局文件
        LayoutInflater inflater = LayoutInflater.from(context);
        header = inflater.inflate(R.layout.header_layout,null);
        measureView(header);
        headerHeight =header.getMeasuredHeight();
        Log.i("tag","headerHeight is "+ Integer.toString(headerHeight));
        topPadding(-headerHeight);
        this.addHeaderView(header);
        //设置监听器
        this.setOnScrollListener(this);
        //this.setOnScrollChangeListener(this);
        //this.setOnScrollChangeListener(this);
    }




    /**
    *通知父布局，占用的宽和高
    *@author Fan Xin <fanxin.hit@gmail.com>
    *@time
    */
    private void measureView(View view){
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null){
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0,0,p.width);
        int height;
        int tempHeight = p.height;
        if (tempHeight > 0){
            height = MeasureSpec.makeMeasureSpec(tempHeight,MeasureSpec.EXACTLY);

        }else {
            height = MeasureSpec.makeMeasureSpec(tempHeight,MeasureSpec.UNSPECIFIED);
        }
        view.measure(width,height);
    }



    /**
    *设置header布局的上边距
    *@author Fan Xin <fanxin.hit@gmail.com>
    *@time 2018 10 22 20：30
    */

    private void topPadding(int topPading){
        header.setPadding(header.getPaddingLeft(),topPading,
                header.getPaddingRight(),header.getPaddingBottom());
        header.invalidate();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //按下，要是否在界面最顶端
                if (firstVisibleItem == 0 ){
                    isRemark = true;
                    startY = (int) ev.getY();

                }
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if (state == RELESE){
                    state = REFLASHING;
                    //加载最新数据
                    reflashViewByState();
                    iRefreshListener.onRefresh();

                    //reflashComplete();

                }else if (state == PULL){
                    state = NONE;
                    isRemark=false;
                    reflashViewByState();

                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
    *根据当前状态，改变界面显示
     * 状态不同，显示的内容不同
    *@author Fan Xin <fanxin.hit@gmail.com>
    *@time
    */
    private void reflashViewByState(){
        TextView textView = header.findViewById(R.id.tip);
        ImageView arrow = header.findViewById(R.id.arrow);
        ProgressBar progressBar = header.findViewById(R.id.progress);
        //设置箭头反转的动画
        RotateAnimation animation = new RotateAnimation(0,180, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(500);
        animation.setFillAfter(true);

        RotateAnimation animation1 = new RotateAnimation(180,0, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(500);
        animation.setFillAfter(true);

        switch (state){
            case NONE:
                //没有下拉的时候，是不显示的
                topPadding(-headerHeight);
                arrow.clearAnimation();
                break;

            case PULL:
                //下拉状态，箭头向下显示，进度条隐藏
                arrow.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                textView.setText("下拉可以刷新");
                arrow.clearAnimation();
                arrow.setAnimation(animation1);
                break;
            case RELESE:
                //下拉之后状态，箭头向上显示，进度条隐藏
                arrow.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                textView.setText("松开可以刷新");
                arrow.clearAnimation();
                arrow.setAnimation(animation);
                break;
            case REFLASHING:
                //设置高度
                topPadding(50);
                arrow.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                textView.setText("正在刷新...");
                arrow.clearAnimation();
                break;

        }

    }

    /**
    *获取完数据
    *@author Fan Xin <fanxin.hit@gmail.com>
    *@time
    */
    public void reflashComplete(){
        state = NONE;
        isRemark=false;
        //刷新界面
        reflashViewByState();
        //设置时间
        TextView lastUpdateTime = header.findViewById(R.id.lastRefrshTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String time = format.format(date);
        lastUpdateTime.setText(time);

    }

    public void setInterface(IRefreshListener iRefreshListener){
        this.iRefreshListener = iRefreshListener;

    }


    /**
    *定义接口，刷新数据
    *@author Fan Xin <fanxin.hit@gmail.com>
    *@time
    */
    public interface IRefreshListener{
        public void onRefresh();
        
    }

    /**
    *判断移动过程中的操作
    *@author Fan Xin <fanxin.hit@gmail.com>
    *@time
    */
    
    private void onMove(MotionEvent ev){
        if (!isRemark){
            return;
        }

        int tempY = (int)ev.getY();
        int space = tempY - startY;
        int topPadding = space - headerHeight;

        switch (state){
            case NONE:
                if (space > 0){
                    state = PULL;
                    reflashViewByState();
                }
                break;
            case PULL:
                topPadding(topPadding);
                if (space > headerHeight+30 && scrollState == SCROLL_STATE_TOUCH_SCROLL ){
                    state = RELESE;
                    reflashViewByState();

                }
                break;
            case RELESE:
                topPadding(topPadding);
                if (space < headerHeight + 30){
                    state = PULL;
                    reflashViewByState();
                }else if (space <= 0){
                    state = NONE;
                    isRemark = false;
                    reflashViewByState();
                }
                break;
        }

    }

    //    @Override
//    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState = scrollState;
    }

}
