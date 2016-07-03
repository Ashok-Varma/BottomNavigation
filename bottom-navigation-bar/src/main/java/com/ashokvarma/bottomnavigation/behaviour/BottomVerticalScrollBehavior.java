package com.ashokvarma.bottomnavigation.behaviour;


import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.Interpolator;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Class description
 *
 * @author ashokvarma
 * @version 1.0
 * @see VerticalScrollingBehavior
 * @since 25 Mar 2016
 */
public class BottomVerticalScrollBehavior<V extends View> extends VerticalScrollingBehavior<V> {
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private int mBottomNavHeight;
    private WeakReference<BottomNavigationBar> mViewRef;

    ///////////////////////////////////////////////////////////////////////////
    // onBottomBar changes
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, final V child, int layoutDirection) {
        // First let the parent lay it out
        parent.onLayoutChild(child, layoutDirection);
        if (child instanceof BottomNavigationBar) {
            mViewRef = new WeakReference<>((BottomNavigationBar) child);
        }

        child.post(new Runnable() {
            @Override
            public void run() {
                mBottomNavHeight = child.getHeight();
            }
        });
        updateSnackBarPosition(parent, child, getSnackBarInstance(parent, child));

        return super.onLayoutChild(parent, child, layoutDirection);
    }

    ///////////////////////////////////////////////////////////////////////////
    // SnackBar Handling
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, V child, View dependency) {
        return isDependent(dependency) || super.layoutDependsOn(parent, child, dependency);
    }

    private boolean isDependent(View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, V child, View dependency) {
        if (isDependent(dependency)) {
            updateSnackBarPosition(parent, child, dependency);
            return false;
        }

        return super.onDependentViewChanged(parent, child, dependency);
    }

    private void updateSnackBarPosition(CoordinatorLayout parent, V child, View dependency) {
        updateSnackBarPosition(parent, child, dependency, ViewCompat.getTranslationY(child) - child.getHeight());
    }

    private void updateSnackBarPosition(CoordinatorLayout parent, V child, View dependency, float translationY) {
        if (dependency != null && dependency instanceof Snackbar.SnackbarLayout) {
            ViewCompat.animate(dependency).setInterpolator(INTERPOLATOR).setDuration(80).setStartDelay(0).translationY(translationY).start();
        }
    }

    private Snackbar.SnackbarLayout getSnackBarInstance(CoordinatorLayout parent, V child) {
        final List<View> dependencies = parent.getDependencies(child);
        for (int i = 0, z = dependencies.size(); i < z; i++) {
            final View view = dependencies.get(i);
            if (view instanceof Snackbar.SnackbarLayout) {
                return (Snackbar.SnackbarLayout) view;
            }
        }
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Auto Hide Handling
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onNestedVerticalScrollUnconsumed(CoordinatorLayout coordinatorLayout, V child, @ScrollDirection int scrollDirection, int currentOverScroll, int totalScroll) {
        // Empty body
    }

    @Override
    public void onNestedVerticalPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed, @ScrollDirection int scrollDirection) {
//        handleDirection(child, scrollDirection);
    }

    @Override
    protected boolean onNestedDirectionFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY, boolean consumed, @ScrollDirection int scrollDirection) {
//        if (consumed) {
//            handleDirection(child, scrollDirection);
//        }
        return consumed;
    }

    @Override
    public void onNestedVerticalScrollConsumed(CoordinatorLayout coordinatorLayout, V child, @ScrollDirection int scrollDirection, int currentOverScroll, int totalConsumedScroll) {
        handleDirection(coordinatorLayout, child, scrollDirection);
    }

    private void handleDirection(CoordinatorLayout parent, V child, int scrollDirection) {
        BottomNavigationBar bottomNavigationBar = mViewRef.get();
        if (bottomNavigationBar != null && bottomNavigationBar.isAutoHideEnabled()) {
            if (scrollDirection == ScrollDirection.SCROLL_DIRECTION_DOWN && bottomNavigationBar.isHidden()) {
                updateSnackBarPosition(parent, child, getSnackBarInstance(parent, child), -mBottomNavHeight);
                bottomNavigationBar.show();
            } else if (scrollDirection == ScrollDirection.SCROLL_DIRECTION_UP && !bottomNavigationBar.isHidden()) {
                updateSnackBarPosition(parent, child, getSnackBarInstance(parent, child), 0);
                bottomNavigationBar.hide();
            }
        }
    }
}
