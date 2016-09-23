package com.example.lenovo.timescroller.View;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 16/4/27.
 */
public class RecyclerViewItemAnimator extends RecyclerView.ItemAnimator {
    List<RecyclerView.ViewHolder> mAnimationAddViewHolders = new ArrayList<>();
    List<RecyclerView.ViewHolder> mAnimationRemoveViewHolders = new ArrayList<>();

    @Override
    public boolean animateDisappearance(RecyclerView.ViewHolder viewHolder, ItemHolderInfo preLayoutInfo, ItemHolderInfo postLayoutInfo) {
        return mAnimationRemoveViewHolders.add(viewHolder);
    }

    @Override
    public boolean animateAppearance(RecyclerView.ViewHolder viewHolder, ItemHolderInfo preLayoutInfo, ItemHolderInfo postLayoutInfo) {
        return mAnimationAddViewHolders.add(viewHolder);
    }

    @Override
    public boolean animatePersistence(RecyclerView.ViewHolder viewHolder, ItemHolderInfo preLayoutInfo, ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, ItemHolderInfo preLayoutInfo, ItemHolderInfo postLayoutInfo) {
        return false;
    }

    //需要执行动画时会系统会调用，用户无需手动调用
    @Override
    public void runPendingAnimations() {
        if (!mAnimationAddViewHolders.isEmpty()) {

            AnimatorSet animator;
            View target;
            for (final RecyclerView.ViewHolder viewHolder : mAnimationAddViewHolders) {
                target = viewHolder.itemView;
                animator = new AnimatorSet();

                animator.playTogether(
                        ObjectAnimator.ofFloat(target, "translationX", -target.getMeasuredWidth(), 0.0f),
                        ObjectAnimator.ofFloat(target, "alpha", target.getAlpha(), 1.0f)
                );

                animator.setTarget(target);
                animator.setDuration(100);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mAnimationAddViewHolders.remove(viewHolder);
                        if (!isRunning()) {
                            dispatchAnimationsFinished();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                animator.start();
            }
        } else if (!mAnimationRemoveViewHolders.isEmpty()) {
        }
    }


    @Override
    public void endAnimation(RecyclerView.ViewHolder viewHolder) {
    }

    @Override
    public void endAnimations() {
    }

    @Override
    public boolean isRunning() {
        return !(mAnimationAddViewHolders.isEmpty() && mAnimationRemoveViewHolders.isEmpty());
    }
}
