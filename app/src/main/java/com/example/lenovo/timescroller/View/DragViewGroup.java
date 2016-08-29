package com.example.lenovo.timescroller.View;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 自定义拖拽View
 * Created by kevin.tian on 2016/8/29.
 */
public class DragViewGroup extends LinearLayout {

    /**
     * 多点触控导致第二个手指触摸是，View会偏移位置
     */

    private float mLastX;
    private float mLastY;

    public DragViewGroup(Context context) {
        this(context, null);
    }

    public DragViewGroup(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public DragViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
//                mLastX = event.getX();
//                mLastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float currentX = event.getX();
                float currentY = event.getY();
                float dx = currentX - mLastX;
                float dy = currentY - mLastY;
                layout((int) (getLeft() + dx), (int) (getTop() + dy), (int) (getRight() + dx), (int) (getBottom() + dy));
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;

        }

        return true;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        //为了使手指按在Button等可点击的控件上任可以滑动，需要拦截滑动实践
        //并且为了使坐标准确，在此处记录按下的点
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getX();
                mLastY= event.getY();

                return false;
        }
        return false;
    }
    private void init() {
    }


}
