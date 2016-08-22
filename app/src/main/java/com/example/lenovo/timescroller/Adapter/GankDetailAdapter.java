package com.example.lenovo.timescroller.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.lenovo.timescroller.Activity.WebViewActivity;
import com.example.lenovo.timescroller.Model.MeiZhi;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.ViewHolder.BaseRecyclerViewHolder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kevin.tian on 2016/8/18.
 */
public class GankDetailAdapter extends BaseRecyclerViewAdapter<MeiZhi.ResultsBean>{

    private int mLastPosition = -1;
    private final static int DELAY = 139;
    private Context mContext;

    public GankDetailAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean isPinnedItem(int viewType) {
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_item_gank,null);
        return new GankDetailHolder(view,mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GankDetailHolder detailHolder = (GankDetailHolder) holder;
        MeiZhi.ResultsBean gank = lists.get(position);
        if (position == 0) {
            showCategory(detailHolder);
        }
        else {
            boolean theCategoryOfLastEqualsToThis = lists.get(
                    position - 1).getType().equals(lists.get(position).getType());
            if (!theCategoryOfLastEqualsToThis) {
                showCategory(detailHolder);
            }
            else {
                hideCategory(detailHolder);
            }
        }
        detailHolder.category.setText(gank.getType());
        SpannableStringBuilder builder = new SpannableStringBuilder(gank.getDesc()).append(
                String.format( " (via. " +
                        gank.getWho() +
                        ")"));
        CharSequence gankText = builder.subSequence(0, builder.length());

        detailHolder.gank.setText(gankText);
        showItemAnim(detailHolder.gank, position);
    }

    /**
     * view.isShown() is a kidding...
     */
    private boolean isVisibleOf(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    private void showCategory(GankDetailHolder holder) {
        if (!isVisibleOf(holder.category)) holder.category.setVisibility(View.VISIBLE);
    }


    private void hideCategory(GankDetailHolder holder) {
        if (isVisibleOf(holder.category)) holder.category.setVisibility(View.GONE);
    }

    public void showItemAnim(final View view, final int position) {
        if (position > mLastPosition) {
            view.setAlpha(0);
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                        Animation animation = AnimationUtils.loadAnimation(mContext,
                                R.anim.slide_in_right);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override public void onAnimationStart(Animation animation) {
                                view.setAlpha(1);
                            }


                            @Override public void onAnimationEnd(Animation animation) {}


                            @Override public void onAnimationRepeat(Animation animation) {}
                        });
                        view.startAnimation(animation);
                }
            }, DELAY * position);
            mLastPosition = position;
        }
    }
    public class GankDetailHolder extends BaseRecyclerViewHolder {
        @InjectView(R.id.tv_category)
        TextView category;
        @InjectView(R.id.tv_title)
        TextView gank;

        public GankDetailHolder(View itemView, Context context) {
            super(itemView, context);
            ButterKnife.inject(this, itemView);
        }

        @OnClick(R.id.ll_gank_parent)
        void onGank(View v) {
            MeiZhi.ResultsBean gank = lists.get(getLayoutPosition());
            WebViewActivity.startWebActivity(gank.getUrl(),mContext);
        }
    }
}
