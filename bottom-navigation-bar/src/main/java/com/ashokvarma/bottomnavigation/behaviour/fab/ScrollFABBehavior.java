package com.ashokvarma.bottomnavigation.behaviour.fab;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Created by ibrishkoski on 4/13/16.
 */
public class ScrollFABBehavior extends FloatingActionButton.Behavior {
    private static final Interpolator INTERPOLATOR = new LinearOutSlowInInterpolator();

    private ViewPropertyAnimatorCompat mTranslationAnimator;
    private int mBottomNavHeight;
    private int mDefaultOffset;

    public ScrollFABBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, final FloatingActionButton child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);

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
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout,
                                       final FloatingActionButton child,
                                       final View directTargetChild, final View target, final int nestedScrollAxes) {
        // Ensure we react to vertical scrolling
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child,
                directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(final CoordinatorLayout coordinatorLayout,
                               final FloatingActionButton child,
                               final View target, final int dxConsumed, final int dyConsumed,
                               final int dxUnconsumed, final int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0) {
            // User scrolled down and the FAB is currently visible -> hide the FAB
            animateOffset(child, mDefaultOffset + mBottomNavHeight);
        } else if (dyConsumed < 0) {
            // User scrolled up and the FAB is currently not visible -> show the FAB
            animateOffset(child, mDefaultOffset);
        }
    }

    private void animateOffset(final View child, final int offset) {
        ensureOrCancelAnimator(child);
        mTranslationAnimator.translationY(offset).start();
    }

    private void ensureOrCancelAnimator(View child) {
        if (mTranslationAnimator == null) {
            mTranslationAnimator = ViewCompat.animate(child);
            mTranslationAnimator.setDuration(400);
            mTranslationAnimator.setInterpolator(INTERPOLATOR);
        } else {
            mTranslationAnimator.cancel();
        }
    }
}