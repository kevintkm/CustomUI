package com.example.lenovo.timescroller.View;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lenovo.timescroller.Activity.MainActivity;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.Util.FloatRoute;
import com.example.lenovo.timescroller.Util.Util;

/**
 * 自定义拖拽View
 * Created by kevin.tian on 2016/8/29.
 */
public class DragViewGroup extends LinearLayout {

    /**
     * 多点触控导致第二个手指触摸是，View会偏移位置(在ACTION_POINTER_UP中重新选择操作点)
     */

    int childCount;
    Rect mRect = new Rect();
    FloatRoute mRoute = new FloatRoute();
    BitmapDrawable drawable;
    private View currentView;
    AnimatorSet wobbleSet;
    boolean isAnimating;
    MoveChildRunnable moveChildRunnable= new MoveChildRunnable();
    /**
     * 是否拦截Touch
     */
    private boolean isInterceptTouch = false;
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
    }


    /**
     * 通过遍历RootView树得到当前点击范围内的子view
     *
     * @param
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mRoute.setDown(ev.getX(), ev.getY());
        }
        return isInterceptTouch;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (drawable == null || getChildCount() < 1)
            return super.dispatchTouchEvent(event);
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        //为了使手指按在Button等可点击的控件上任可以滑动，需要拦截滑动实践
        //并且为了使坐标准确，在此处记录按下的点
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                mRoute.setCurrent(event.getRawX(), event.getRawY());
                actionMove();
                break;
//            case MotionEvent.ACTION_DOWN:
//                mRoute.setDown(event.getRawX(), event.getRawY());
//                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isInterceptTouch = false;
                drawable = null;
                currentView.setVisibility(View.VISIBLE);
                mRoute.reset();
                stopWobbleAnim();
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private void actionMove() {
        float dx = mRoute.getDeltaX();
//        float dy = mRoute.getDeltaY();
        mRect.left += dx;
        mRect.right = mRect.left + currentView.getWidth();
//        mRect.top += dy;
//        mRect.bottom = mRect.top + currentView.getHeight();
        if (mRect.left<0){
            mRect.left = 0;
            mRect.right = currentView.getWidth();
        }

        if (mRect.right>getMeasuredWidth()){
            mRect.right = getMeasuredWidth();
            mRect.left = getMeasuredWidth() - currentView.getWidth();
        }

        invalidate();

        if(isAnimating)
            return ;
        for (int i = 0; i <childCount; i++) {
            final View childView = getChildAt(i);
            if(childView == currentView)
                continue;
            int centerX = mRect.left+currentView.getMeasuredWidth()/2;
            //int centerY = mRect.top+currentView.getMeasuredWidth()/2;
            //int diffOffset = currentView.getMeasuredWidth()/8;
            if (centerX>childView.getLeft() && centerX<childView.getRight()) {
                isAnimating = true;

                removeCallbacks(moveChildRunnable);
                moveChildRunnable.setTargetIndex(i);
                postDelayed(moveChildRunnable, 50);
//            	startMoveChildAnim(i);
                break;
            }
        }

    }
    /**
     * 延迟执行动画Runnable
     *Created by linjinfa 331710168@qq.com
     */
    class MoveChildRunnable implements Runnable{

        private int targetIndex;

        @Override
        public void run() {
            startMoveChildAnim(targetIndex);
        }

        public void setTargetIndex(int targetIndex) {
            this.targetIndex = targetIndex;
        }
    }

    private void startMoveChildAnim(final int targetIndex) {
        int position = indexOfChild(currentView);
        if(position==-1)
            return ;
        isAnimating = true;
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(200);
        if(position<targetIndex){
            for(int i=position+1;i<=targetIndex;i++){
                View preChildView = getChildAt(i-1);
                View nextChildView = getChildAt(i);
                animationSet.addAnimation(new MoveAnim(nextChildView, nextChildView.getLeft(), preChildView.getLeft(), nextChildView.getRight(), preChildView.getRight(), nextChildView.getTop(), preChildView.getTop(), nextChildView.getBottom(), preChildView.getBottom()));
            }
        }else{
            for(int i=targetIndex;i<position;i++){
                View preChildView = getChildAt(i);
                View nextChildView = getChildAt(i+1);
                animationSet.addAnimation(new MoveAnim(preChildView, preChildView.getLeft(), nextChildView.getLeft(), preChildView.getRight(), nextChildView.getRight(), preChildView.getTop(), nextChildView.getTop(), preChildView.getBottom(), nextChildView.getBottom()));
            }
        }
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimating = true;
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                removeView(currentView);
                addView(currentView, targetIndex);
                isAnimating = false;
            }
        });
        startAnimation(animationSet);
    }


    /**
     * 移动动画
     */
    class MoveAnim extends Animation {

        private View targetView;
        private int fromLeft;
        private int toLeft;
        private int fromRight;
        private int toRight;
        private int fromTop;
        private int toTop;
        private int fromBottom;
        private int toBottom;

        public MoveAnim(View targetView, int fromLeft, int toLeft,
                        int fromRight, int toRight, int fromTop, int toTop,
                        int fromBottom, int toBottom) {
            super();
            this.targetView = targetView;
            this.fromLeft = fromLeft;
            this.toLeft = toLeft;
            this.fromRight = fromRight;
            this.toRight = toRight;
            this.fromTop = fromTop;
            this.toTop = toTop;
            this.fromBottom = fromBottom;
            this.toBottom = toBottom;
        }


    @Override
    protected void applyTransformation(float interpolatedTime,
                                       Transformation t) {
        int left = (int) (fromLeft - (fromLeft - toLeft) * interpolatedTime);
        int right = (int) (fromRight - (fromRight - toRight) * interpolatedTime);
        int top = (int) (fromTop - (fromTop - toTop) * interpolatedTime);
        int bottom = (int) (fromBottom - (fromBottom - toBottom) * interpolatedTime);
        targetView.setLeft(left);
        targetView.setRight(right);
        targetView.setTop(top);
        targetView.setBottom(bottom);
    }
}
    public void init() {
        for (int i = 0; i < 5; i++) {
            ImageView image = new ImageView(getContext());
            image.setBackgroundResource(R.drawable.kevin);
            int screenWidth = Util.getScreenWidth((MainActivity) getContext());
            int width = (screenWidth - 12) / 5;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, Util.dpToPx(getResources(), 78));
            params.rightMargin = Util.dpToPx(getResources(), 3);
            image.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    isInterceptTouch = true;
                    currentView = v;
                    getParent().requestDisallowInterceptTouchEvent(true);
                    createBitmap();
                    startWobbleAnim();
                    return true;
                }
            });
            addView(image, params);
        }
        childCount = getChildCount();
    }

    private void startWobbleAnim() {
        stopWobbleAnim();
        wobbleSet = new AnimatorSet();
        ObjectAnimator[] obectAnimators = new ObjectAnimator[childCount];
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            ObjectAnimator animator = ObjectAnimator.ofFloat(child, "rotation", 3, -3);
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            obectAnimators[i] = animator;
        }
        wobbleSet.playTogether(obectAnimators);
        wobbleSet.setDuration(180);
        wobbleSet.start();
    }

    private void stopWobbleAnim() {
        if (wobbleSet != null) {
            wobbleSet.end();
            wobbleSet = null;
            for (int i = 0; i < childCount; i++) {
                getChildAt(i).setRotation(0);
            }
        }

    }

    private void createBitmap() {
        mRect = new Rect(currentView.getLeft(), currentView.getTop(), currentView.getRight(), currentView.getBottom());
        Bitmap bitmap = Bitmap.createBitmap(currentView.getWidth(), currentView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        currentView.draw(canvas);
        drawable = new BitmapDrawable(getResources(), bitmap);
        currentView.setVisibility(INVISIBLE);
        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (drawable != null) {
            drawable.setBounds(mRect);
            drawable.draw(canvas);
        }
    }
}
