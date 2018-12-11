package com.example.dataresolverdemo;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.sql.BatchUpdateException;

public class MainActivity extends AppCompatActivity {

    //ContentResolver类
    ContentResolver resolver;

    private EditText nameEdit,ageEdit;
    private EditText idEdit;

    private ListView listView;

    private Button btn4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取ContentResolver对象
        resolver = getContentResolver();
//操作其他应用程序数据的方法
        //在程序提供方，都有同名和同参数的方法
//        resolver.query();
//        resolver.insert();
//        resolver.update();
//        resolver.delete():

        //resolver.query();

        listView = (ListView)findViewById(R.id.stu_list);


        Button btn = (Button)findViewById(R.id.insert_btn);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();

                values.put("name","aaa");
                values.put("age",23);
                values.put("gender","bbb");

                Uri uri = resolver.insert(Uri.parse("content://com.fan.myprovider"),values);
                long id = ContentUris.parseId(uri);
                Toast.makeText(MainActivity.this, "add !!!!"+id,Toast.LENGTH_SHORT).show();

            }
        });

        Button btn2 = (Button)findViewById(R.id.query_btn);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = resolver.query(Uri.parse("content://com.fan.myprovider"),null,null,null,null);
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this,R.layout.item,c,
                        new String[]{"_id","name","age","gender"},new int[]{R.id.id_txt,R.id.name_txt,R.id.age_txt,R.id.gender_txt},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                listView.setAdapter(adapter);
            }
        });


        Button btn3 = (Button)findViewById(R.id.delete_btn);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int result = resolver.delete(Uri.parse("content://com.fan.myprovider"),"_id=?",new String[]{"10"});
                if (result>0){
                    Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"fail",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn4= (Button)findViewById(R.id.update_btn);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues values2 = new ContentValues();

                values2.put("name","bbb");
                values2.put("age",55);
                values2.put("gender","qwer");


                int result = resolver.update(Uri.parse("content://com.fan.myprovider"),values2,"_id=?",new String[]{"5"});

                if(result > 0){
                    Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"fail",Toast.LENGTH_SHORT).show();
                }



            }
        });

        Button btn5 = (Button)findViewById(R.id.urimatcher_btn);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resolver.delete(Uri.parse("content://com.fan.myprovider/hello"),null,null);
                resolver.delete(Uri.parse("content://com.fan.myprovider/hello2"),null,null);
                resolver.delete(Uri.parse("content://com.fan.myprovider/hello3"),null,null);
                resolver.delete(Uri.parse("content://com.fan.myprovider/hello4"),null,null);
                resolver.delete(Uri.parse("content://com.fan.myprovider/hello5"),null,null);

            }
        });

        Button btn6 = (Button)findViewById(R.id.urimatcher_btn);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = resolver.insert(Uri.parse("content://com.fan.myprovider/whatever?name=张三&age=99&gender=男"),new ContentValues());

                long id2 = ContentUris.parseId(uri);
                Toast.makeText(MainActivity.this, "成功调用"+id2,Toast.LENGTH_SHORT).show();

            }
        });



    }





    //    public void operate(View v){
//        switch (v.getId()){
//            case R.id.insert_btn:
//                //参数1 URI对象 Uniform Resource Identifier
//                //content://authorities/path
//                ContentValues values = new ContentValues();
//                resolver.insert(Uri.parse("content:/com.fan.myprovider"),values);
//                break;
//
//
//
//        }
//
//    }
}
