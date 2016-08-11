package com.example.lenovo.timescroller.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by kevin.tian on 2016/7/7.
 */
public class SimpleScroller extends FrameLayout{
    private Scroller mScroller;
    public SimpleScroller(Context context) {
        super(context);
        mScroller = new Scroller(context);
    }

    public SimpleScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    public SimpleScroller(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            this.postInvalidate();
        }
    }

    public void scrollTo(int y){
        mScroller.startScroll(getScrollX(),getScrollY(),y,0);
        this.invalidate();
    }
}
