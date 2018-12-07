package com.example.audiodemo;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {

    private ListView musicView;
    //存放音乐曲目的名字
    private ArrayList<String> nameList = new ArrayList<>();

    public static ArrayList<String> pathList = new ArrayList<>();

    private int index = 0; //当前播放的曲目

    private Button playpause;

    public static ProgressBar musicPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        musicView = (ListView)findViewById(R.id.id_listview);
        //网易云

        getMusic("/mymusic/");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,nameList);
        musicView.setAdapter(arrayAdapter);

        playpause = (Button)findViewById(R.id.id_btn_play);

        musicPro = (ProgressBar)findViewById(R.id.id_music_progress_bar);



        //启动服务，并且通知服务接下要要播放歌曲的路径
        //点击歌名，进行播放
        musicView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(MusicActivity.this, MusicService.class);
                it.putExtra("index",position);
                it.putExtra("tag","play");
                startService(it);
                index = position;//保存当前播放曲目的位置

                //点击列表，播放歌曲以后，按钮要变为暂停
                playpause.setText("暂停");
            }
        });



    }

    public void getMusic(String path){
        //path是音乐文件的存放目录
        path = Environment.getExternalStorageDirectory().getAbsolutePath()+path;
        Toast.makeText(this,path,Toast.LENGTH_SHORT).show();
        File f = new File(path);
        //获取该文件夹下单 所有文件集合
        File[] fs = f.listFiles();
        for (int i = 0; i < fs.length; i++) {
            String name = fs[i].getName();
            String suffix = name.substring(name.length()-3 , name.length());
            if (suffix.equalsIgnoreCase("mp3")){
                //将歌曲名称加入列表
                nameList.add(name);
                pathList.add(fs[i].getAbsolutePath());
            }
        }

    }

    public void control(View v){
        switch (v.getId()){
            case R.id.id_btn_play:
                //播放暂停按钮
                Intent it = new Intent(MusicActivity.this, MusicService.class);


                if (playpause.getText().equals("播放")){
                    //播放
                    it.putExtra("tag","start");
                    playpause.setText("暂停");

                }else {
                    //暂停
                    it.putExtra("tag","pause");
                    playpause.setText("播放");

                }
                startService(it);


                break;

            case R.id.id_btn_pause:
                //停止就是直接停止服务
                Intent it2 = new Intent(MusicActivity.this, MusicService.class);
                stopService(it2);
                playpause.setText("播放");
                break;

            case R.id.id_btn_last:
                if (index == 0){
                    index = pathList.size()-1;
                }else {
                    index--;
                }

                Intent it3 = new Intent(MusicActivity.this, MusicService.class);
                it3.putExtra("index",index);
                it3.putExtra("tag","play");
                startService(it3);
                playpause.setText("暂停");
                break;
            case R.id.id_btn_next:
                if (index == pathList.size()-1){
                    index = 0;
                }else {
                    index++;

                }

                Intent it4 = new Intent(MusicActivity.this, MusicService.class);
                it4.putExtra("index",index);
                it4.putExtra("tag","play");
                startService(it4);
                playpause.setText("暂停");
                break;
        }
    }
}
