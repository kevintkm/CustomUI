package com.example.lenovo.timescroller.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.lenovo.timescroller.Model.NewTab;
import com.example.lenovo.timescroller.Model.sortbean.MenuItems;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.Util.ImageLoaderUtil;
import com.example.lenovo.timescroller.Util.Util;
import com.example.lenovo.timescroller.Util.ViewUtils;
import com.example.lenovo.timescroller.ViewHolder.BaseRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 发洋财分类tabView(icon、title) 一行、可左滑矩形型，宽高比可变
 * Created by East.K on 2016/3/2.
 */
public class CategoryView extends ScrollView {

    private Context context;
    private BaseRecyclerViewHolder.OnItemClickListener listener;
    private List<MenuItems> mDatas;
    private int curIndex = 0;
    private int itemPaddingRL = Util.dpToPx(getResources(), 0);
    private LinearLayout parentView;
    private static int DEFAULT_TEXT_COLOR = Color.parseColor("#333333");
    private static int SELECTED_TEXT_COLOR = R.color.red;
    private static final float DEFAULT_VISIBLE_ITEM_COUNT = 3f;
    private static final float DEFAULT_PROPORTION = 1;
    private List<View> titleView = new ArrayList<View>();
    private List<View> itemViews = new ArrayList<View>();
    private float visibleItemCount = DEFAULT_VISIBLE_ITEM_COUNT;
    private float proportion = DEFAULT_PROPORTION;
    /**
     * 需要 scroll 的view
     */
    private View needScrollView;
    private List<MenuItems> menuItemses;

    public CategoryView(Context context) {
        this(context, null);
    }

