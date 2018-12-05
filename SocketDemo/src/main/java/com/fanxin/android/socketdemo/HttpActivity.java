package com.fanxin.android.socketdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fanxin.android.socketdemo.biz.UdpClientBiz;
import com.fanxin.android.socketdemo.https.HttpUtils;

import org.w3c.dom.Text;

public class HttpActivity extends AppCompatActivity {

    private EditText mEtMsg;
    private Button mBtnSend;
    private TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URLstr = mEtMsg.getText().toString();
                if (TextUtils.isEmpty(URLstr)){
                    return;
                }

                HttpUtils.doGet(URLstr, new HttpUtils.HttpListener() {
                    @Override
                    public void onSuccess(String content) {
                        mTvContent.setText(content);

                    }

                    @Override
                    public void onFail(Exception ex) {
                        ex.printStackTrace();

                    }
                });

            }
        });

    }

    private void appendMsgToContent(String msg){
        mTvContent.append(msg+"\n");
    }

    private void initView() {

        mEtMsg = (EditText)findViewById(R.id.id_edittext);
        mBtnSend = (Button)findViewById(R.id.id_send_button);
        mTvContent = (TextView)findViewById(R.id.id_tv_content);
    }
}
