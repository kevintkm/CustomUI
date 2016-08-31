package com.example.lenovo.timescroller.View;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.Util.Util;

import java.util.ArrayList;
import java.util.HashMap;

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
    int targetInedex;
    int currentIndex;
    int childCount;
    private Scroller mScroller;
    private ImageView currentView;

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
                currentView.setTranslationX(dx);
//                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(currentView, "translationX", dx);
//                objectAnimator.setDuration(0);
//                objectAnimator.start();
                onDrag(dx);
                // scrollTo((int) -dx, 0);
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
                currentView = (ImageView) getTouchTarget(this, (int) x, (int) y);
                mLastX = x;
                mLastY = y;
                // Save the ID of this pointer (for dragging)
                //  mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                break;
            case MotionEvent.ACTION_UP:
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    child.setTranslationX(0);
                }
                removeView(currentView);
                addView(currentView,currentIndex+targetInedex);
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private HashMap<View,ObjectAnimator> viewObjectAnimatorHashMap = new HashMap<>();

    private void onDrag(float dx) {
        currentIndex = indexOfChild(currentView);
        int offset = (int) dx / (currentView.getMeasuredWidth() / 2 + Util.dpToPx(getResources(), 3));
        float ss = -(Util.dpToPx(getResources(), 3) + currentView.getWidth());
        if (offset > 0) {
            targetInedex = (offset + 1) / 2;
            for (int i = 1; i < targetInedex + 1; i++) {

               View child = getChildAt(currentIndex + i);

                child.setTranslationX(ss);
                 /*if(child == currentView){
                    continue;
                }
                float ss = -(Util.dpToPx(getResources(), 3) + currentView.getWidth());
                if(child.getTranslationX() == ss){
                    continue;
                }
//                child.setTranslationX(ss);
                ObjectAnimator objectAnimator = viewObjectAnimatorHashMap.get(child);
                if(objectAnimator==null || !objectAnimator.isRunning()){
                    objectAnimator = ObjectAnimator.ofFloat(child, "translationX", ss);
                    viewObjectAnimatorHashMap.put(child, objectAnimator);
                    objectAnimator.setDuration(100);
                    objectAnimator.start();
                }*/
            }
        }else if (offset<0){
            targetInedex = (offset - 1) / 2;
            for (int i = -1; i > targetInedex -1 ; i--) {
                View child = getChildAt(currentIndex + i);

                child.setTranslationX(-ss);
            }
        }
    }

    private View getTouchTarget(View view, int x, int y) {
        View target = null;
        ArrayList<View> TouchableViews = view.getTouchables();
        childCount = TouchableViews.size();
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
