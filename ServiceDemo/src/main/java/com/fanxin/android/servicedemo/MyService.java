package com.fanxin.android.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.FileDescriptor;

public class MyService extends Service {
    private static final String TAG = "MyService-app";

    private int i;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"服务创建了！");
        //开启一个线程，从1数到100,用于模拟耗时操作
        new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    for (i = 0; i < 1000; i++) {
                        sleep(100);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }.start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"服务启动了！");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG,"服务解绑了！");
        return super.onUnbind(intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"服务销毁了！");
    }


    //用于绑定的方法
    //在android中用于远程操作对象打一个基本接口
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"服务绑定了！");


        //Binder
        return new MyBinder();

        //对于onBind方法而言，要求返回IBinder对象
        //实际上，我们会自己定义一个内部类，继承Binder类


//        return new IBinder() {
//            @Nullable
//            @Override
//            public String getInterfaceDescriptor() throws RemoteException {
//                return null;
//            }
//
//            @Override
//            public boolean pingBinder() {
//                return false;
//            }
//
//            @Override
//            public boolean isBinderAlive() {
//                return false;
//            }
//
//            @Nullable
//            @Override
//            public IInterface queryLocalInterface(@NonNull String descriptor) {
//                return null;
//            }
//
//            @Override
//            public void dump(@NonNull FileDescriptor fd, @Nullable String[] args) throws RemoteException {
//
//            }
//
//            @Override
//            public void dumpAsync(@NonNull FileDescriptor fd, @Nullable String[] args) throws RemoteException {
//
//            }
//
//            @Override
//            public boolean transact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
//                return false;
//            }
//
//            @Override
//            public void linkToDeath(@NonNull DeathRecipient recipient, int flags) throws RemoteException {
//
//            }
//
//            @Override
//            public boolean unlinkToDeath(@NonNull DeathRecipient recipient, int flags) {
//                return false;
//            }
//        };

    }

    class MyBinder extends Binder{
        //定义自己需要打方法
        //最好实现进度监控
        public int getProcess(){
            return i;
        }


    }
}
