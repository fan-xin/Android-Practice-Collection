package com.example.listviewdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.listviewdemo.R;
import com.example.listviewdemo.model.LessonResult;

import java.util.ArrayList;
import java.util.List;

public class RequestDataAdapter extends BaseAdapter {
    private List<LessonResult.LessonInfo> lessonInfos;;

    //上下文环境，从构造函数中传入
    private Context mcontext;

    public RequestDataAdapter(Context context, List<LessonResult.LessonInfo> infos) {
        lessonInfos = infos;
        mcontext = context;
    }

    //获取元素个数的方法
    @Override
    public int getCount() {
        //如果直接传回lessonInfos.size()会报错，因为lessonInfos可能为空，所以要判断以后再传回去
        return lessonInfos != null? lessonInfos.size():0;
    }

    @Override
    public Object getItem(int position) {
        return lessonInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /*
    * 利用inflater填充子元素的布局文件，然后找到子元素布局文件里的每一个控件
    * 使用viewholder来缓存子元素布局文件里的控件可以提升性能
    *
    * inflater类似于findviewbyId,inflater用来找布局文件
    * findviewbyId 用来找具体xml文件下的控件
    *
    * 参数含义
    *
    * position 表示现在正在绘制view中的第几个item
    *
    * convertView展示在界面上的一个item
    *
    *
    * parent convertView的父控件
    *
    * view相当于一个view控件
    * 把当前activity压缩成一个视图，赋值给convertview
    * 也就是说convertView就是当前的Activity，最后返回的也是convertView
    *
    * 每次显示一个item子元素，都调用一次getView方法，但是每次调用的时候
    * convertView是空的。
    *
    * 当把屏幕填充满之后，convertView就不是空的了，所以可以直接gtTag来填充convertView
    *
    * */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.item_app_list,null);


            viewHolder.imageView = convertView.findViewById(R.id.id_app_icon_image_view);
            viewHolder.textView = convertView.findViewById(R.id.id_app_name_tv);

            convertView.setTag(viewHolder);


        }else {
            //用getTag方法获取绑定的Viewholder对象，避免对控件的层层查询
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(lessonInfos.get(position).getName());

        //使图片不可见
        viewHolder.imageView.setVisibility(View.GONE);


        return convertView;
    }

    //ViewHolder是一个控件集合
    public class ViewHolder{
        public ImageView imageView;
        public TextView textView;
    }
}
