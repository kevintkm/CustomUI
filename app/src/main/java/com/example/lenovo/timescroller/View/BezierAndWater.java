package com.example.lenovo.timescroller.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kevin.tian on 2016/9/23.
 */

public class BezierAndWater extends View {
    private int viewHeight, viewWidth;
    private int pointX, pointY;
    private int waterHeight;
    private Paint mPaint, paint;
    private Path mPath;
    private boolean isInc;

    public BezierAndWater(Context context) {
        this(context, null);
    }

    public BezierAndWater(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#AFDEE4"));
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        viewWidth = w ;
        pointY = (int) (7/8f*viewHeight);
        waterHeight = (int) (15/16f*viewHeight);
    }

    /**
     * 测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, 300);
        } else if (wSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, hSpecSize);
        } else if (hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(wSpecSize, 300);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 起始点位置 
        mPath.moveTo(-1 / 4F * viewWidth, waterHeight);
        //绘制水波浪
        mPath.quadTo(pointX, pointY, viewWidth + 1 / 4F * viewWidth, waterHeight);
        //绘制波浪下方闭合区域
        mPath.lineTo(viewWidth + 1 / 4F * viewWidth, viewHeight);
        mPath.lineTo(-1 / 4F * viewWidth, viewHeight);
        mPath.close();
        //绘制路径
        canvas.drawPath(mPath, mPaint);
        //绘制红色水位高度辅助线
        canvas.drawLine(0,waterHeight,viewWidth,waterHeight,paint);
        //产生波浪左右涌动的感觉
        if (pointX >= viewWidth + 1 / 4F * viewWidth) {//控制点坐标大于等于终点坐标改标识
            isInc = false;
        } else if (pointX <= -1 / 4F * viewWidth) {//控制点坐标小于等于起点坐标改标识
            isInc = true;
        }
        pointX = isInc ? pointX + 20 : pointX - 20;
        //水位不断加高  当距离控件顶端还有1/8的高度时，不再上升
        if (pointY >= 1 / 8f * viewHeight) {
            pointY -= 2;
            waterHeight -= 2;
        }
        //路径重置
        mPath.reset();
        // 重绘
        invalidate();
    }

}
