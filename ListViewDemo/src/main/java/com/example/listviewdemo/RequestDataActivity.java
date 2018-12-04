package com.example.listviewdemo;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.listviewdemo.adapter.RequestDataAdapter;
import com.example.listviewdemo.model.LessonResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class RequestDataActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);




        setContentView(R.layout.activity_request_data);

        if (getSupportActionBar()!= null){
            getSupportActionBar().hide();
        }


        listView = (ListView) findViewById(R.id.id_listview_request_data);


        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //添加底部banner
        View headerView = inflater.inflate(R.layout.header_list_view,null);
        listView.addHeaderView(headerView);
        //添加底部banner
        View footerView = inflater.inflate(R.layout.header_list_view,null);
        listView.addFooterView(footerView);

        new RequestDataAsyncTask().execute();

        //给listview中的item设置点击监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(RequestDataActivity.this,"item clicked :  "+position,Toast.LENGTH_SHORT).show();
            }
        });

    }


//继承自AsyncTask，有三个参数，前两个设置为Void，因为不需要
    //设置第三个参数为String，
class RequestDataAsyncTask extends AsyncTask<Void, Void, String>{

    @Override
    protected String doInBackground(Void... voids) {

        return request("https://www.imooc.com/api/teacher?type=2&page=1");
    }

    private String request(String s) {
        URL url = null;
        try {
            url = new URL(s);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setConnectTimeout(30*1000);
            httpURLConnection.setRequestMethod("GET");

            httpURLConnection.connect();


            int responseCode = httpURLConnection.getResponseCode();
            String responseMessage = httpURLConnection.getResponseMessage();
            if (responseCode == HttpURLConnection.HTTP_OK){
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();

                String line;

                while ((line = bufferedReader.readLine())!= null){
                    stringBuilder.append(line);
                }
                inputStreamReader.close();

                return stringBuilder.toString();
            }else {
                //请求失败的情况
                return null;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


    //请求开始之前
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    //请求开始之后
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //Loading消失，数据处理，刷新界面

        LessonResult lessonResult = new LessonResult();
        try {
            //将s强制转换成json对象
            JSONObject jsonObject = new JSONObject(s);
            //获取到status和msg的内容
            lessonResult.setmStatus(jsonObject.getInt("status"));
            lessonResult.setMessage(jsonObject.getString("msg"));

            List<LessonResult.LessonInfo> lessonInfos = new ArrayList<>();

            JSONArray dataArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {
                LessonResult.LessonInfo lessonInfo = new LessonResult.LessonInfo();
                JSONObject tempJsonObject = (JSONObject) dataArray.get(i);

                lessonInfo.setName(tempJsonObject.getString("name"));

                lessonInfos.add(lessonInfo);
                //数据量太少，再次加载相同的数据
                lessonInfos.add(lessonInfo);

            }
            lessonResult.setmLessonInfoList(lessonInfos);



        } catch (JSONException e) {
            e.printStackTrace();
        }


        listView.setAdapter(new RequestDataAdapter(RequestDataActivity.this,lessonResult.getmLessonInfoList()));



    }
}

}
