package com.example.asynctaskdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


/*
* 1. 网络上请求数据
* 2. 布局layout
* 3. 下载之前做什么
* 4. 下载中做什么
* 5. 下载后做什么
*
* **/
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String DOWNLOAD_URL = "https://archive.apache.org/dist/httpd/httpd-2.3.16-beta-deps.tar.gz";
    Button btn_download;
    TextView tv_download_status;
    ProgressBar pb_download;
    private int INIT_PROGESS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new DownloadAsyncTask().execute("fanxin","good");

        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permission != PackageManager.PERMISSION_GRANTED){
            //在之前获取权限不成功的情况下，动态的申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        }

        //初始化视图
        initView();

        //设置点击监听
        setListener();

        //设置UI数据
        setData();


//        DownloadHelper.download(DOWNLOAD_URL, "", new DownloadHelper.OnDownloadListener.SimpleDownloadListener() {
//
//
//            @Override
//            public void onSuccess(int code, File file) {
//
//            }
//
//            @Override
//            public void onFail(int code, File file, String message) {
//
//            }
//        });

        
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            //******
        }
    }

    private void setData() {
        INIT_PROGESS = 0;
        pb_download.setProgress(INIT_PROGESS);
        btn_download.setText("点击下载");
        
    }

    private void setListener() {
        //给btn设置点击监听事件
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //下载的事情

                DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask();




//                DownloadHelper.DownloadAsyncTask downloadAsyncTask = new DownloadHelper.DownloadAsyncTask(DOWNLOAD_URL,"", new DownloadHelper.OnDownloadListener.SimpleDownloadListener() {
//                    @Override
//                    public void onSuccess(int code, File file) {
//
//                    }
//
//                    @Override
//                    public void onFail(int code, File file, String message) {
//
//                    }
//
//                    @Override
//                    public void onProgress(int progress) {
//                        super.onProgress(progress);
//
//                    }
//                });

                //在执行execute的时候进行传参,可以传递任意个参数
                downloadAsyncTask.execute(DOWNLOAD_URL);
            }
        });




    }

    private void initView() {

        btn_download = (Button)findViewById(R.id.id_btn_download);
        tv_download_status = (TextView)findViewById(R.id.id_tv_download_status);
        pb_download = (ProgressBar)findViewById(R.id.id_progressbar);

    }

    /**
     * String 入参
     * Integer 进度
     * Boolean 返回值
     */
    public class DownloadAsyncTask extends AsyncTask<String, Integer, Boolean> {
        String mFilePath;
        /**
         * 在异步任务之前，在主线程中
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // 可操作UI  类似淘米,之前的准备工作
            btn_download.setText("准备下载");
            tv_download_status.setText("下载状态");
            pb_download.setProgress(50);
        }

        /**
         * 在另外一个线程中处理事件
         *
         * @param params 入参  煮米
         * @return 结果
         */
        @Override
        protected Boolean doInBackground(String... params) {
            if(params != null && params.length > 0){
                String apkUrl = params[0];

                try {
                    // 构造URL
                    URL url = new URL(apkUrl);
                    // 构造连接，并打开
                    URLConnection urlConnection = url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();

                    // 获取了下载内容的总长度
                    int contentLength = urlConnection.getContentLength();

                    // 下载地址准备
                    mFilePath = Environment.getExternalStorageDirectory()
                            + File.separator +  "fanxinshr";

                    // 对下载地址进行处理
                    File apkFile = new File(mFilePath);
                    if(apkFile.exists()){
                        boolean result = apkFile.delete();
                        if(!result){
                            return false;
                        }
                    }

                    // 已下载的大小
                    int downloadSize = 0;

                    // byte数组
                    byte[] bytes = new byte[1024];

                    int length;

                    // 创建一个输入管道
                    OutputStream outputStream = new FileOutputStream(mFilePath);

                    // 不断的一车一车挖土,走到挖不到为止
                    while ((length = inputStream.read(bytes)) != -1){
                        // 挖到的放到我们的文件管道里
                        outputStream.write(bytes, 0, length);
                        // 累加我们的大小
                        downloadSize += length;
                        // 发送进度
                        publishProgress(downloadSize * 100/contentLength);
                    }

                    inputStream.close();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            // 也是在主线程中 ，执行结果 处理
            //mDownloadButton.setText(result? getString(R.string.download_finish) : getString(R.string.download_finish));
            //mResultTextView.setText(result? getString(R.string.download_finish) + mFilePath: getString(R.string.download_finish));

        }



        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // 收到进度，然后处理： 也是在UI线程中。
            if (values != null && values.length > 0) {
                pb_download.setProgress(0);;
            }
        }

    }


}
