package com.example.asynctaskdemo;


/*
* １. download方法　接收参数 url， localpath， listener
* 2. listener
* 3. 用AsyncTask封装主要功能
*
* */

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadHelper {

    public static void download(String url,String localPath, OnDownloadListener listener){

        //调用AsyncTask
        DownloadAsyncTask task = new DownloadAsyncTask(url,localPath,listener);
        task.execute();
    }

    /*
     * String 入参
     * Integer　进度
     * Boolean　返回值
     *
     * **/
    public static class DownloadAsyncTask extends AsyncTask<String,Integer,Boolean> {

        String mURL;
        String mFilePath;
        OnDownloadListener mListener;

        //构造方法
        public DownloadAsyncTask(String mURL, String mFilePath, OnDownloadListener mListener) {
            this.mURL = mURL;
            this.mFilePath = mFilePath;
            this.mListener = mListener;
        }

        /*
         * 在另外一个线程中处理事件
         *
         * */
        @Override
        protected Boolean doInBackground(String... params) {
            for (int i = 0; i < 10000; i++) {
                Log.i("hello","doInBackground: "+params[0]);
                //抛出进度
                publishProgress();
            }

            //下载过程
            /*
             * 入参是下载的地址
             *
             * **/
//            if (params!= null && params.length >0){
//                String url = params[0];
            String apkUrl = mURL;


                try {
                    //构造URL
                    URL mUrl = new URL(apkUrl);
                    //构造链接并打开
                    URLConnection urlConnection = mUrl.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    //获取下载内容的总长度
                    int contentLength = urlConnection.getContentLength();


                    //下载地址准备
                    //获取的地址永远是正确的
                    String mFilePath = Environment.getExternalStorageDirectory()
                            + File.separator+ "fanxinshr"+File.separator;

                    //Log.d(TAG,"path is "+mFilePath);



                    //对下载地址进行处理
                    File apkFile = new File(mFilePath);
                    if (apkFile.exists()){
                        boolean result = apkFile.delete();
                        if(!result){
                            if (mListener != null){
                                mListener.onFail(-1,apkFile,"文件删除失败");
                            }
                            return false;
                        }
                    }

                    //已下载的大小
                    int downloadSize = 0;

                    //byte数组
                    byte[] bytes = new byte[1024];

                    int length;

                    //创建一个输出管道
                    OutputStream outputStream = new FileOutputStream(mFilePath);

                    //不断的读取数据，直到读完为止
                    while ((length = inputStream.read(bytes)) != -1){
                        //将读取到的数据放到文件管道里
                        outputStream.write(bytes,0,length);
                        downloadSize +=length;
                        //发送进度
                        publishProgress(downloadSize*100/contentLength);
                    }

                    inputStream.close();
                    outputStream.close();



                } catch (IOException e) {
                    e.printStackTrace();
                    if (mListener != null){
                        mListener.onFail(-2,new File(mFilePath),e.getMessage());
                    }
//                }


//            }else{
                return false;
            }

            if(mListener != null){
                    mListener.onSuccess(0, new File(mFilePath));
            }


            return true;
        }


        protected void onPostExecute(Boolean result){
            super.onPostExecute(result);
            //在主线程中，执行结果
//            btn_download.setText(aBoolean?"下载完成":"下载失败");
//            tv_download_status.setText(aBoolean?"下载完成":"下载失败");

            if (mListener != null){
                if (result){
                    mListener.onSuccess(0,new File(mFilePath));
                }else {
                    mListener.onFail(-1,new File(mFilePath),"下载失败");
                }
            }

        }

        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //收到抛出的进度，然后处理，也是在UI线程
//            pb_download.setProgress(values[0]);

            if(values != null && values.length >0 ){
                if (mListener != null ){
                    mListener.onProgress(values[0]);

                }
            }


        }

        protected void onCancelled(){
            super.onCancelled();

        }


        /*
         * 在异步任务之前，在主线程中
         *
         * **/

        protected void onPreExecute(){
            super.onPreExecute();
            //可操作UI
            if (mListener != null){
                //如果监听器不为空，则启动回调
                mListener.onStrat();
            }


//            btn_download.setText("下载中");
//            tv_download_status.setText("下载中");
//            pb_download.setProgress(INIT_PROGESS);


        }






    }

    public interface OnDownloadListener{
        void onStrat();

        //Success成功以后回调，传出文件
        void onSuccess(int code, File file);

        //
        void onFail(int code, File file, String message);

        //回调progress
        void onProgress(int progress);

        //使用抽象类，可以不用实现每一个方法
        abstract class SimpleDownloadListener implements OnDownloadListener {
            public void onStrat(){

            }

            public void onProgress(int progress) {

            }
        }
    }


}
