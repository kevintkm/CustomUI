package com.example.lenovo.timescroller;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by kevin.tian on 2016/4/28.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };
    Drawable drawable;
    public DividerItemDecoration(Context context) {
        TypedArray array=context.obtainStyledAttributes(ATTRS);
        drawable=array.getDrawable(0);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + drawable.getIntrinsicHeight();
            drawable.setBounds(left, top, right, bottom);
            drawable.draw(c);
//            Paint paint=new Paint();
//            paint.setColor(Color.BLUE);
//            c.drawLine(view.getWidth() / 2 - 2, child.getTop(), view.getWidth() / 2 + 2, child.getBottom(), paint);

        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
       /* Paint paint=new Paint();
        paint.setColor(Color.BLUE);
        c.drawLine(parent.getWidth() / 2 - 2, parent.getTop(), parent.getWidth() / 2 + 2, parent.getBottom(), paint);*/
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }
}
