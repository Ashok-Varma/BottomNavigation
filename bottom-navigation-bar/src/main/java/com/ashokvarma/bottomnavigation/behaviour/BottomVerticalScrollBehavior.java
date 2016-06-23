package com.ashokvarma.bottomnavigation.behaviour;


import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Class description
 *
 * @author ashokvarma
 * @version 1.0
 * @see VerticalScrollingBehavior
 * @since 25 Mar 2016
 */
public class BottomVerticalScrollBehavior<V extends View> extends VerticalScrollingBehavior<V> {
    private static final Interpolator INTERPOLATOR = new LinearOutSlowInInterpolator();
    private int mBottomNavHeight;
    private int mDefaultOffset;
//    private WeakReference<V> mViewRef;

    private ViewPropertyAnimatorCompat mTranslationAnimator;

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, final V child, int layoutDirection) {
        // First let the parent lay it out
        parent.onLayoutChild(child, layoutDirection);
//        mViewRef = new WeakReference<>(child);

        child.post(new Runnable() {
            @Override
            public void run() {
                mBottomNavHeight = child.getHeight();
            }
        });

        mDefaultOffset = 0;

        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public void onNestedVerticalScrollUnconsumed(CoordinatorLayout coordinatorLayout, V child, @ScrollDirection int scrollDirection, int currentOverScroll, int totalScroll) {
        // Empty body
    }

    @Override
    public void onNestedVerticalPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed, @ScrollDirection int scrollDirection) {
//        handleDirection(child, scrollDirection);
    }

    @Override
    public void onNestedVerticalScrollConsumed(CoordinatorLayout coordinatorLayout, V child, @ScrollDirection int scrollDirection, int currentOverScroll, int totalConsumedScroll) {
        handleDirection(child, scrollDirection);
    }

    private void handleDirection(V child, int scrollDirection) {
        float yOffset = child.getTranslationY();
        boolean hidden = yOffset > mDefaultOffset;
        
        if (scrollDirection == ScrollDirection.SCROLL_DIRECTION_DOWN && hidden) {
            animateOffset(child, mDefaultOffset);
        } else if (scrollDirection == ScrollDirection.SCROLL_DIRECTION_UP && !hidden) {
            animateOffset(child, mBottomNavHeight + mDefaultOffset);
        }
    }

    @Override
    protected boolean onNestedDirectionFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY, boolean consumed, @ScrollDirection int scrollDirection) {
        if (consumed) {
            handleDirection(child, scrollDirection);
        }
        return consumed;
    }

    private void animateOffset(final V child, final int offset) {
        ensureOrCancelAnimator(child);
        mTranslationAnimator.translationY(offset).start();
    }

    private void ensureOrCancelAnimator(V child) {
        if (mTranslationAnimator == null) {
            mTranslationAnimator = ViewCompat.animate(child);
            mTranslationAnimator.setDuration(400);
            mTranslationAnimator.setInterpolator(INTERPOLATOR);
        } else {
            mTranslationAnimator.cancel();
        }
    }
}
