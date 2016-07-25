package com.example.lenovo.timescroller.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kevin.tian on 2016/7/22.
 * Paint,Canvas使用方法
 * 渲染类Shader和SweepGradient
 * canvas.save和canvas.restore
 * 由Paint引申的PathEffect、PorterDuffXfermode，已经Matrix等类要有个基本的概念
 * 图层绘制的一些概念
 * 脏矩形技术
 */
public class AliArc extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final int[] mColors = new int[]{0xffff0000, 0xffffff00, 0xff00ff00,
            0xff00ffff, 0xff0000ff, 0xffff00ff};// 渐变色环颜色
    private Shader mShader = new SweepGradient(0, 0, mColors, null);

    private int CENTERX;
    private int width;
    private int height;


    public AliArc(Context context) {
        this(context, null);
    }

    public AliArc(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AliArc(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setShader(mShader);
        //mPaint.setColor(0xffff0000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(40);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height =  (int) ( ( Math.tan(Math.PI / 6) + 1 ) * width / 2 ) ;
        CENTERX = width / 2 ;
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float r = CENTERX-mPaint.getStrokeWidth()*0.5f;
        canvas.save();
        canvas.translate(CENTERX, CENTERX);
        canvas.rotate(150);
        canvas.drawOval(new RectF(-r, -r, r, r), mPaint);
         canvas.restore();
    }
}
