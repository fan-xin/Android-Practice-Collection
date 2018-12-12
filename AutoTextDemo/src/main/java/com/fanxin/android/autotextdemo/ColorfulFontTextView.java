package com.fanxin.android.autotextdemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 18/12/12  15:07
 */
public class ColorfulFontTextView extends TextView {

    int TextViewWidth;
    private LinearGradient linearGradient;
    private Paint paint;

    public ColorfulFontTextView(Context context) {
        super(context);
    }

    public ColorfulFontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (TextViewWidth == 0){
            TextViewWidth = getMeasuredWidth();
            if (TextViewWidth > 0){
                paint = getPaint();
                linearGradient = new LinearGradient(0,0,TextViewWidth,0,
                        new int[]{Color.BLUE,Color.YELLOW,Color.RED,Color.GREEN,Color.GRAY},null, Shader.TileMode.CLAMP);
                paint.setShader(linearGradient);
            }
        }

    }
}
