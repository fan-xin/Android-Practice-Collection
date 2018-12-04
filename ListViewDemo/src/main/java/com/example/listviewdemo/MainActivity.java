package com.example.listviewdemo;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    ListView mlistView;
    String[] values = new String[]{"aaa","bbb","ccc"};
    String[] title_values = new String[]{"最简单的ListView演示","获取应用列表","获取网络数据","模仿IM聊天布局"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //去除状态栏,要放在加载布局文件之前
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);

        mlistView = (ListView) findViewById(R.id.list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.item,title_values);



        mlistView.setAdapter(adapter);

//        ArrayList<ListView> activityitems = new ArrayList<>();
//        activityitems.add(new ActivtiyItem("1. 应用列表"),LisViewDemoActivity.class);
//
//        //设置adapter
//        mlistView.setAdapter(new MainListAdapater(MainActivity.this, activityitems));

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
//                        Intent intent = new Intent(MainActivity.this, LisViewDemoActivity.class);
//                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "clicked item 1: ",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Intent intent2 = new Intent(MainActivity.this, AppListActivity.class);
                        startActivity(intent2);
                        Toast.makeText(MainActivity.this, "clicked item 2: ",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Intent intent3 = new Intent(MainActivity.this, RequestDataActivity.class);
                        startActivity(intent3);
                        Toast.makeText(MainActivity.this, "clicked item 3: ",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Intent intent4 = new Intent(MainActivity.this, ChatActivity.class);
                        startActivity(intent4);
                        Toast.makeText(MainActivity.this, "clicked item 4: ",Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });



    }




}
