package com.fanxin.android.socketdemo.tcp.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 18/12/03  21:01
 */
public class MsgPool {
    //单例模式

    private static MsgPool sInstance = new MsgPool();

    public static MsgPool getsInstance(){
        return sInstance;
    }

    //会阻塞的队列
    private LinkedBlockingDeque<String> mQueue =
            new LinkedBlockingDeque<>();

    private MsgPool(){

    }

    //新建独立的线程，从队列中取数据
    public void start(){
        new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        //取到数据
                        String msg = mQueue.take();
                        //通知其他客户端
                        notifyMsgComing(msg);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    //通知有消息到达
    private void notifyMsgComing(String msg) {
        //遍历监听器
        for (MsgComingListener listener:mListeners){
            listener.onMsgComing(msg);
        }
    }

    public interface MsgComingListener{
        void onMsgComing(String msg);
    }

    private List<MsgComingListener> mListeners = new ArrayList<>();

    public void addMsgComingListener(MsgComingListener listener){
        mListeners.add(listener);
    }

    //发送消息到队列
    public void sendMsg(String msg){
        try {
            mQueue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
