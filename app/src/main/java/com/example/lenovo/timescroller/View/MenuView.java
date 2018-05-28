package com.example.lenovo.timescroller.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
public class MenuView extends HorizontalScrollView {

    /**
     * 是否隐藏滑块(且滑块不做动画了) @East.K
     */
    private boolean isHiddenLine;


    private Context context;
    private BaseRecyclerViewHolder.OnItemClickListener listener;
    /**
     * 滑块动画监听
     */
    private OnAnimationListener onAnimationListener;
    private List<MenuItems> mDatas;
    private ImageView line;
    private int itemWidth;
    private int itemHeight;
    private int curIndex = 0;
    private int itemPaddingRL = Util.dpToPx(getResources(), 0);
    private int itemPaddingTB = Util.dpToPx(getResources(), 3);
    private LinearLayout parentView;
    private static int DEFAULT_TEXT_COLOR = Color.parseColor("#333333");
    private static int DEFAULT_TEXT_SIZE = 15;
    private static int SELECTED_TEXT_COLOR = R.color.red;
    private static final int DEFAULT_LINE_WIDTH = 190;
    private static final float DEFAULT_VISIBLE_ITEM_COUNT = 3f;
    private static final float DEFAULT_PROPORTION = 1;
    private List<View> titleView = new ArrayList<View>();
    private List<View> itemViews = new ArrayList<View>();
    private int lineWidth = 0;
    private float visibleItemCount = DEFAULT_VISIBLE_ITEM_COUNT;
    private float proportion = DEFAULT_PROPORTION;
    /**
     * 是否正在动画
     */
    private boolean isAniming = false;
    /**
     * 设置textView 高度
     */
    private int txtHeight = -1;
    /**
     * 是否单行
     */
    private boolean isSingleLine = true;
    /**
     * 是否调用监听
     */
    private boolean isNeedListener = true;
    /**
     * 需要 scroll 的view
     */
    private View needScrollView;
    private List<MenuItems> menuItemses;
    private int totalWidth;

    public MenuView(Context context) {
        this(context, null);
    }

