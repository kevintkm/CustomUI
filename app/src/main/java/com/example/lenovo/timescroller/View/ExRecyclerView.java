package com.example.lenovo.timescroller.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.lenovo.timescroller.Adapter.BaseRecyclerViewAdapter;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.ViewHolder.DynamicRecyclerViewHolder;
import com.example.lenovo.timescroller.ViewHolder.HFRecyclerViewHolder;
import com.example.lenovo.timescroller.ViewHolder.SingleLineRecyclerViewHolder;

import java.util.ArrayList;

/**
 * Created by chris on 16/4/7.
 */
public class ExRecyclerView extends RecyclerView {

    public interface OnRefreshListener {
        void onHeaderRefresh();
        void onFooterRefresh();
    }

    public interface OnExScrollListener {
        void onScrollStateChanged(RecyclerView recyclerView, int newState);
        void onScrolled(RecyclerView recyclerView, int dx, int dy);
    }

    private InnerAdapter adapter = null;
    private OnRefreshListener listener = null;
    private boolean pullDown = true;
    private boolean pullUp = false;
    private int positionY = 0;
    private float pullFactor = 0.3f;
    private String[][] refreshText = null;

    private View anchorView = null;
    private ArrayList<View> scrapPinnedView = null;
    private boolean pinned = false;
    private View pinnedView = null;
    private float pinnedViewOffset = 0f;
    private int widthMode = MeasureSpec.UNSPECIFIED;
    private boolean isNoMore = false;
    private OnExScrollListener exOnScrollListener;

    public ExRecyclerView(Context context) {
        this(context, null);
    }

