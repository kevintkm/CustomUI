package com.example.lenovo.timescroller.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.example.lenovo.timescroller.R;

/**
 * Created by kevin.tian on 2016/7/22.
 * Paint,Canvas使用方法
 * 渲染类Shader和SweepGradient
 * canvas.save和canvas.restore
 * 由Paint引申的PathEffect、PorterDuffXfermode，已经Matrix等类要有个基本的概念
 * 图层绘制的一些概念
 * 脏矩形技术
 */
public class AliArc extends FrameLayout {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final int[] mColors = new int[]{0xffff0000, 0xffffff00, 0xff00ff00,
            0xff00ffff, 0xff0000ff, 0xffff00ff};// 渐变色环颜色
    private Shader mShader = new SweepGradient(0, 0, mColors, null);

    private int CENTERX;
    private int width;
    private int height;
    Bitmap bitmapLocation;
    int bitmapWidth;
    int bitmapHeight;
    private float totalRotateAngle = 0f;
    private float currentRotateAngle = 0f;
    /**
     * 单位时间内旋转角度
     */
    private float rotateAngle = 0f;

    private boolean isSetReferValue = false;


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

    public static interface RotateListener {
        public void rotate(float sweepAngle, float value);
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
        height = (int) ((Math.tan(Math.PI / 6) + 1) * width / 2);
        CENTERX = width / 2;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float r = CENTERX - mPaint.getStrokeWidth() * 0.5f;
        canvas.save();
        canvas.translate(CENTERX, CENTERX);
        canvas.rotate(150);
        canvas.drawOval(new RectF(-r, -r, r, r), mPaint);
        canvas.restore();

        canvas.save();
        Paint innerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerPaint.setStyle(Paint.Style.STROKE);
        innerPaint.setStrokeWidth(4);
        innerPaint.setColor(Color.GRAY);
        PathEffect effect = new DashPathEffect(new float[]{5, 5, 5}, 0);
        Paint vitualPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        vitualPaint.setStyle(Paint.Style.STROKE);
        vitualPaint.setStrokeWidth(4);
        vitualPaint.setColor(Color.GREEN);
        vitualPaint.setPathEffect(effect);
        canvas.translate(CENTERX, CENTERX);
        canvas.drawCircle(0, 0, r * 5 / 8, vitualPaint);
        canvas.drawCircle(0, 0, r * 3 / 4, innerPaint);
        canvas.restore();


        Paint bigPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bigPaint.setColor(Color.WHITE);
        bigPaint.setStrokeWidth(4);
        Paint smallPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        smallPaint.setColor(Color.WHITE);
        smallPaint.setStrokeWidth(2);
        //每次画最右边直线，通过旋转实现分割
        int startX = (int) (width - mPaint.getStrokeWidth());
        for (int i = 0; i < 60; i++) {
            canvas.save();
            canvas.rotate(-(-30 + 4 * i), CENTERX, CENTERX);
            if (i % 10 == 0) {
                canvas.drawLine(startX, CENTERX, width, CENTERX, bigPaint);
            } else {
                canvas.drawLine(startX, CENTERX, width, CENTERX, smallPaint);
            }
            canvas.restore();
        }

        //Path使用实现圆环
        Path path = new Path();
        Paint oval = new Paint(Paint.ANTI_ALIAS_FLAG);
        oval.setColor(Color.WHITE);
        path.moveTo(CENTERX, CENTERX);
        path.lineTo(0, height);
        path.lineTo(width, height);
        path.lineTo(CENTERX, CENTERX);
        path.close();
        canvas.drawPath(path, oval);

        String[] text = {"950", "极好", "700", "优秀", "650", "良好", "600", "中等", "550", "较差", "350", "很差", "150"};

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(18);
        for (int i = 0; i <= 12; i++) {
            canvas.save();
            canvas.rotate(-(-120 + 20 * i), CENTERX, CENTERX);
            canvas.drawText(text[i], CENTERX - 20, CENTERX * 3 / 16, textPaint);
            canvas.restore();
        }

        Paint paintMiddleArc = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint paintBitmap = new Paint(Paint.ANTI_ALIAS_FLAG);

        paintMiddleArc.setColor(0xff00d4af);
        paintMiddleArc.setStrokeWidth(6);
        paintMiddleArc.setStyle(Paint.Style.STROKE);

        bitmapLocation = BitmapFactory.decodeResource(getResources(), R.drawable.location1_03);
        bitmapWidth = bitmapLocation.getWidth();
        bitmapHeight = bitmapLocation.getHeight();

        if (isSetReferValue) {
            float r1 = r * 3 / 4;
            canvas.save();
            canvas.translate(CENTERX, CENTERX);
            canvas.drawArc(new RectF(-r1, -r1, r1, r1), -210, currentRotateAngle, false, paintMiddleArc);
            canvas.rotate(-30 + currentRotateAngle);
            Matrix matrix = new Matrix();
            matrix.preTranslate(-r1 - bitmapWidth * 3/ 8, -bitmapHeight / 2);
            canvas.drawBitmap(bitmapLocation, matrix, paintBitmap);
            canvas.restore();
        }

    }

    public void setValue(int referValue, final RotateListener rotateListener) {
        isSetReferValue = !isSetReferValue;
        if (referValue <= 150) {
            totalRotateAngle = 0f;
        } else if (referValue <= 550) {
            totalRotateAngle = (referValue - 150) * 80 / 400f;
        } else if (referValue <= 700) {
            totalRotateAngle = (referValue - 550) * 120 / 150f + 80;
        } else if (referValue <= 950) {
            totalRotateAngle = (referValue - 700) * 40 / 250f + 200;
        } else {
            totalRotateAngle = 210f;
        }

        rotateAngle = totalRotateAngle / 60;
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean rotating = true;
                float value = 350;
                while (rotating) {
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    currentRotateAngle += rotateAngle;
                    if (currentRotateAngle >= totalRotateAngle) {
                        currentRotateAngle = totalRotateAngle;
                        rotating = false;
                    }
                    if (null != rotateListener) {
                        if (currentRotateAngle <= 80) {
                            value = 350 + (currentRotateAngle / 80) * 400;
                        } else if (currentRotateAngle <= 200) {
                            value = 550 + ((currentRotateAngle - 80) / 120) * 150;
                        } else {
                            value = 700 + ((currentRotateAngle - 200) / 40) * 250;
                        }
                        rotateListener.rotate(currentRotateAngle, value);
                    }
                    postInvalidate();
                }
            }
        }).start();
    }

}
