package com.example.listviewrefreshdemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RefreshListView.IRefreshListener {

    RefreshListView listView;
    ArrayList arrayList = new ArrayList();
    //用来判断是否是最新的内容，两个内容交替更新
    boolean is_new_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> data = getData();
        //显示listview的内容
        showList(data);

    }

    private void showList(List<String> data){

        listView = (RefreshListView)findViewById(R.id.main_listview);
        listView.setInterface(this);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,data));
    }

    private List<String> getData(){
        List<String> data = new ArrayList<String>();
        data.add("hello1");
        data.add("hello1");
        data.add("hello1");
        data.add("hello1");
        data.add("hello1");
        data.add("hello1");
        data.add("hello1");
        data.add("hello1");
        data.add("hello1");
        data.add("hello1");

        is_new_data = false;

        return data;
    }

    private List<String> getRefreshData(){
        List<String> data = new ArrayList<String>();
        data.add("hello2");
        data.add("hello2");
        data.add("hello2");
        data.add("hello2");
        data.add("hello2");
        data.add("hello2");
        data.add("hello2");
        data.add("hello2");
        data.add("hello2");
        data.add("hello2");

        is_new_data = true;

        return data;
    }


    @Override
    public void onRefresh() {
        //间隔２秒之后再显示
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //获取到最新的数据
                //getRefreshData();
                //通知界面显示数据
                List<String> data;
                //获取最新的数据
                if (is_new_data == true){
                    data = getData();
                }else {
                    data = getRefreshData();
                }

                showList(data);
                //通知listview刷新数据完毕
                listView.reflashComplete();

            }
        },2000);

    }

    public class ListViewAdapater extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
