package com.ashokvarma.bottomnavigation.behaviour;


import android.content.Context;
import android.support.annotation.IntDef;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Class description
 *
 * @author ashokvarma
 * @version 1.0
 * @since 25 Mar 2016
 */
public abstract class VerticalScrollingBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    private int mTotalDyUnconsumed = 0;
    private int mTotalDy = 0;

    @ScrollDirection
    private int mOverScrollDirection = ScrollDirection.SCROLL_NONE;
    @ScrollDirection
    private int mScrollDirection = ScrollDirection.SCROLL_NONE;

    public VerticalScrollingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalScrollingBehavior() {
        super();
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ScrollDirection.SCROLL_DIRECTION_UP, ScrollDirection.SCROLL_DIRECTION_DOWN})
    public @interface ScrollDirection {
        int SCROLL_DIRECTION_UP = 1;
        int SCROLL_DIRECTION_DOWN = -1;
        int SCROLL_NONE = 0;
    }


    /*
       @return Overscroll direction: SCROLL_DIRECTION_UP, CROLL_DIRECTION_DOWN, SCROLL_NONE
   */
    @ScrollDirection
    public int getOverScrollDirection() {
        return mOverScrollDirection;
    }


    /**
     * @return Scroll direction: SCROLL_DIRECTION_UP, SCROLL_DIRECTION_DOWN, SCROLL_NONE
     */

    @ScrollDirection
    public int getScrollDirection() {
        return mScrollDirection;
    }


    /**
     * @param coordinatorLayout the CoordinatorLayout parent of the view this Behavior is
     *                          associated with
     * @param child             the child view of the CoordinatorLayout this Behavior is associated with
     * @param scrollDirection         Direction of the scroll: SCROLL_DIRECTION_UP, SCROLL_DIRECTION_DOWN
     * @param currentOverScroll Unconsumed value, negative or positive based on the direction;
     * @param totalOverScroll   Cumulative value for current direction
     */
    public abstract void onNestedVerticalOverScroll(CoordinatorLayout coordinatorLayout, V child, @ScrollDirection int scrollDirection, int currentOverScroll, int totalOverScroll);

    /**
     * @param coordinatorLayout the CoordinatorLayout parent of the view this Behavior is
     *                          associated with
     * @param child             the child view of the CoordinatorLayout this Behavior is associated with
     * @param target            the descendant view of the CoordinatorLayout performing the nested scroll
     * @param dx                the raw horizontal number of pixels that the user attempted to scroll
     * @param dy                the raw vertical number of pixels that the user attempted to scroll
     * @param consumed          out parameter. consumed[0] should be set to the distance of dx that
     *                          was consumed, consumed[1] should be set to the distance of dy that
     *                          was consumed
     * @param scrollDirection   Direction of the scroll: SCROLL_DIRECTION_UP, SCROLL_DIRECTION_DOWN
     */
    public abstract void onDirectionNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed, @ScrollDirection int scrollDirection);

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & View.SCROLL_AXIS_VERTICAL) != 0;
    }

//    @Override
//    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, V child, View directTargetChild, View target, int nestedScrollAxes) {
//        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
//    }
//
//    @Override
//    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target) {
//        super.onStopNestedScroll(coordinatorLayout, child, target);
//    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyUnconsumed > 0 && mTotalDyUnconsumed < 0) {
            mTotalDyUnconsumed = 0;
            mOverScrollDirection = ScrollDirection.SCROLL_DIRECTION_UP;
        } else if (dyUnconsumed < 0 && mTotalDyUnconsumed > 0) {
            mTotalDyUnconsumed = 0;
            mOverScrollDirection = ScrollDirection.SCROLL_DIRECTION_DOWN;
        }
        mTotalDyUnconsumed += dyUnconsumed;
        onNestedVerticalOverScroll(coordinatorLayout, child, mOverScrollDirection, dyConsumed, mTotalDyUnconsumed);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        if (dy > 0 && mTotalDy < 0) {
            mTotalDy = 0;
            mScrollDirection = ScrollDirection.SCROLL_DIRECTION_UP;
        } else if (dy < 0 && mTotalDy > 0) {
            mTotalDy = 0;
            mScrollDirection = ScrollDirection.SCROLL_DIRECTION_DOWN;
        }
        mTotalDy += dy;
        onDirectionNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, mScrollDirection);
    }


    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY, boolean consumed) {
        super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
        mScrollDirection = velocityY > 0 ? ScrollDirection.SCROLL_DIRECTION_UP : ScrollDirection.SCROLL_DIRECTION_DOWN;
        return onNestedDirectionFling(coordinatorLayout, child, target, velocityX, velocityY, mScrollDirection);
    }

    /**
     * @param coordinatorLayout the CoordinatorLayout parent of the view this Behavior is
     *                          associated with
     * @param child             the child view of the CoordinatorLayout this Behavior is associated with
     * @param target            the descendant view of the CoordinatorLayout performing the nested scroll
     * @param velocityX         horizontal velocity of the attempted fling
     * @param velocityY         vertical velocity of the attempted fling
     * @param scrollDirection   Direction of the scroll: SCROLL_DIRECTION_UP, SCROLL_DIRECTION_DOWN
     * @return
     */
    protected abstract boolean onNestedDirectionFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY, @ScrollDirection int scrollDirection);

//    @Override
//    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY) {
//        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
//    }
//
//    @Override
//    public WindowInsetsCompat onApplyWindowInsets(CoordinatorLayout coordinatorLayout, V child, WindowInsetsCompat insets) {
//
//        return super.onApplyWindowInsets(coordinatorLayout, child, insets);
//    }
//
//    @Override
//    public Parcelable onSaveInstanceState(CoordinatorLayout parent, V child) {
//        return super.onSaveInstanceState(parent, child);
//    }

}
