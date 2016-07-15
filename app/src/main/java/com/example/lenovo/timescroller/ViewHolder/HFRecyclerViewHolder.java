package com.example.lenovo.timescroller.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.timescroller.R;


/**
 * Created by chris on 16/4/9.
 */
public abstract class HFRecyclerViewHolder extends BaseRecyclerViewHolder {

    public RelativeLayout layout = null;
    private int maxHeight = 0;
    private int minHeight = 0;

    protected final String DEFAULT_TXT_SIZE = "13";
    protected final String DEFAULT_TXT_COLOR = "#fd6847";
    private String[][] hfRefreshText = null;

    public static final int STATUS_NONE = 0;
    public static final int STATUS_SCROLL_UNEXCEED = 1;
    public static final int STATUS_SCROLL_EXCEED = 2;
    public static final int STATUS_REFRESHING = 3;
    public static final int STATUS_FORBIDDEN = 4;
    private int status = STATUS_NONE;

    public HFRecyclerViewHolder(View itemView) {
        this(itemView, null);
    }

    public HFRecyclerViewHolder(View itemView, Context context) {
        super(itemView, context);
        layout = (RelativeLayout) itemView.findViewById(R.id.layout);
        maxHeight = measureLayout(layout);
        resetLayout(minHeight);
    }

    public int getHeight() {
        StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) layout.getLayoutParams();
        return lp.height;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setHfRefreshText(String[][] hfRefreshText) {
        this.hfRefreshText = hfRefreshText;
    }

    /*******************************************************************************************************************
     * Measure the layout real height (header / footer)
     *******************************************************************************************************************/
    private int measureLayout(View view) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w = View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.EXACTLY);
        int h = View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.AT_MOST);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }

    /*******************************************************************************************************************
     * Init layout and give it 1-pixel height
     *******************************************************************************************************************/
    private void resetLayout(int height) {
        StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) layout.getLayoutParams();
        if (lp == null) {
            lp = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        } else {
            lp.height = height;
        }
        layout.setLayoutParams(lp);
    }

    /*******************************************************************************************************************
     * TouchEvent process, adjust layout's height when MotionEvent.ACTION_MOVE
     *******************************************************************************************************************/
    public void onScroll(int distanceY) {
        StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) layout.getLayoutParams();
//        L.msg("distanceY = " + distanceY + ", lp.height = " + lp.height);
        lp.height += distanceY;
        if (status < STATUS_REFRESHING && lp.height <= minHeight) {
            lp.height = minHeight;
        } else if (status >= STATUS_REFRESHING && lp.height <= maxHeight) {
            lp.height = maxHeight;
        }
        layout.setLayoutParams(lp);
        setStatus(lp.height, false);
    }

    /*******************************************************************************************************************
     * TouchEvent process, adjust layout's height when MotionEvent.ACTION_UP or CANCEL
     *******************************************************************************************************************/
    public void onRelease() {
        StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) layout.getLayoutParams();
        if (status >= STATUS_REFRESHING || lp.height >= maxHeight) {
            lp.height = maxHeight;
        } else {
            lp.height = minHeight;
        }
        layout.setLayoutParams(lp);
        setStatus(lp.height, true);
    }

    /*******************************************************************************************************************
     * Notify child any status change
     *******************************************************************************************************************/
    private void setStatus(int height, boolean isRelease) {
//        L.msg("height = " + height + ", isRelease = " + isRelease);
        if (status < STATUS_REFRESHING) {
            if (!isRelease) {
                if (height >= maxHeight) {
                    status = STATUS_SCROLL_EXCEED;
                } else {
                    status = STATUS_SCROLL_UNEXCEED;
                }
            } else {
                if (height >= maxHeight) {
                    status = STATUS_REFRESHING;
                } else {
                    status = STATUS_NONE;
                }
            }
            onStatusChange(status);
        }
    }

    public int getStatus() {
        return status;
    }

    protected void setTextAttrByIndex(TextView tv, int refreshTextIndex) {
        if (tv != null && hfRefreshText != null && hfRefreshText.length > 0) {
            String text = "";
            float textSize = 0f;
            String textColor = null;
            if (refreshTextIndex < hfRefreshText.length) {
                text = hfRefreshText[refreshTextIndex][0];
                textSize = Float.parseFloat(hfRefreshText[refreshTextIndex][1]);
                textColor = hfRefreshText[refreshTextIndex][2];
            } else {
                text = hfRefreshText[0][0];
                textSize = Float.parseFloat(DEFAULT_TXT_SIZE);
                textColor = DEFAULT_TXT_COLOR;
            }
            tv.setText(text);
            tv.setTextSize(textSize);
            tv.setTextColor(Color.parseColor(textColor));
        }
    }

    /*******************************************************************************************************************
     * On Complete reset layout and status
     *******************************************************************************************************************/
    public void noMore() {
        resetLayout(minHeight);
        status = STATUS_NONE;
        onStatusChange(status);
    }

    public void reset() {
        resetLayout(minHeight);
        status = STATUS_NONE;
        onStatusChange(status);
    }

    public abstract void onStatusChange(int status);
}
