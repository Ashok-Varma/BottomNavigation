package com.ashokvarma.bottomnavigation;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;

import java.lang.ref.WeakReference;

/**
 * Class description : Holds and manages data for badges
 * (i.e data structure which holds all data to paint a badge and updates badges when changes are made)
 *
 * @author ashokvarma
 * @version 1.0
 * @since 21 Apr 2016
 */
abstract class BadgeItem<T extends BadgeItem<T>> {

    private int mGravity = Gravity.TOP | Gravity.END;
    private boolean mHideOnSelect;

    private WeakReference<BadgeTextView> mTextViewRef;

    private boolean mIsHidden = false;

    private int mAnimationDuration = 200;

    /**
     * @return subClass to allow Builder pattern
     */
    abstract T getSubInstance();

    /**
     * if any extra binding is required binds all badgeItem, BottomNavigationTab and BadgeTextView
     *
     * @param bottomNavigationTab to which badgeItem needs to be attached
     */
    abstract void bindToBottomTabInternal(BottomNavigationTab bottomNavigationTab);

    ///////////////////////////////////////////////////////////////////////////
    // Public setter methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @param gravity gravity of badge (TOP|LEFT ..etc)
     * @return this, to allow builder pattern
     */
    public T setGravity(int gravity) {
        this.mGravity = gravity;
        if (isWeakReferenceValid()) {
            TextView textView = mTextViewRef.get();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.gravity = gravity;
            textView.setLayoutParams(layoutParams);
        }
        return getSubInstance();
    }

    /**
     * @param hideOnSelect if true hides badge on tab selection
     * @return this, to allow builder pattern
     */
    public T setHideOnSelect(boolean hideOnSelect) {
        this.mHideOnSelect = hideOnSelect;
        return getSubInstance();
    }

    /**
     * @param animationDuration hide and show animation time
     * @return this, to allow builder pattern
     */
    public T setAnimationDuration(int animationDuration) {
        this.mAnimationDuration = animationDuration;
        return getSubInstance();
    }


    ///////////////////////////////////////////////////////////////////////////
    // Library only access method
    ///////////////////////////////////////////////////////////////////////////

    /**
     * binds all badgeItem, BottomNavigationTab and BadgeTextView
     *
     * @param bottomNavigationTab to which badgeItem needs to be attached
     */
    void bindToBottomTab(BottomNavigationTab bottomNavigationTab) {
        // set initial bindings
        bottomNavigationTab.badgeView.clearPrevious();
        if (bottomNavigationTab.badgeItem != null) {
            // removing old reference
            bottomNavigationTab.badgeItem.setTextView(null);
        }
        bottomNavigationTab.setBadgeItem(this);
        setTextView(bottomNavigationTab.badgeView);

        // allow sub class to modify the things
        bindToBottomTabInternal(bottomNavigationTab);

        // make view visible because gone by default
        bottomNavigationTab.badgeView.setVisibility(View.VISIBLE);

        // set layout params based on gravity
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) bottomNavigationTab.badgeView.getLayoutParams();
        layoutParams.gravity = getGravity();
        bottomNavigationTab.badgeView.setLayoutParams(layoutParams);

        // if hidden hide
        if (isHidden()) {
            // if hide is called before the initialisation of bottom-bar this will handle that
            // by hiding it.
            hide();
        }
    }

    /**
     * Internal method used to update view when ever changes are made
     *
     * @param mTextView badge textView
     * @return this, to allow builder pattern
     */
    private T setTextView(BadgeTextView mTextView) {
        this.mTextViewRef = new WeakReference<>(mTextView);
        return getSubInstance();
    }

    /**
     * @return gravity of badge
     */
    int getGravity() {
        return mGravity;
    }

    /**
     * @return should hide on selection ?
     */
    boolean isHideOnSelect() {
        return mHideOnSelect;
    }

    /**
     * @return reference to text-view
     */
    WeakReference<BadgeTextView> getTextView() {
        return mTextViewRef;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal Methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @return returns if BadgeTextView's reference is valid
     */
    boolean isWeakReferenceValid() {
        return mTextViewRef != null && mTextViewRef.get() != null;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal call back methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * callback from bottom navigation tab when it is selected
     */
    void select() {
        if (mHideOnSelect) {
            hide(true);
        }
    }

    /**
     * callback from bottom navigation tab when it is un-selected
     */
    void unSelect() {
        if (mHideOnSelect) {
            show(true);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Public functionality methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @return this, to allow builder pattern
     */
    public T toggle() {
        return toggle(true);
    }

    /**
     * @param animate whether to animate the change
     * @return this, to allow builder pattern
     */
    public T toggle(boolean animate) {
        if (mIsHidden) {
            return show(animate);
        } else {
            return hide(animate);
        }
    }

    /**
     * @return this, to allow builder pattern
     */
    public T show() {
        return show(true);
    }

    /**
     * @param animate whether to animate the change
     * @return this, to allow builder pattern
     */
    public T show(boolean animate) {
        mIsHidden = false;
        if (isWeakReferenceValid()) {
            TextView textView = mTextViewRef.get();
            if (animate) {
                textView.setScaleX(0);
                textView.setScaleY(0);
                textView.setVisibility(View.VISIBLE);
                ViewPropertyAnimatorCompat animatorCompat = ViewCompat.animate(textView);
                animatorCompat.cancel();
                animatorCompat.setDuration(mAnimationDuration);
                animatorCompat.scaleX(1).scaleY(1);
                animatorCompat.setListener(null);
                animatorCompat.start();
            } else {
                textView.setScaleX(1);
                textView.setScaleY(1);
                textView.setVisibility(View.VISIBLE);
            }
        }
        return getSubInstance();
    }

    /**
     * @return this, to allow builder pattern
     */
    public T hide() {
        return hide(true);
    }

    /**
     * @param animate whether to animate the change
     * @return this, to allow builder pattern
     */
    public T hide(boolean animate) {
        mIsHidden = true;
        if (isWeakReferenceValid()) {
            TextView textView = mTextViewRef.get();
            if (animate) {
                ViewPropertyAnimatorCompat animatorCompat = ViewCompat.animate(textView);
                animatorCompat.cancel();
                animatorCompat.setDuration(mAnimationDuration);
                animatorCompat.scaleX(0).scaleY(0);
                animatorCompat.setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        // Empty body
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        view.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        view.setVisibility(View.GONE);
                    }
                });
                animatorCompat.start();
            } else {
                textView.setVisibility(View.GONE);
            }
        }
        return getSubInstance();
    }

    /**
     * @return if the badge is hidden
     */
    public boolean isHidden() {
        return mIsHidden;
    }
}