    public ExRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);

        if (!(layout instanceof StaggeredGridLayoutManager) || ((StaggeredGridLayoutManager) layout).getOrientation() != StaggeredGridLayoutManager.VERTICAL) {
            new Throwable("Should be staggered or vertical !");
            return;
        }

        init();
    }

    public ExRecyclerView initialize(int spanCount) {
        setLayoutManager(new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL));
        setItemAnimator(new RecyclerViewItemAnimator());
        return this;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter == null) {
            return;
        }
        this.adapter.setInnerAdapter((BaseRecyclerViewAdapter) adapter);
        super.swapAdapter(this.adapter, true);
    }

    public Adapter getAdapter() {
        return adapter.getInnerAdapter();
    }

    /************************************************************************************************************
     * Listener: header or footer
     ************************************************************************************************************/
    public void setOnRefreshListener(OnRefreshListener l) {
        listener = l;
    }

    /************************************************************************************************************
     * 设置滑动监听  以及接口回调   @East.K
     ************************************************************************************************************/
    public void setOnExScrollListener(OnExScrollListener exOnScrollListener) {
        this.exOnScrollListener = exOnScrollListener;
    }

    public void setOnTopRefresh() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                HFRecyclerViewHolder viewHolder = getHFViewHolderByPos(0);
                if (viewHolder != null) {
                    viewHolder.onScroll((int) (viewHolder.getMaxHeight() * 1.2f));
                    viewHolder.onRelease();
                }
            }
        }, 500);
    }

    public void onLoadNoMoreComplete() {
        if (pullUp) {
            HFRecyclerViewHolder holder = getHFViewHolder();
            if (holder != null) {
                holder.noMore();
            }
            pullUp = false;
        }
    }

    public void onRefreshComplete() {
        HFRecyclerViewHolder holder = getHFViewHolder();
        if (pullUp) {
            pullUp = false;
        } else if (pullDown) {
            pullDown = false;
        }
        if (holder != null) {
            holder.reset();
        }
    }

    /**
     * 是否下拉刷新
     *
     * @return
     */
    public boolean isPullRefresh() {
        return pullDown;
    }

    public void reset() {
        isNoMore = false;
    }

    /************************************************************************************************************
     * 公有方法 Header & Footer
     ************************************************************************************************************/
    public ExRecyclerView setHeaderView(int layoutResId) {
        if (adapter != null) {
            adapter.setHeaderView(layoutResId).enableHeader(true);
        }
        return this;
    }

    public ExRecyclerView setFooterView(int layoutResId) {
        if (adapter != null) {
            adapter.setFooterView(layoutResId).enableFooter(true);
        }
        return this;
    }

    public ExRecyclerView setHeaderRefreshTextsAttr(String[][] headerRefreshText) {
        if (hasHeaderView()) {
            this.refreshText = headerRefreshText;
            HFRecyclerViewHolder holder = getHFViewHolderByPos(0);
            if (holder != null) {
                holder.setHfRefreshText(headerRefreshText);
            }
        }
        return this;
    }

    public ExRecyclerView setFooterRefreshTextsAttr(String[][] footerRefreshText) {
        if (hasFooterView()) {
            this.refreshText = footerRefreshText;
            HFRecyclerViewHolder holder = getHFViewHolderByPos(getLastItemPosition());
            if (holder != null) {
                holder.setHfRefreshText(footerRefreshText);
            }
        }
        return this;
    }

    public boolean hasHeaderView() {
        return adapter.headerEnable();
    }

    public boolean hasFooterView() {
        return adapter.footerEnable();
    }

    public ExRecyclerView setPullFactor(float factor) {
        pullFactor = factor;
        return this;
    }

    public int getFirstItemPosition() {
        return hasHeaderView() ? 1 : 0;
    }

    public int getLastItemPosition() {
        return adapter.getItemCount() - 1;
    }

    /************************************************************************************************************
     * 公有方法 pinned
     ************************************************************************************************************/
    public ExRecyclerView pinnedEnable(boolean enable) {
        pinned = enable;
        if (pinned) {
            scrapPinnedView = new ArrayList<>();
        }
        return this;
    }

    /************************************************************************************************************
     * Default Initialization
     ************************************************************************************************************/
    private void init() {
        adapter = new InnerAdapter(null);
        initScrollListener();
    }

    /************************************************************************************************************
     * Get First/Last Position
     ************************************************************************************************************/
    private StaggeredGridLayoutManager getStaggeredGridLayoutManager() {
        return (StaggeredGridLayoutManager) getLayoutManager();
    }

    private int getFirstVisiblePosition() {
        StaggeredGridLayoutManager layoutManager = getStaggeredGridLayoutManager();
        int[] pos = layoutManager.findFirstVisibleItemPositions(new int[layoutManager.getSpanCount()]);
        return getMinPosition(pos);
    }

    private int getLastVisiblePosition() {
        StaggeredGridLayoutManager layoutManager = getStaggeredGridLayoutManager();
        int[] pos = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
        return getMaxPosition(pos);
    }

    private int getMinPosition(int[] position) {
        int minPos = Integer.MAX_VALUE;
        for (int i = 0; i < position.length; i++) {
            if (minPos > position[i]) {
                minPos = position[i];
            }
        }
        return minPos;
    }

    private int getMaxPosition(int[] position) {
        int maxPos = Integer.MIN_VALUE;
        for (int i = 0; i < position.length; i++) {
            if (maxPos < position[i]) {
                maxPos = position[i];
            }
        }
        return maxPos;
    }

    /************************************************************************************************************
     * Get RecyclerView.ViewHolder
     ************************************************************************************************************/
    private HFRecyclerViewHolder getHFViewHolder() {
        HFRecyclerViewHolder holder = null;
        int pos = 0;
        if (pullUp) {
            pos = adapter.getItemCount() - 1;
        } else if (pullDown) {
            pos = 0;
        } else {
            return null;
        }

        View view = getLayoutManager().findViewByPosition(pos);
        if (view != null) {
            RecyclerView.ViewHolder viewHolder = getChildViewHolder(view);
            if (viewHolder instanceof HFRecyclerViewHolder) {
                holder = (HFRecyclerViewHolder) viewHolder;
            }
        }
        return holder;
    }

    private HFRecyclerViewHolder getHFViewHolderByPos(int pos) {
        HFRecyclerViewHolder holder = null;
        View view = getLayoutManager().findViewByPosition(pos);
        if (view != null) {
            RecyclerView.ViewHolder viewHolder = getChildViewHolder(view);
            if (viewHolder instanceof HFRecyclerViewHolder) {
                holder = (HFRecyclerViewHolder) viewHolder;
            }
        }
        return holder;
    }

    private SingleLineRecyclerViewHolder getViewHolderByPos(int pos) {
        SingleLineRecyclerViewHolder holder = null;
        View view = getLayoutManager().findViewByPosition(pos);
        if (view != null) {
            RecyclerView.ViewHolder viewHolder = getChildViewHolder(view);
            if (viewHolder instanceof SingleLineRecyclerViewHolder) {
                holder = (SingleLineRecyclerViewHolder) viewHolder;
            }
        }
        return holder;
    }

    /************************************************************************************************************
     * 内部私有方法: For pinned view
     ************************************************************************************************************/
    private int findAfterPinnedViewPosition(int position) {
        int afterPinnedPos = -1;
        for (int i = position; i < adapter.getItemCount(); i++) {
            if (adapter.isPinnedItem(adapter.getItemViewType(i))) {
                afterPinnedPos = i;
                break;
            }
        }
        return afterPinnedPos;
    }

    private int findLastPinnedViewPosition(int position) {
        int lastPinnedPos = -1;
        for (int i = position; i >= 0; i--) {
            if (adapter.isPinnedItem(adapter.getItemViewType(i))) {
                lastPinnedPos = i;
                break;
            }
        }
        return lastPinnedPos;
    }

    private void ensurePinnedHeaderLayout(View header) {
        if (header != null && header.isLayoutRequested()) {
            int widthSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), widthMode);

            int heightSpec;
            ViewGroup.LayoutParams layoutParams = header.getLayoutParams();
            if (layoutParams != null && layoutParams.height > 0) {
                heightSpec = MeasureSpec.makeMeasureSpec(layoutParams.height, MeasureSpec.EXACTLY);
            } else {
                heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            }
            header.measure(widthSpec, heightSpec);
            header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());
        }
    }

    private void drawPinnedView(Canvas canvas) {
        if (adapter == null || !pinned || pinnedView == null) {
            return;
        }
        int saveCount = canvas.save();
        canvas.translate(0, pinnedViewOffset);
        canvas.clipRect(0, 0, getWidth(), pinnedView.getMeasuredHeight());
        pinnedView.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    /************************************************************************************************************
     * ScrollStateChange: For Pull
     ************************************************************************************************************/
    private void scrollChangeForPull(int newState) {
        if (newState == SCROLL_STATE_IDLE) {
            if ((getLastVisiblePosition() + 1 == getLastItemPosition()) && hasFooterView()) {
                HFRecyclerViewHolder holder = getHFViewHolderByPos(getLastItemPosition());
                if (holder != null) {
                    if (holder.getStatus() == HFRecyclerViewHolder.STATUS_NONE) {
                        pullUp = true;
                        pullDown = false;
                    }
                }
            } else if ((getFirstVisiblePosition() == getFirstItemPosition() || getFirstVisiblePosition() == -1) && hasHeaderView()) {
                HFRecyclerViewHolder holder = getHFViewHolderByPos(0);
                if (holder != null) {
                    if (holder.getStatus() == HFRecyclerViewHolder.STATUS_NONE) {
                        pullDown = true;
                        pullUp = false;
                    }
                }
            }
        }
    }

    /************************************************************************************************************
     * Scroll-ing: For Pinned
     ************************************************************************************************************/
    private void scrollForPinned() {
        if (adapter == null || !pinned || adapter.getItemCount() == 0) {
            return;
        }

        pinnedViewOffset = 0;
        int first = getFirstVisiblePosition();
        int end = getLastVisiblePosition();

        getPinnedView(first);
        setPinnedView(pinnedView);
        calcOffset(first, end);
    }

    private void getPinnedView(int first) {
        if (adapter.isPinnedItem(adapter.getItemViewType(first))) {
            SingleLineRecyclerViewHolder holder = getViewHolderByPos(first);
            if (holder != null) {
                if (holder.itemView.getTop() == getPaddingTop()) {  // just-is, no need pinned
                    pinnedView = null;
                } else {                                            // first need pinned item scroll-in
                    if (pinnedView != null) {
                        scrapPinnedView.add(pinnedView);
                    }
                    pinnedView = holder.itemView;
                    ensurePinnedHeaderLayout(pinnedView);
                }
            }
        } else {
            int lastPinnedPos = findLastPinnedViewPosition(first);  // check last pinned if-exist
            if (lastPinnedPos > -1) {                               // has then show it
                if (scrapPinnedView != null && scrapPinnedView.size() > 0) {
                    pinnedView = scrapPinnedView.remove(scrapPinnedView.size() - 1);
                }
            } else {
                pinnedView = null;
            }
        }
    }

    private void setPinnedView(View view) {
        if (view != null) {
            if (anchorView == null) {
                anchorView = new View(getContext());
                anchorView.setLayoutParams(new ViewGroup.LayoutParams(view.getMeasuredWidth(), view.getMeasuredHeight()));
                ((ViewGroup) getParent()).addView(anchorView);
                anchorView.setY(getRealLocationY());
            }
        } else {
            if (anchorView != null) {
                ((ViewGroup) getParent()).removeView(anchorView);
                anchorView = null;
            }
        }

        SingleLineRecyclerViewHolder holder = null;
        if (view != null) {
            int lastPinnedPos = findLastPinnedViewPosition(getFirstVisiblePosition());
            if (lastPinnedPos > -1) {
                holder = getViewHolderByPos(lastPinnedPos);
            }
        } else {
            int afterPinnedPos = findAfterPinnedViewPosition(getFirstVisiblePosition());
            if (afterPinnedPos > -1) {
                holder = getViewHolderByPos(afterPinnedPos);
            }
        }
        if (holder != null) {
            holder.setAnchorView(anchorView);
        }
    }

    private void calcOffset(int first, int end) {
        if (pinnedView != null) {
            for (int i = first + 1; i < end; i++) {
                if (adapter.isPinnedItem(adapter.getItemViewType(i)) && getViewHolderByPos(i) != null) {
                    View view = getViewHolderByPos(i).itemView;
                    float top = view.getTop();
                    float pinnedheight = pinnedView.getMeasuredHeight();
                    if (pinnedheight >= top && top > 0) {
                        pinnedViewOffset = top - view.getHeight();
                    }
                    break;
                }
            }
        }
    }

    private int getRealLocationY() {
        int[] loc = new int[2];
        getLocationOnScreen(loc);
        return (loc[1] - getStatusBarHeight());
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /************************************************************************************************************
     * Scroll Listener
     ************************************************************************************************************/
    private void initScrollListener() {
        setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                scrollChangeForPull(newState);
                if (exOnScrollListener != null) {
                    exOnScrollListener.onScrollStateChanged(recyclerView, newState);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollForPinned();
                if (exOnScrollListener != null) {
                    exOnScrollListener.onScrolled(recyclerView, dx, dy);
                }
            }
        });
    }

    /************************************************************************************************************
     * Get mode for measure childview
     ************************************************************************************************************/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthMode = MeasureSpec.getMode(widthMeasureSpec);
    }

    /************************************************************************************************************
     * Dispatch Event
     ************************************************************************************************************/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (pullDown || pullUp) {
            HFRecyclerViewHolder holder = getHFViewHolder();
            if (holder != null) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        positionY = (int) ev.getY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        int pos = (int) ((positionY - ev.getY()) * pullFactor);
                        if (pullDown) {
                            pos *= -1;
                        }
                        holder.onScroll(pos);
                        positionY = (int) ev.getY();
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        positionY = 0;
                        holder.onRelease();
                        break;
                }

                if (pullDown && holder.getHeight() > holder.getMinHeight()) {
                    return true;
                }
            }
        }

        if (pinnedView != null) {
            if (ev.getY() < pinnedView.getMeasuredHeight()) {
                pinnedView.dispatchTouchEvent(ev);
                invalidate();
                return true;
            }
        }
        boolean dispatchTouchEventBool = false;
        try {
            dispatchTouchEventBool = super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dispatchTouchEventBool;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawPinnedView(canvas);
    }

    /************************************************************************************************************
     * 内部Adapter
     ************************************************************************************************************/
    protected class InnerAdapter extends BaseRecyclerViewAdapter {
        private BaseRecyclerViewAdapter innerAdapter = null;
        private int headerViewLayoutResId = 0;
        private int footerViewLayoutResId = 0;
        private boolean enableHeader = false;
        private boolean enableFooter = false;
        public static final int TYPE_HEADER = -1;
        public static final int TYPE_FOOTER = -2;

        public InnerAdapter(BaseRecyclerViewAdapter innerAdapter) {
            this.innerAdapter = innerAdapter;
        }

        public void setInnerAdapter(BaseRecyclerViewAdapter innerAdapter) {
            this.innerAdapter = innerAdapter;
            this.innerAdapter.registerAdapterDataObserver(new AdapterDataObserver() {
                @Override
                public void onChanged() {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    notifyItemRangeInserted(positionStart, itemCount);
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    notifyItemRangeRemoved(positionStart, itemCount);
                }
            });
        }

        public BaseRecyclerViewAdapter getInnerAdapter() {
            return innerAdapter;
        }

        public InnerAdapter setHeaderView(int layoutResId) {
            headerViewLayoutResId = layoutResId;
            return this;
        }

        public InnerAdapter setFooterView(int layoutResId) {
            footerViewLayoutResId = layoutResId;
            return this;
        }

        public InnerAdapter enableHeader(boolean enableHeader) {
            this.enableHeader = enableHeader;
            return this;
        }

        public boolean headerEnable() {
            return enableHeader;
        }

        public boolean footerEnable() {
            return enableFooter;
        }

        public InnerAdapter enableFooter(boolean enableFooter) {
            this.enableFooter = enableFooter;
            return this;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder = null;
            switch (viewType) {
                case TYPE_HEADER:
                    if (headerViewLayoutResId > 0) {
                        holder = new HeaderViewHolder(LayoutInflater.from(getContext()).inflate(headerViewLayoutResId, parent, false), getContext());
                        holder.setIsRecyclable(false);
                    }
                    break;

                case TYPE_FOOTER:
                    if (footerViewLayoutResId > 0) {
                        holder = new FooterViewHolder(LayoutInflater.from(getContext()).inflate(footerViewLayoutResId, parent, false), getContext());
                    }
                    break;

                default:
                    holder = innerAdapter.onCreateViewHolder(parent, viewType);
                    break;
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            int type = getItemViewType(position);
            if (type == TYPE_HEADER || type == TYPE_FOOTER) {
                if (holder instanceof HFRecyclerViewHolder) {
                    StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) ((HFRecyclerViewHolder) holder).layout.getLayoutParams();
                    lp.setFullSpan(true);
                }
            } else {
                innerAdapter.onBindViewHolder(holder, enableHeader ? position - 1 : position);
            }
            setFullSpan(holder);
        }

        @Override
        public int getItemViewType(int position) {
            int headerPosition = 0;
            int footerPosition = getItemCount() - 1;
            if (position == headerPosition && enableHeader) {
                return TYPE_HEADER;
            } else if (position == footerPosition && enableFooter) {
                return TYPE_FOOTER;
            } else {
                return innerAdapter.getItemViewType(enableHeader ? position - 1 : position);
            }
        }

        @Override
        public int getItemCount() {
            int count = (innerAdapter == null ? 0 : innerAdapter.getItemCount());
            if (enableHeader) {
                count++;
            }
            if (enableFooter) {
                count++;
            }
            return count;
        }

        @Override
        public boolean isPinnedItem(int viewType) {
            return innerAdapter != null && innerAdapter.isPinnedItem(viewType);
        }

        private void setFullSpan(ViewHolder holder) {
            if (holder instanceof SingleLineRecyclerViewHolder) {
                StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) ((SingleLineRecyclerViewHolder) holder).layout.getLayoutParams();
                if (lp != null) {
                    lp.setFullSpan(true);
                }
            } else if (holder instanceof DynamicRecyclerViewHolder) {
                StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) ((DynamicRecyclerViewHolder) holder).layout.getLayoutParams();
                if (lp != null) {
                    lp.setFullSpan(((DynamicRecyclerViewHolder) holder).isFullSpan());
                }
            }
        }
    }

    /************************************************************************************************************
     * Header ViewHolder
     ************************************************************************************************************/
    protected class HeaderViewHolder extends HFRecyclerViewHolder {

        private final String UPDATE_DOWN = "下拉即可刷新";
        private final String UPDATE_RELEASE = "释放即可刷新";
        private final String UPDATE_ING = "刷新中...";
        private LinearLayout layoutTips = null;
        private ImageView loadAnim = null;
        private TextView loadText = null;
        private AnimationDrawable drawableAnim = null;

        public HeaderViewHolder(View itemView, Context context) {
            super(itemView, context);

            layoutTips = (LinearLayout) itemView.findViewById(R.id.layoutTips);
            loadAnim = (ImageView) itemView.findViewById(R.id.loadAnim);
            loadText = (TextView) itemView.findViewById(R.id.loadText);
            loadAnim.setImageResource(R.drawable.md_refresh_loading);
            drawableAnim = (AnimationDrawable) loadAnim.getDrawable();
            if (refreshText != null && refreshText.length > 0) {
                setHfRefreshText(refreshText);
            } else {
                setHfRefreshText(new String[][]{
                        {UPDATE_DOWN, DEFAULT_TXT_SIZE, DEFAULT_TXT_COLOR},
                        {UPDATE_RELEASE, DEFAULT_TXT_SIZE, DEFAULT_TXT_COLOR},
                        {UPDATE_ING, DEFAULT_TXT_SIZE, DEFAULT_TXT_COLOR}
                });
            }
            addRules();
        }

        private void addRules() {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutTips.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutTips.setLayoutParams(lp);
        }

        @Override
        public void onStatusChange(int status) {
            drawableAnim.start();
            switch (status) {
                case STATUS_NONE:
                    pullDown = false;
                case STATUS_SCROLL_UNEXCEED:
                    setTextAttrByIndex(loadText, 0);
                    break;

                case STATUS_SCROLL_EXCEED:
                    setTextAttrByIndex(loadText, 1);
                    break;

                case STATUS_REFRESHING:
                    if (listener != null) {
                        listener.onHeaderRefresh();
                    }
                    setTextAttrByIndex(loadText, 2);
                    break;
            }
        }
    }

    /************************************************************************************************************
     * Footer ViewHolder
     ************************************************************************************************************/
    protected class FooterViewHolder extends HFRecyclerViewHolder {

        private final String LOAD_MORE = "加载更多";
        private final String LOAD_MORE_RELEASE = "释放即可加载";
        private final String LOAD_MORE_ING = "加载中...";
        private RelativeLayout noMore = null;
        private LinearLayout layoutTips = null;
        private ImageView loadAnim = null;
        private TextView loadText = null;
        private AnimationDrawable drawableAnim = null;

        public FooterViewHolder(View itemView, Context context) {
            super(itemView, context);

            noMore = (RelativeLayout) itemView.findViewById(R.id.noMore);
            layoutTips = (LinearLayout) itemView.findViewById(R.id.layoutTips);
            loadAnim = (ImageView) itemView.findViewById(R.id.loadAnim);
            loadText = (TextView) itemView.findViewById(R.id.loadText);
            loadAnim.setImageResource(R.drawable.md_refresh_loading);
            drawableAnim = (AnimationDrawable) loadAnim.getDrawable();

            if (refreshText != null && refreshText.length > 0) {
                setHfRefreshText(refreshText);
            } else {
                setHfRefreshText(new String[][]{
                        {LOAD_MORE, DEFAULT_TXT_SIZE, DEFAULT_TXT_COLOR},
                        {LOAD_MORE_RELEASE, DEFAULT_TXT_SIZE, DEFAULT_TXT_COLOR},
                        {LOAD_MORE_ING, DEFAULT_TXT_SIZE, DEFAULT_TXT_COLOR}
                });
            }
            addRules();
            if (isNoMore) {
                noMore();
            }
        }

        private void addRules() {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutTips.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutTips.setLayoutParams(lp);
        }

        @Override
        public void noMore() {
            isNoMore = true;
            layoutTips.setVisibility(View.GONE);
            noMore.setVisibility(View.VISIBLE);
            super.noMore();
        }

        @Override
        public void onStatusChange(int status) {
            drawableAnim.start();
            switch (status) {
                case STATUS_NONE:
                    pullUp = false;
                case STATUS_SCROLL_UNEXCEED:
                    setTextAttrByIndex(loadText, 0);
                    break;

                case STATUS_SCROLL_EXCEED:
                    setTextAttrByIndex(loadText, 1);
                    break;

                case STATUS_REFRESHING:
                    if (listener != null) {
                        listener.onFooterRefresh();
                    }
                    setTextAttrByIndex(loadText, 2);
                    break;
            }
        }
    }
}
