package com.example.storagedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class InternalStorage extends AppCompatActivity {

    private EditText edt;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_storage);

        //获取控件
        edt = findViewById(R.id.text_int);
        txt = findViewById(R.id.txt_int);
    }

    public void operate(View view){
        //data/data/包名/files
        //getCacheDir /data/data/包名/caches
        switch (view.getId()){
            case R.id.btn_int_save:
                Toast.makeText(InternalStorage.this, "btn_int_save button clicked",Toast.LENGTH_SHORT).show();
                File f = new File(getFilesDir(),"getFile.txt");
                try{
                    if (!f.exists()){
                        f.createNewFile();
                    }
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(edt.getText().toString().getBytes());
                    fos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
                break;
            case R.id.btn_int_read:
                try{
                    FileInputStream fis = new FileInputStream(new File(getFilesDir()+"/getFile.txt"));
                    byte[] b = new byte[1024];
                    int len = fis.read(b);
                    String str2 = new String(b, 0,len);
                    txt.setText(str2);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
                break;
        }
    }
}
