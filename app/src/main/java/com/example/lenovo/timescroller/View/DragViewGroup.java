package com.example.lenovo.timescroller.View;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.lenovo.timescroller.Util.Util;

import java.util.ArrayList;

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
    private Scroller mScroller;

    // The ‘active pointer’ is the one currently moving our object.
//    private int INVALID_POINTER_ID = -1000;
//    private int mActivePointerId = INVALID_POINTER_ID;


    public DragViewGroup(Context context) {
        this(context, null);
    }

    public DragViewGroup(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public DragViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
        init();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 通过遍历RootView树得到当前点击范围内的子view
     *
     * @param event
     * @return
     */

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        //为了使手指按在Button等可点击的控件上任可以滑动，需要拦截滑动实践
        //并且为了使坐标准确，在此处记录按下的点
        switch (action) {
            case MotionEvent.ACTION_MOVE:
//                final int pointer =
//                        MotionEventCompat.findPointerIndex(event, mActivePointerId);

                final float x1 = event.getRawX();
                final float y1 = event.getRawY();
                float currentX = x1;
                float currentY = y1;
                float dx = currentX - mLastX;
                float dy = currentY - mLastY;
                View child = getTouchTarget(this, (int) currentX, (int) currentY);
                scrollTo((int) -dx, 0);
                break;
            case MotionEvent.ACTION_DOWN:

            /*    final int pointerIndex = MotionEventCompat.getActionIndex(event);
                mActivePointerId=MotionEventCompat.getPointerId(event,pointerIndex);*/

//                final float x = MotionEventCompat.getX(event, pointerIndex);
//                final float y = MotionEventCompat.getY(event, pointerIndex);
                float x = event.getRawX();
                float y = event.getRawY();
                Log.d("-----", x + "");
                Log.d("-----", y + "");
                mLastX = x;
                mLastY = y;
                // Save the ID of this pointer (for dragging)
                //  mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private View getTouchTarget(View view, int x, int y) {
        View target = null;
        ArrayList<View> TouchableViews = view.getTouchables();
        for (View child : TouchableViews) {
            if (isTouchPointInView(child, x, y)) {
                target = child;
                break;
            }
        }

        return target;
    }

    private boolean isTouchPointInView(View view, int x, int y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (view.isClickable() && y >= top && y <= bottom
                && x >= left && x <= right) {
            return true;
        }
        return false;
    }

    private void init() {
    }

}
