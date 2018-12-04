package com.example.listviewdemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.v4.view.KeyEventDispatcher;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AppListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_app_list);

        ListView appListView = findViewById(R.id.id_app_list_view);

//        List<String> appNames = new ArrayList<>();
//        appNames.add("aaa");
//        appNames.add("aaa");
//        appNames.add("aaa");
//        appNames.add("aaa");
//        appNames.add("aaa");
//        appNames.add("aaa");

        final List<ResolveInfo> appInfos = getAppInfos();

        //连接起来
        appListView.setAdapter(new AppListAdapter(appInfos));



        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View headerView = inflater.inflate(R.layout.header_list_view,null);

        appListView.addHeaderView(headerView);



        //为每一个Item添加点击的监听事件
        appListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String packageName = appInfos.get(position-1).activityInfo.packageName;
                String className = appInfos.get(position-1).activityInfo.name;

                        ComponentName componetName = new ComponentName(packageName,className);
                        final Intent intent = new Intent();
                        intent.setComponent(componetName);
                        startActivity(intent);
            }
        });

        //添加其他类型的监听器
        appListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });



    }

    //获取所有应用的信息
    private List<ResolveInfo> getAppInfos(){
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        return getPackageManager().queryIntentActivities(intent,0);
    }

    //将数据和视图适配的类
    public class AppListAdapter extends BaseAdapter{

        //要填充的数据
        List<ResolveInfo> mAppInfos;

        public AppListAdapter(List<ResolveInfo> appInfos){
            mAppInfos = appInfos;

        }

        //有多少条数据
        @Override
        public int getCount() {
            return mAppInfos.size();
        }

        //获取当前position位置的一条
        @Override
        public Object getItem(int position) {
            return mAppInfos.get(position);
        }

        //返回当前position位置的这一条ID
        @Override
        public long getItemId(int position) {
            return position;
        }

        //处理view和data之间填充数据的过程
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = new ViewHolder();



            //如果converView为空，则进行创建，并标记为tag
            if (convertView == null){
                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_app_list,null);

                viewHolder.appIconImageView = convertView.findViewById(R.id.id_app_icon_image_view);
                viewHolder.appNameTextView = convertView.findViewById(R.id.id_app_name_tv);


                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }





                //从convertView中获取控件

                //通过系统服务获取app应用的名称activityInfo.loadLabel(getPackageManager())
                viewHolder.appNameTextView.setText(mAppInfos.get(position).activityInfo.loadLabel(getPackageManager()));
                viewHolder.appIconImageView.setImageDrawable(mAppInfos.get(position).activityInfo.loadIcon(getPackageManager()));


//                //给convertView添加监听事件
//                convertView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String packageName = mAppInfos.get(position).activityInfo.packageName;
//                        String className = mAppInfos.get(position).activityInfo.name;
//
//                        ComponentName componetName = new ComponentName(packageName,className);
//
//                        final Intent intent = new Intent();
//                        intent.setComponent(componetName);
//                        startActivity(intent);
//                    }
//                });

                //给textView添加监听事件
//                textView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String packageName = mAppInfos.get(position).activityInfo.packageName;
//                        String className = mAppInfos.get(position).activityInfo.name;
//
//                        ComponentName componetName = new ComponentName(packageName,className);
//
//                        final Intent intent = new Intent();
//                        intent.setComponent(componetName);
//                        startActivity(intent);
//                    }
//                });

                //给ImageView添加监听事件
//                imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String packageName = mAppInfos.get(position).activityInfo.packageName;
//                        String className = mAppInfos.get(position).activityInfo.name;
//
//                        ComponentName componetName = new ComponentName(packageName,className);
//
//                        final Intent intent = new Intent();
//                        intent.setComponent(componetName);
//                        startActivity(intent);
//                    }
//                });



                return convertView;




        }

        //使用ViewHolder缓存
        public class ViewHolder{
            public ImageView appIconImageView;
            public TextView appNameTextView;

        }
    }
}