    public MenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        if (needScrollView != null && (needScrollView.getLeft() != needScrollView.getRight())) {
            doScroll(needScrollView);
            needScrollView = null;
        }
    }

    /**
     * 初始化
     */
    private void init() {
        setHorizontalScrollBarEnabled(false);
        // 所有分类父布局
        parentView = new LinearLayout(context);
        parentView.setOrientation(LinearLayout.HORIZONTAL);
        parentView.setPadding(3, 8, 3, 8);
//        parentView.setBackgroundResource(R.color.white);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        parentView.setLayoutParams(params);
        parentView.setId(ViewUtils.generateViewId());
        RelativeLayout itemView = new RelativeLayout(context);
        LayoutParams itemsParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        itemView.setLayoutParams(itemsParams);
        // 滑块
        line = new ImageView(context);
        line.setId(ViewUtils.generateViewId());
        line.setImageResource(R.color.red);
        RelativeLayout.LayoutParams lineParams = new RelativeLayout.LayoutParams(lineWidth, Util.dpToPx(getResources(), 2.2f));
        lineParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        line.setLayoutParams(lineParams);
        itemView.setBackgroundResource(R.color.white);
        itemView.addView(line);
        itemView.addView(parentView);
        setPadding(0, 0, 0, Util.dpToPx(getResources(), 0.5f));
//        setBackgroundResource(R.drawable.border_only_bottom_theme_gray_bg_grey_line);

        //添加底部的线
        View bottomLineView = new View(context);
        bottomLineView.setBackgroundColor(context.getResources().getColor(R.color.grey6));
        RelativeLayout.LayoutParams bottomLineViewLP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, Util.dpToPx(getResources(), 1f));
        bottomLineViewLP.addRule(RelativeLayout.BELOW, line.getId());
        bottomLineView.setLayoutParams(bottomLineViewLP);
        itemView.addView(bottomLineView);

        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(itemView);
    }


    /**
     * @param position
     * @param menuItems
     * @return
     */
    private LinearLayout newItemInstanceView(int position, MenuItems menuItems) {
        final LinearLayout imgLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imgLayout.setLayoutParams(params);
//        imgLayout.setBackgroundResource(R.color.white);
        imgLayout.setTag(position);
        imgLayout.setOrientation(LinearLayout.VERTICAL);
        imgLayout.setGravity(Gravity.CENTER);

        if (menuItems != null && !TextUtils.isEmpty(menuItems.getItemIconUrl())) {   //是否隐藏图标
            ImageView iv = new ImageView(context);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(itemWidth, ViewGroup.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(lp);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageLoaderUtil.loadImage(context, menuItems.getItemIconUrl(), iv);
            imgLayout.addView(iv);
            titleView.add(iv);
        } else {
            TextView tv = new TextView(context);
            tv.setLayoutParams(new LinearLayout.LayoutParams(itemWidth - 1, ViewGroup.LayoutParams.MATCH_PARENT));
            tv.setText(menuItems.getItemName());
            tv.setTextSize(15);
            tv.setGravity(Gravity.CENTER);
            tv.setSingleLine(isSingleLine);
            tv.setTextColor(DEFAULT_TEXT_COLOR);
            tv.setTextSize(DEFAULT_TEXT_SIZE);
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setTextColor(position == 0 ? getResources().getColor(SELECTED_TEXT_COLOR) : DEFAULT_TEXT_COLOR);
            imgLayout.addView(tv);
            titleView.add(tv);
        }
        imgLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag();
                scrollToItemView(tag);
//                if (tag != curIndex) {
//                    if (isAniming) {
//                        return;
//                    }
//                    isAniming = true;
//                    doScroll(v);
//                    doAnimation(tag, v, menuItems);
////                    if(onAnimationListener == null){
////                        if (listener != null) {
////                            listener.setOnItemClickListener(tag,  menuItems.getObject());
////                        }
////                    }
//                }
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
        int x = view.getLeft();
        int width = getMeasuredWidth();
        x = x - width / 2 + view.getMeasuredWidth() / 2;
        smoothScrollTo(x, 0);
    }

    /**
     * 执行滑块动画
     *
     * @param position
     * @param view     @暂时未使用该param
     */
    private void doAnimation(final int position, View view, final MenuItems menuItems) {
//        final Rect currItemRect = new Rect();
//        view.getHitRect(currItemRect);
//        Rect lineRect = new Rect();
//        line.getHitRect(lineRect);
        ObjectAnimator animator = ObjectAnimator.ofFloat(line, "translationX", curIndex * lineWidth, position * lineWidth);
        animator.setDuration(200);
        setTextColor(position);
        curIndex = position;
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (listener != null && isNeedListener) {
                    listener.onItemClick(position, menuItems.getObject());
                }
                isAniming = false;
            }
        });
        animator.start();


    }

    /**
     * 滑动到某个item，调用监听
     *
     * @param index
     */
    public void scrollToItemView(int index) {
        isNeedListener = true;
        scrollToPosition(index);
    }

    /**
     * 滑动到某个item，控制是否监听
     *
     * @param index
     * @param isNeedListener
     */
    public void scrollToItemView(int index, boolean isNeedListener) {
        this.isNeedListener = isNeedListener;
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
        if (v.getLeft() == v.getRight()) {
            needScrollView = v;
            requestLayout();
        } else {
            doScroll(v);
        }
        if (isHiddenLine) {
            // 禁用滑块
            if (listener != null && isNeedListener) {
                listener.onItemClick(index, menuItems.getObject());
            }
            curIndex = index;
        } else {
            // defult
            if (index != curIndex) {
                if (isAniming) {
                    return;
                }
                isAniming = true;
                doAnimation(index, v, menuItems);
            }
        }
    }

    /**
     * 根据屏幕可见个数计算每个类目的宽度
     *
     * @return
     */
    private int getItemWidth() {
        float tmpVisibleItemCount = visibleItemCount;
        if (mDatas != null) {
            if (mDatas.size() <= visibleItemCount) {
                tmpVisibleItemCount = mDatas.size();
            }
        }
        float width = Util.getScreenWidth((Activity) context) / tmpVisibleItemCount - itemPaddingRL * 2;
        return (int) (width + 0.5);
    }

    /**
     * 根据icon宽高比计算icon的高度
     *
     * @return
     */
    private int getIconHeight() {
        float height = getItemWidth() * proportion;
        return (int) (height + 0.5);
    }


    private void refreshItemView() {
        if (mDatas == null) {
            setVisibility(View.GONE);
            return;
        }
        itemViews.clear();

        if (parentView != null) {
            for (int i = 0; i < mDatas.size(); i++) {
                LinearLayout linearLayout = newItemInstanceView(i, mDatas.get(i));
                totalWidth += linearLayout.getWidth();
                parentView.addView(linearLayout);
                itemViews.add(linearLayout);
            }
        }
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
            if (titleView.get(position) instanceof TextView) {
                ((TextView) titleView.get(position)).setTextColor(getResources().getColor(SELECTED_TEXT_COLOR));
            }
            if (titleView.get(curIndex) instanceof TextView && curIndex < titleView.size()) {
                ((TextView) titleView.get(curIndex)).setTextColor(DEFAULT_TEXT_COLOR);
            }
        }
    }

    /**
     * 设置文本颜色
     *
     * @param size
     */
    public void setTextSize(float size) {
        if (titleView != null) {
            for (View view : titleView) {
                if (view instanceof TextView) {
                    ((TextView) view).setTextSize(size);
                }
            }
        }
    }

    /**
     * 设置 TextView 高度
     */
    public void setTextViewHeight(int txtHeight) {
        this.txtHeight = txtHeight;
    }

    /**
     * 设置分类菜单数据
     *
     * @param data             类目数据集合
     * @param visibleItemCount 屏幕内分类的可见个数
     * @param proportion       icon宽高比
     */
    public void setData(List<MenuItems> data, float visibleItemCount, float proportion) {
        parentView.removeAllViews();
        this.mDatas = data;
        if (visibleItemCount >= 3) {
            this.visibleItemCount = visibleItemCount;
        }
        this.proportion = proportion > 0 ? proportion : 0.25f;
        itemWidth = getItemWidth();
        itemHeight = getIconHeight();

        if (isHiddenLine()) {
            line.setVisibility(View.GONE);
        } else {
            line.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams lp = line.getLayoutParams();
            lp.width = itemWidth + itemPaddingRL * 2;
            double left = lp.width * 0.08;
            double right = lp.width * 0.08;
            line.setPadding((int) left, 0, (int) right, 0);
            lineWidth = lp.width;
            line.setLayoutParams(lp);
        }
        refreshItemView();
    }

    /**
     * 设置数据源
     */
    public void setDataOnlyTxt(List<String> data, float visibleItemCount, int txtHeight) {
        List<MenuItems> datas = new ArrayList<MenuItems>();
        for (int i = 0; i < data.size(); i++) {
            MenuItems menuItem = new MenuItems();
            menuItem.setItemName(data.get(i));
            datas.add(menuItem);
        }
        setTextViewHeight(txtHeight);
        setIsSingleLine(true);
        setData(datas, visibleItemCount, 0);
    }

    /**
     * 初始化文本加角标数据
     */
    public void setDataTxTAndLable(List<NewTab> mList) {
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
            setTextViewHeight(txtHeight);
            setIsSingleLine(true);
            setData(menuItemses, menuItemses.size() * 2 / 3, 0);
        }
    }

    public boolean isHiddenLine() {
        return isHiddenLine;
    }

    public void setIsHiddenLine(boolean isHiddenLine) {
        this.isHiddenLine = isHiddenLine;
    }

    /**
     * @param onAnimationListener
     */
    public void setOnAnimationListener(OnAnimationListener onAnimationListener) {
        this.onAnimationListener = onAnimationListener;
    }


    /**
     * 设置文本显示是否单行
     *
     * @param isSingleLine
     */
    public MenuView setIsSingleLine(boolean isSingleLine) {
        this.isSingleLine = isSingleLine;
        return this;
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
