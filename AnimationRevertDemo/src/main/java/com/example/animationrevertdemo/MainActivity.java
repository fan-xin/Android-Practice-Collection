package com.example.animationrevertdemo;

import android.animation.Animator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;

import static android.view.ViewAnimationUtils.createCircularReveal;

public class MainActivity extends AppCompatActivity {


    private Button import_btn;
    private View view;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        import_btn = (Button)findViewById(R.id.id_btn_import);
        view = (View)findViewById(R.id.id_view_image);
        checkBox = (CheckBox)findViewById(R.id.checkBox);
        
    }

    public void onClick(View view) {
        //获取checkbot的值
        final boolean playAnimation = checkBox.isChecked();

        switch (view.getId()){
            case R.id.id_btn_import:
                handleChangeVisibility(playAnimation);
                break;
        }

        if(import_btn.getText().equals("导入")){
            import_btn.setText("撤销");
        }else {
            import_btn.setText("导入");
        }



    }

    private void handleChangeVisibility(boolean playAnimation) {
        if (playAnimation){
            if (view.isShown()){
                revertExit();
            }else {
                revertEnter();
            }
            
        }else {
            if (view.isShown()){
                view.setVisibility(View.INVISIBLE);
            }else {
                view.setVisibility(View.VISIBLE);
            }
            
        }


    }

    //退出
    private void revertExit() {
        int w = view.getWidth();
        int h = view.getHeight();

        int cx = w;
        int cy = h;

        int r = (int) Math.hypot(w,h);



        Animator animator = ViewAnimationUtils.createCircularReveal(view,cx,cy,r,0);

        //animator.setDuration(5000);

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                view.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.start();


    }

    //进入
    private void revertEnter() {
        int w = view.getWidth();
        int h = view.getHeight();
        int cx = w ;
        int cy = h;
        int r = (int)Math.hypot(w,h);


        Animator animator = createCircularReveal(view,cx,cy,0,r);


        //animator.setDuration(5000);
        animator.start();
        view.setVisibility(View.VISIBLE);

    }


}
