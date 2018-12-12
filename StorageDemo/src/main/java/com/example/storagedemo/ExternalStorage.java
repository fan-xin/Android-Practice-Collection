package com.example.storagedemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExternalStorage extends AppCompatActivity {

    EditText infoEdt;
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);

        infoEdt = (EditText)findViewById(R.id.text_ext);
        txtView = (TextView)findViewById(R.id.txt);


        //申请权限
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permission != PackageManager.PERMISSION_GRANTED){
            //在之前获取权限不成功的情况下，动态的申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            //******
        }
    }

    public void  operate(View view){

        if(Environment.getExternalStorageState().equals("mounted")){
            //获取文件的位置
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/fan.txt";
            Log.e("TAG",path);
            //输出/storage/emulated/0/fan.txt
            Toast.makeText(ExternalStorage.this, path,Toast.LENGTH_SHORT).show();

            switch (view.getId()){
                case R.id.btn_ext_save:
                    File f = new File(path);
                    try {
                        if(!f.exists()){
                            f.createNewFile();
                        }

                        FileOutputStream fos = new FileOutputStream(path);
                        //获取输入框的内容
                        String str = infoEdt.getText().toString();
                        fos.write(str.getBytes());
                        Toast.makeText(ExternalStorage.this, "write",Toast.LENGTH_SHORT).show();



                    }catch (IOException ioe){
                        ioe.printStackTrace();
                    }

                    break;
                case R.id.btn_ext_read:
                    try{
                        FileInputStream fis = new FileInputStream(path);
                        byte[] b = new byte[1024];
                        int len = fis.read(b);
                        String str2 = new String(b, 0,len);
                        txtView.setText(str2);


                    }catch (FileNotFoundException e){
                        e.printStackTrace();

                    }catch (IOException e){
                        e.printStackTrace();
                    }



                    break;
            }

        }

    }

}
