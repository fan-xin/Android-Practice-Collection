package com.fanxin.android.threaddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mStart_Work_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStart_Work_Button = (Button)findViewById(R.id.id_start_work);
        mStart_Work_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //运行4个线程工作
                SaleTickets thread1 = new SaleTickets(5);
                SaleTickets thread2 = new SaleTickets(6);
                SaleTickets thread3 = new SaleTickets(10);
                SaleTickets thread4 = new SaleTickets(7);

                thread1.start();
                thread2.start();
                thread3.start();
                thread4.start();

            }
        });

    }


    private class SaleTickets extends Thread{
        private static final String TAG = "SaleTickets-app";

        private int tickets = 0;

        public SaleTickets(int tickets){
            this.tickets = tickets;
        }

        @Override
        public void run() {
            super.run();
            //sale tickets
            while (tickets > 0){
                saleTicket();
            }
            Log.d(TAG," "+Thread.currentThread().getName()+" sales tickets done !!!");

        }

        private void saleTicket(){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //卖掉一张票
            tickets--;
            Log.d(TAG," "+Thread.currentThread().getName()+" Saled on ticket, left "+ tickets + "tickets!");

        }
    }
}
