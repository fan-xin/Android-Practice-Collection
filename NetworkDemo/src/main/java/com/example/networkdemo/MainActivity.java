package com.example.networkdemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextView;
    private Button button_get_data;
    private Button button_parse_data;
    private static final String TAG = "MainActivity";
    private String mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setListeners();

    }

    private void findViews() {
        button_parse_data = findViewById(R.id.id_btn_parse_data);
        button_get_data = findViewById(R.id.id_btn_get_data);
        mTextView = findViewById(R.id.id_tv_show_result);
    }

    private void setListeners() {
        button_get_data.setOnClickListener(this);
        button_parse_data.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //获取数据
            case R.id.id_btn_get_data:

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //请求数据
                        requestDataByGet();
                        //创建json对象，大括号
                        //jsonarray，表示中括号

                    }
                }).start();

                Toast.makeText(this,"获取数据成功",Toast.LENGTH_SHORT).show();

                break;
            //解析数据
            case R.id.id_btn_parse_data:
                handleJSONData(mResult);
                break;
        }

    }

    //解析数据
    //解析后的数据把背景颜色修改
    private void handleJSONData(String result) {
        try {

            LessonResult lessonResult = new LessonResult();

            JSONObject jsonObject = new JSONObject(result);
            List<LessonResult.Lesson> lessonList = new ArrayList<>();
            int status = jsonObject.getInt("status");
            JSONArray lessons = jsonObject.getJSONArray("data");

            lessonResult.setmStatus(status);


            if((lessons != null)&&(lessons.length() > 0)){
                //循环获取子元素的内容
                for (int i = 0; i < lessons.length() ; i++) {
                    //新建一个json object
                    JSONObject lesson = (JSONObject) lessons.get(i);

                    int id = lesson.getInt("id");
                    int learner = lesson.getInt("learner");
                    String name = lesson.getString("name");
                    String smallPic = lesson.getString("picSmall");
                    String bigPic = lesson.getString("picBig");
                    String description = lesson.getString("description");

                    //将获取到的内容，赋值给对象
                    LessonResult.Lesson lessonItem = new LessonResult.Lesson();
                    lessonItem.setmId(id);
                    lessonItem.setmLearnNumber(learner);
                    lessonItem.setmBigPicture(bigPic);
                    lessonItem.setmSmallPicture(smallPic);
                    lessonItem.setmDescription(description);
                    lessonItem.setmName(name);
                    lessonItem.setmLearnNumber(learner);

                    //添加到lessonList中
                    lessonList.add(lessonItem);
                }
                lessonResult.setmLessons(lessonList);
            }
            mTextView.setBackgroundColor(Color.parseColor("#e7eecc"));

            mTextView.setText(lessonResult.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void requestDataByGet() {

        try{
            //构建URL
            //String urlStr = "http://www.imooc.com/api/teacher?type=2&page=1";
            URL url = new URL("https://www.imooc.com/api/teacher?type=2&page=1");
            //通过url获取connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置connection
            connection.setRequestMethod("GET"); //设置请求的方法类型
            connection.setConnectTimeout(30*1000); //超时时间　５秒
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept-Charset","UTF-8");
            connection.setRequestProperty("Charset","UTF-8");

            //发起连接
            connection.connect();

            //获取请求码
            int responseCode = connection.getResponseCode();
            String responseMsg = connection.getResponseMessage();

            //判断
            if(responseCode == HttpURLConnection.HTTP_OK){
                InputStream inputStream = connection.getInputStream();
                mResult = streamToString(inputStream);
                //这里最好用hander处理
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText(" ");
                        mTextView.setBackgroundColor(Color.WHITE);
                        mResult = decode(mResult);
                        mTextView.setText(mResult);
                    }
                });

                //post到主线程中
                //                                mTextView.post(new Runnable() {
                //                                    @Override
                //                                    public void run() {
                //
                //                                    }
                //                                });

            }else {
                Log.d(TAG, "run: error " +responseCode + " message:"+responseMsg );
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    private void requestDataByPOST() {
        try{
            //构建URL
            //?type=2&page=1
            String urlStr = "https://www.imooc.com/api/teacher";
            URL url = new URL(urlStr);
            //通过url获取connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置connection
            connection.setRequestMethod("POST"); //设置请求的方法类型
            connection.setConnectTimeout(30*1000); //超时时间　５秒
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept-Charset","UTF-8");
            connection.setRequestProperty("Charset","UTF-8");

            //设置运行的输入输出
            connection.setDoOutput(true);
            connection.setDoInput(true);
            //POST不能使用缓存
            connection.setUseCaches(false);

            //发起连接
            connection.connect();

            String data = "username="+ getEncodeValue("imooc") +"&number="+getEncodeValue("150088886666");
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();

            //获取请求码
            int responseCode = connection.getResponseCode();
            String responseMsg = connection.getResponseMessage();

            //判断
            if(responseCode == HttpURLConnection.HTTP_OK){
                InputStream inputStream = connection.getInputStream();
                mResult = streamToString(inputStream);
                //这里最好用ｈａｎｄｌｅｒ处理
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mResult = decode(mResult);
                        mTextView.setText(mResult);
                    }
                });

            }else {
                Log.d(TAG, "run: error " +responseCode + " message:"+responseMsg );
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private String getEncodeValue(String imooc) {

        try {
            return URLEncoder.encode(imooc, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return imooc;
    }


    /**
     * 将输入流转换成字符串
     *
     * */
    public String streamToString(InputStream is){
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1){
                baos.write(buffer,0,len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return  new String(byteArray);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 将unicode字符转换为UTF-8类型字符串
     *
     * */
    public static String decode(String unicodeStr){
        if(unicodeStr == null){
            return null;
        }
        StringBuilder retBuf = new StringBuilder();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop ; i++) {
            if(unicodeStr.charAt(i) == '\\'){
                if((i < maxLoop -5)
                        && ((unicodeStr.charAt(i+1) == 'u') || (unicodeStr.charAt(i+1) == 'U'))){
                    try{
                        retBuf.append((char) Integer.parseInt(unicodeStr.substring(i+2,i+6),16));
                        i += 5;

                    }catch (NumberFormatException localNumberFormatException){
                        retBuf.append(unicodeStr.charAt(i));
                    }
                }else{
                    retBuf.append(unicodeStr.charAt(i));
                }

            }else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }


}
