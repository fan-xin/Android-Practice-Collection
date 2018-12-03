package com.fanxin.android.socketdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fanxin.android.socketdemo.biz.UdpClientBiz;

public class MainActivity extends AppCompatActivity {

    private EditText mEtMsg;
    private Button mBtnSend;
    private TextView mTvContent;

    //初始化业务类
    private UdpClientBiz udpClientBiz = new UdpClientBiz();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入的内容是
                String msg = mEtMsg.getText().toString();
                //判断输入信息是否为空
                if (TextUtils.isEmpty(msg)){
                    return;

                }

                //加入自己的message
                appendMsgToContent("client: "+msg);


                udpClientBiz.sendMsg(msg, new UdpClientBiz.OnMsgReturnedListener() {
                    @Override
                    public void onMsgReturned(String msg) {
                        //如果有返回的信息，则把信息加入到textview中
                        appendMsgToContent("server: "+msg);

                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();

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
