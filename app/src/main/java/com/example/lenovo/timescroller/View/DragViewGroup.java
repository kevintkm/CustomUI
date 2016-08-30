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
     * 多点触控导致第二个手指触摸是，View会偏移位置(在ACTION_POINTER_UP中重新选择操作点)
     */

    private float mLastX;
    private float mLastY;

    // The ‘active pointer’ is the one currently moving our object.
    private int INVALID_POINTER_ID = -1000;
    private int mActivePointerId = INVALID_POINTER_ID;


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

                final int pointerIndex =
                        MotionEventCompat.findPointerIndex(event, mActivePointerId);

                final float x = MotionEventCompat.getX(event, pointerIndex);
                final float y = MotionEventCompat.getY(event, pointerIndex);

                float currentX = x;
                float currentY = y;
                float dx = currentX - mLastX;
                float dy = currentY - mLastY;
                layout((int) (getLeft() + dx), (int) (getTop() + dy), (int) (getRight() + dx), (int) (getBottom() + dy));
                invalidate();
                break;
            case MotionEvent.ACTION_UP: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {

                int pointerInd = MotionEventCompat.getActionIndex(event);
                final int pointerId = MotionEventCompat.getPointerId(event, pointerInd);

                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerInd == 0 ? 1 : 0;
                    mLastX = MotionEventCompat.getX(event, newPointerIndex);
                    mLastY = MotionEventCompat.getY(event, newPointerIndex);
                    mActivePointerId = MotionEventCompat.getPointerId(event, newPointerIndex);
                }
                break;
            }
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

                final int pointerIndex = MotionEventCompat.getActionIndex(event);
                mActivePointerId=MotionEventCompat.getPointerId(event,pointerIndex);

                final float x = MotionEventCompat.getX(event, pointerIndex);
                final float y = MotionEventCompat.getY(event, pointerIndex);

                mLastX = x;
                mLastY= y;
                // Save the ID of this pointer (for dragging)
                mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                return false;
        }
        return false;
    }
    private void init() {
    }


}
