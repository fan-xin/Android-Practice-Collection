package com.example.vediodemo;


import android.Manifest;
import android.app.ActionBar;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String[] title = new String[]{"使用Intent","使用VideoView","使用MediaPlayer"};
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0x110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去除状态栏,要放在加载布局文件之前
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        ActionBar actionBar = getActionBar();
//        actionBar.hide();

        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.id_listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,R.layout.title_item,title);

//        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.item,title_values);



        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        checkPermissionAndPlayVideo();
                        Toast.makeText(MainActivity.this,String.valueOf(position),Toast.LENGTH_SHORT).show();
                        break;
                    case 1:

                        VideoViewActivity.start(MainActivity.this);

                        Toast.makeText(MainActivity.this,String.valueOf(position),Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        MediaPlayerActivity.start(MainActivity.this);

                        Toast.makeText(MainActivity.this,String.valueOf(position),Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }

    private void checkPermissionAndPlayVideo(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {



                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.

        }else {
            playVideoUseIntent();
        }
    }

    //处理权限请求响应
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    //授权失败

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private void playVideoUseIntent(){
        File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(),"/123.mp4");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断sdk的版本
        if (Build.VERSION.SDK_INT >= 24){

            Uri contentUri = FileProvider.getUriForFile(this, "com.example.vediodemo.fileprovider", file);

            intent.setDataAndType(contentUri,"video/*");
            grantUriPermission(getPackageName(),contentUri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            intent.setClipData(ClipData.newRawUri(MediaStore.EXTRA_OUTPUT,contentUri));


        }else {

            intent.setDataAndType(Uri.fromFile(file),"video/*");
        }

        startActivity(intent);
    }



}