    public CategoryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CategoryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        scrollNeedView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        scrollNeedView();
    }

    /**
     * 滚动 初始化时 的 needScrollView
     */
    private void scrollNeedView() {
        if (needScrollView != null && (needScrollView.getTop() != needScrollView.getBottom())) {
            doScroll(needScrollView);
            needScrollView = null;
        }
    }

    /**
     * 初始化
     */
    private void init() {
//        setOverScrollMode(OVER_SCROLL_NEVER);
        // 所有分类父布局
        parentView = new LinearLayout(context);
        parentView.setOrientation(LinearLayout.VERTICAL);
//        parentView.setBackgroundResource(R.color.white);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        parentView.setLayoutParams(params);
        parentView.setId(ViewUtils.generateViewId());
        RelativeLayout itemView = new RelativeLayout(context);
        LayoutParams itemsParams = new LayoutParams(LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
        itemView.setLayoutParams(itemsParams);
        itemView.setBackgroundResource(R.color.white);
        itemView.addView(parentView);
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(itemView);
    }


    /**
     * @param position
     * @param menuItems
     * @return
     */
    private View newItemInstanceView(int position, final MenuItems menuItems) {
        View imgLayout = LayoutInflater.from(context).inflate(R.layout.category_item_layout, this, false);
        imgLayout.setTag(position);
        TextView tv = (TextView) imgLayout.findViewById(R.id.category_tv_item);
        ImageView iv = (ImageView) imgLayout.findViewById(R.id.category_iv_item);
        tv.setText(menuItems.getItemName());
        titleView.add(tv);
        if (TextUtils.isEmpty(menuItems.getItemIconUrl())) {
            iv.setVisibility(GONE);
        } else {
            iv.setVisibility(VISIBLE);
            ImageLoaderUtil.loadImage(context, menuItems.getItemIconUrl(), iv);
        }
        imgLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag();
                if (listener != null) {
                    listener.onItemClick(tag, menuItems.getObject());
                }
                scrollToItemView(tag);
            }
        });

        return imgLayout;
    }

    /**
     * 判断点击的条目是否全部区域可见，否则滑动至完全可见
     *
     * @param view
     */
    private void doScroll(View view) {
        int y = view.getTop();
        int heght = getMeasuredHeight();
        y = y - heght / 2 + view.getMeasuredHeight() / 2;
        smoothScrollTo(0, y);
    }

    /**
     * 滑动到某个item，调用监听
     *
     * @param index
     */
    public void scrollToItemView(int index) {
        if (index==curIndex) {
            return;
        }
        scrollToPosition(index);
    }

    /**
     * 滑动到某个item，控制是否监听
     *
     * @param index
     * @param isNeedListener
     */
    public void scrollToItemView(int index, boolean isNeedListener) {
        scrollToPosition(index);
    }

    /**
     * 滑动到指定位置的 menu 并回调 Listener
     *
     * @param index 索引
     */
    public void scrollToPosition(int index) {
        if (itemViews.size() == 0 || index > itemViews.size() || mDatas == null || index > mDatas.size())
            return;
        final MenuItems menuItems = mDatas.get(index);
        if (menuItems == null)
            return;
        View v = itemViews.get(index);
        if (v.getTop() == v.getBottom()) {
            needScrollView = v;
            requestLayout();
        } else {
            doScroll(v);
        }
        setTextColor(index);
        curIndex = index;
    }

    private void refreshItemView() {
        if (mDatas == null) {
            setVisibility(View.GONE);
            return;
        }
        itemViews.clear();
        titleView.clear();
        if (parentView != null) {
            for (int i = 0; i < mDatas.size(); i++) {
                View linearLayout = newItemInstanceView(i, mDatas.get(i));
                parentView.addView(linearLayout);
                itemViews.add(linearLayout);
            }
        }
        setTextColor(0);
    }


    /**
     * 设置点击监听
     */
    public void setOnItemClickListener(BaseRecyclerViewHolder.OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 获取监听
     *
     * @return
     */
    public BaseRecyclerViewHolder.OnItemClickListener getOnItemClickListener() {
        return listener;
    }

    private void setTextColor(int position) {
        if (titleView != null && titleView.size() > position) {
            if (titleView.get(curIndex) instanceof TextView && curIndex < titleView.size()) {
                ((TextView) titleView.get(curIndex)).setTextColor(DEFAULT_TEXT_COLOR);
            }
            if (titleView.get(position) instanceof TextView) {
                ((TextView) titleView.get(position)).setTextColor(getResources().getColor(SELECTED_TEXT_COLOR));
            }
        }


        if (itemViews.size() > curIndex) {
            itemViews.get(curIndex).setBackgroundColor(Color.parseColor("#fff0f0f0"));
        }
        if (itemViews.size() > position) {
            itemViews.get(position).setBackgroundColor(Color.WHITE);
        }
    }

        /**
         * 设置文本颜色
         *
         * @param size
         */
        public void setTextSize ( float size){
            if (titleView != null) {
                for (View view : titleView) {
                    if (view instanceof TextView) {
                        ((TextView) view).setTextSize(size);
                    }
                }
            }
        }

        /**
         * 设置分类菜单数据
         *
         * @param data 类目数据集合
         */
        public void setData (List < MenuItems > data) {
            parentView.removeAllViews();
            this.mDatas = data;
            refreshItemView();
        }

        /**
         * 设置数据源
         */
        public void setDataOnlyTxt (List < String > data,float visibleItemCount, int txtHeight){
            List<MenuItems> datas = new ArrayList<MenuItems>();
            for (int i = 0; i < data.size(); i++) {
                MenuItems menuItem = new MenuItems();
                menuItem.setItemName(data.get(i));
                datas.add(menuItem);
            }
            setData(datas);
        }

        /**
         * 初始化文本加角标数据
         */
        public void setDataTxTAndLable (List < NewTab > mList) {
            if (menuItemses != null) {
                return;
            }
            if (mList != null && mList.size() > 0) {
                this.menuItemses = new ArrayList<>();
                for (NewTab info : mList) {
                    MenuItems menuItems = new MenuItems();
                    menuItems.setItemName(info.getName());
                    menuItems.setItemIconUrl(info.getIconUrl());
                    menuItems.setObject(info);
                    menuItemses.add(menuItems);
                }
                if (this.menuItemses == null || menuItemses.size() == 0) {
                    setVisibility(View.GONE);
                    return;
                }
                SELECTED_TEXT_COLOR = R.color.red;
                DEFAULT_TEXT_COLOR = Color.parseColor("#666666");
                setData(menuItemses);
            }
        }

        /**
         * 点击事件接口回调
         */
        public interface OnAnimationListener {
            void onAnimationEnd(int selectedPosition, Object object);
        }
/**
 *
 */
    }
