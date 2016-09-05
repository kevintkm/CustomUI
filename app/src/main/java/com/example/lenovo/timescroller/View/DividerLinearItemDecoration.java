package com.example.lenovo.timescroller.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;


/**
 * Created by kevin.tian on 2016/7/13.
 * StaggeredGridLayoutManager.VERTICAL方向的垂直分割线，row为1，根据RecyclerView不支持横向
 */
public class DividerLinearItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDrawable;
    private int mOrientation = StaggeredGridLayoutManager.VERTICAL;
    private final static int[] ATTRS = new int[]{android.R.attr.listDivider};
    private int mColor;
    private Context mContext;
    private Paint mPaint;
    private int mDefaultSize;
    private boolean hasHeader;

    public DividerLinearItemDecoration(Context context, int orientation) {
       this(context, 0, 0, orientation);
    }

    public DividerLinearItemDecoration(Context context, int color, int size, int orientation) {
        this.mContext = context;
        this.mColor = color;
        this.mOrientation = orientation;
        this.mDefaultSize = size;
        init();
    }

    private void init() {
        if (mOrientation != StaggeredGridLayoutManager.VERTICAL && mOrientation != StaggeredGridLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("布局方向参数不合法！");
        }

        if (mDefaultSize >0&&!"".equals(mColor)){
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(mContext.getResources().getColor(mColor));
            mPaint.setStyle(Paint.Style.FILL);
        }

        /**
         * 不设置分割线参数默认使用listDivider
         */
        if (mDrawable == null&&mPaint==null) {
            TypedArray array = mContext.obtainStyledAttributes(ATTRS);
            mDrawable = array.getDrawable(0);
            array.recycle();
        }

    }

    public void setmDrawable(Drawable mDrawable) {
        this.mDrawable = mDrawable;
    }

    public void setmPaint(Paint mPaint) {
        this.mPaint = mPaint;
    }

    public void setmColor(String mColor) {
        if (mPaint!=null){
            mPaint.setColor(Integer.valueOf(mColor));
        }
    }

    public void setmDefaultSize(int mDefaultSize) {
        if (mPaint!=null){
            this.mDefaultSize = mDefaultSize;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        drawLines(c, parent);
    }

    private void drawLines(Canvas c, RecyclerView parent) {
        int visibleCount = parent.getChildCount();
        int count = ((RecyclerView)parent).getAdapter().getItemCount();
        //最后一个item不绘制分割线
        for (int i = 0; i < visibleCount; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                int left = 0, right = 0, top = 0, bottom = 0;
                if (mOrientation == StaggeredGridLayoutManager.HORIZONTAL) {
                    top = parent.getTop() - params.topMargin;
                    bottom = parent.getHeight() - params.bottomMargin;
                    left = child.getRight() - params.rightMargin;
                    right = left + (mDefaultSize > 0 ? mDefaultSize : mDrawable.getIntrinsicWidth());
                } else {
                    top = child.getBottom() + params.bottomMargin;
                    bottom = top + (mDefaultSize > 0 ? mDefaultSize : mDrawable.getIntrinsicHeight());
                    left = parent.getLeft() - params.leftMargin;
                    right = parent.getRight() + params.rightMargin;
                }
                if (mDrawable != null) {
                    mDrawable.setBounds(left, top, right, bottom);
                    mDrawable.draw(c);
                } else {
                    c.drawRect(left, top, right, bottom, mPaint);
                    //      }
                }

        }

    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }
}
