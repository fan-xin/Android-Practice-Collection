package com.example.storagedemo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ShareRefeActivity extends AppCompatActivity {

    private EditText usernameEdt, passwordEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_refe);

        usernameEdt = findViewById(R.id.username_edit);
        passwordEdt = findViewById(R.id.pwd_edit);


        //读取数据
        //获取
        SharedPreferences share2 = getSharedPreferences("share",MODE_PRIVATE);

        //根据key获取内容
        //参数1 key
        //参数2 如果对应的key不存在的话，返回参数2的内容
        String name = share2.getString("username","");
        String pwd = share2.getString("password","");

        usernameEdt.setText(name);
        passwordEdt.setText(pwd);



        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框的内容
                String username = usernameEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                //验证
                if (username.equals("admin") && password.equals("123")){
                    //存储信息
                    //获取SharePreference对象
                    //参数一 文件名 参数二 模式
                    SharedPreferences share = getSharedPreferences("share",MODE_PRIVATE);

                    //获取Editor对象
                    SharedPreferences.Editor editor = share.edit();

                    //存储信息

                    editor.putString("username",username);
                    editor.putString("password",password);

                    //执行提交操作
                    editor.commit();

                    Toast.makeText(ShareRefeActivity.this, "successful",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(ShareRefeActivity.this, "fail",Toast.LENGTH_SHORT).show();
                }



                    //验证失败

            }
        });


    }
}
