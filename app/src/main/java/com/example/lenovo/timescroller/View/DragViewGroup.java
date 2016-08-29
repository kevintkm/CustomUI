package com.example.lenovo.timescroller.View;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * 自定义拖拽View
 * Created by kevin.tian on 2016/8/29.
 */
public class DragViewGroup extends TextView {

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
                mLastX = event.getX();
                mLastY = event.getY();
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

    private void init() {
        setText("拖拽View");
    }


}
