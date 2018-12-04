package com.example.listviewdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listView = (ListView) findViewById(R.id.id_listview_chat);


        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(1,2,"aaa","8:20","你好",true));
        chatMessages.add(new ChatMessage(2,1,"bbb","8:21","不好",false));
        chatMessages.add(new ChatMessage(1,2,"aaa","8:22","给大爷笑一个",true));
        chatMessages.add(new ChatMessage(2,1,"bbb","8:23","小女子只卖身不卖艺",false));
        chatMessages.add(new ChatMessage(1,2,"aaa","8:20","你好",true));
        chatMessages.add(new ChatMessage(2,1,"bbb","8:21","不好",false));
        chatMessages.add(new ChatMessage(1,2,"aaa","8:22","给大爷笑一个",true));
        chatMessages.add(new ChatMessage(2,1,"bbb","8:23","小女子只卖身不卖艺",false));
        chatMessages.add(new ChatMessage(1,2,"aaa","8:20","你好",true));
        chatMessages.add(new ChatMessage(2,1,"bbb","8:21","不好",false));
        chatMessages.add(new ChatMessage(1,2,"aaa","8:22","给大爷笑一个",true));
        chatMessages.add(new ChatMessage(2,1,"bbb","8:23","小女子只卖身不卖艺",false));
        chatMessages.add(new ChatMessage(1,2,"aaa","8:20","你好",true));
        chatMessages.add(new ChatMessage(2,1,"bbb","8:21","不好",false));
        chatMessages.add(new ChatMessage(1,2,"aaa","8:22","给大爷笑一个",true));
        chatMessages.add(new ChatMessage(2,1,"bbb","8:23","小女子只卖身不卖艺",false));
        chatMessages.add(new ChatMessage(1,2,"aaa","8:20","你好",true));
        chatMessages.add(new ChatMessage(2,1,"bbb","8:21","不好",false));
        chatMessages.add(new ChatMessage(1,2,"aaa","8:22","给大爷笑一个",true));
        chatMessages.add(new ChatMessage(2,1,"bbb","8:23","小女子只卖身不卖艺",false));



        listView.setAdapter(new ChatMessageAdapter(chatMessages,this));

    }
    public static class ChatMessageAdapter extends BaseAdapter{
        ArrayList<ChatMessage> mChatMessages = new ArrayList<>();

        private Context mContext;


        //定义两种数据类型
        interface IMessageViewType{
            int COM_MESSAGE = 0;
            int TO_MESSAGE = 1;
        }


        //构造器接收上下文和数据列表
        public ChatMessageAdapter(ArrayList<ChatMessage> mChatMessages, Context mContext) {
            this.mChatMessages = mChatMessages;
            this.mContext = mContext;
        }

        public ChatMessageAdapter() {

        }

        @Override
        public int getCount() {
            return mChatMessages != null? mChatMessages.size():0;
        }

        @Override
        public Object getItem(int position) {
            return mChatMessages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ChatMessage entity = mChatMessages.get(position);
            boolean isComMsg = entity.ismIsComeMessage();

            ViewHolder viewHolder = new ViewHolder();

            ChatMessage chatMessage = mChatMessages.get(position);

            if (convertView == null){
                //如果是空，则创建视图

                //根据消息的类型，来区别视图
                if (isComMsg){
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.chatting_item_msg_text_left,null);

                }else {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.chatting_item_msg_text_right,null);

                }
                //根据视图，拿到控件


                viewHolder.mSendTime = convertView.findViewById(R.id.id_tv_send_time);
                viewHolder.mContent = convertView.findViewById(R.id.id_tv_content);

                convertView.setTag(viewHolder);


            }else {
                viewHolder = (ViewHolder) convertView.getTag();

            }
            viewHolder.mSendTime.setText(chatMessage.getmDate());
            viewHolder.mContent.setText(chatMessage.getmContent());

            return convertView;
        }



        class ViewHolder{
            public TextView mSendTime;
            public TextView mUsername;
            public TextView mContent;
            public TextView mTime;
            public ImageView mUserAvatar;
            public boolean mIsComMessage = true;
        }



        //根据不同的类型，获取视图

        @Override
        public int getItemViewType(int position) {

            ChatMessage chatMessage = mChatMessages.get(position);
            //根据当前消息的类型，返回不同的类型
            return chatMessage.ismIsComeMessage()? IMessageViewType.COM_MESSAGE:IMessageViewType.TO_MESSAGE;
        }

        //获取视图的类型的个数
        @Override
        public int getViewTypeCount() {
            return 2;
        }
    }



    /*
    * 消息对象，将要填充的内容封装到这个对象中
    * */
    public class ChatMessage{
        private int mID;
        private int mFriendID;
        private String mName;
        private String mDate;
        private String mContent;

        private boolean mIsComeMessage;

        //构造器
        public ChatMessage(int mID, int mFriendID, String mName, String mDate, String mContent, boolean mIsComeMessage) {
            this.mID = mID;
            this.mFriendID = mFriendID;
            this.mName = mName;
            this.mDate = mDate;
            this.mContent = mContent;
            this.mIsComeMessage = mIsComeMessage;
        }

        public int getmID() {
            return mID;
        }

        public void setmID(int mID) {
            this.mID = mID;
        }

        public int getmFriendID() {
            return mFriendID;
        }

        public void setmFriendID(int mFriendID) {
            this.mFriendID = mFriendID;
        }

        public String getmName() {
            return mName;
        }

        public void setmName(String mName) {
            this.mName = mName;
        }

        public String getmDate() {
            return mDate;
        }

        public void setmDate(String mDate) {
            this.mDate = mDate;
        }

        public String getmContent() {
            return mContent;
        }

        public void setmContent(String mContent) {
            this.mContent = mContent;
        }

        public boolean ismIsComeMessage() {
            return mIsComeMessage;
        }

        public void setmIsComeMessage(boolean mIsComeMessage) {
            this.mIsComeMessage = mIsComeMessage;
        }
    }
}
