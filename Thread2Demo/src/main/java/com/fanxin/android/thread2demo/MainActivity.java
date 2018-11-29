package com.fanxin.android.thread2demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private static final String TAG = "MainActivity-app";
    private SaleTicket mSaleTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button)findViewById(R.id.id_btn);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建新的线程
                mSaleTicket = new SaleTicket(10);
                //点击按钮，启动线程
                new Thread(mSaleTicket,"队伍1").start();

            }
        });


    }

    private class SaleTicket implements Runnable{
        private int tickets = 0;

        public SaleTicket(int tickets){
            this.tickets = tickets;
        }

        //重写run方法
        @Override
        public void run() {
            while (tickets > 0){
                sale();
            }
            Log.d(TAG," "+Thread.currentThread().getName()+"票卖完了");

        }

        private void sale(){
            tickets--;
            Log.d(TAG, " "+Thread.currentThread().getName()+"卖了一张票，还剩"+tickets+" 张票.");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
