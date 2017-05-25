package com.ashokvarma.bottomnavigation;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;

/**
 * Class description : This class is derived from BadgeItem class.
 * (i.e data structure which holds all data to paint a badge and updates badges when changes are made)
 *
 * @author rahulpandey
 * @version 1.0
 * @since 31 Mar 2017
 */
public class ShapeBadgeItem {

    private int mBackgroundColorResource;
    private String mBackgroundColorCode;
    private int mBackgroundColor = Color.RED;
    public static int CIRCLE = 100;
    public static int SQUARE = 101;

    private int mTextColor = Color.WHITE;

    private int mBorderColorResource;
    private String mBorderColorCode;
    private int mBorderColor = Color.WHITE;

    private int mBorderWidth = 0;

    private int mGravity = Gravity.TOP | Gravity.END;
    private boolean mHideOnSelect;

    //private WeakReference<TextView> mTextViewRef;

    private boolean mIsHidden = false;

    private int mAnimationDuration = 200;

    private int mDimen;

    private Drawable mBadgeBackground;

    private int shape;

    private int[] marginArr;

    WeakReference<FrameLayout> mFrameLayout;


    ///////////////////////////////////////////////////////////////////////////
    // Public setter methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Sets background color resource.
     *
     * @param colorResource resource for background color
     * @return this, to allow builder pattern
     */
    public ShapeBadgeItem setBackgroundColorResource(@ColorRes int colorResource) {
        this.mBackgroundColorResource = colorResource;
        //refreshDrawable();
        return this;
    }


    public Drawable getBadgeBackground() {
        return this.mBadgeBackground;
    }

    /**
     * Sets border width.
     *
     * @param borderWidth border width in pixels
     * @return this, to allow builder pattern
     */
    public ShapeBadgeItem setBorderWidth(int borderWidth) {
        this.mBorderWidth = borderWidth;
        //refreshDrawable();
        return this;
    }

    /**
     * Sets gravity.
     *
     * @param gravity gravity of badge (TOP|LEFT ..etc)
     * @return this, to allow builder pattern
     */
    /*public ShapeBadgeItem setGravity(int gravity) {
        this.mGravity = gravity;
        if (isWeakReferenceValid()) {
            TextView textView = mTextViewRef.get();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.gravity = gravity;
            textView.setLayoutParams(layoutParams);
        }
        return this;
    }*/

    /**
     * Sets hide on select.
     *
     * @param hideOnSelect if true hides badge on tab selection
     * @return this, to allow builder pattern
     */
    public ShapeBadgeItem setHideOnSelect(boolean hideOnSelect) {
        this.mHideOnSelect = hideOnSelect;
        return this;
    }

    /**
     * Sets animation duration.
     *
     * @param animationDuration hide and show animation time
     * @return this, to allow builder pattern
     */
    public ShapeBadgeItem setAnimationDuration(int animationDuration) {
        this.mAnimationDuration = animationDuration;
        return this;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Library only access method
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Gets background color.
     *
     * @param context to fetch color
     * @return background color
     */
    protected int getBackgroundColor(Context context) {
        if (this.mBackgroundColorResource != 0) {
            return ContextCompat.getColor(context, mBackgroundColorResource);
        } else if (!TextUtils.isEmpty(mBackgroundColorCode)) {
            return Color.parseColor(mBackgroundColorCode);
        } else {
            return mBackgroundColor;
        }
    }


    /**
     * Gets border color.
     *
     * @param context to fetch color
     * @return border color
     */
    protected int getBorderColor(Context context) {
        if (this.mBorderColorResource != 0) {
            return ContextCompat.getColor(context, mBorderColorResource);
        } else if (!TextUtils.isEmpty(mBorderColorCode)) {
            return Color.parseColor(mBorderColorCode);
        } else {
            return mBorderColor;
        }
    }

    /**
     * Gets border width.
     *
     * @return border width
     */
    protected int getBorderWidth() {
        return mBorderWidth;
    }

    /**
     * Gets gravity.
     *
     * @return gravity of badge
     */
    protected int getGravity() {
        return mGravity;
    }

    /**
     * Is hide on select boolean.
     *
     * @return should hide on selection ?
     */
    protected boolean isHideOnSelect() {
        return mHideOnSelect;
    }

    /**
     * Gets text view.
     *
     * @return reference to text-view
     */
    /*protected WeakReference<TextView> getTextView() {
        return mTextViewRef;
    }*/


    ///////////////////////////////////////////////////////////////////////////
    // Internal Methods
    ///////////////////////////////////////////////////////////////////////////

    /*private void refreshDrawable() {
        if (isWeakReferenceValid()) {
            TextView textView = mTextViewRef.get();
            textView.setBackgroundDrawable(BottomNavigationHelper.getShapeBadgeDrawable(this, textView.getContext()));
        }
    }*/

    /*private void setTextColor() {
        if (isWeakReferenceValid()) {
            TextView textView = mTextViewRef.get();
            textView.setTextColor(getTextColor(textView.getContext()));
        }
    }*/
    private boolean isWeakReferenceValid() {
        return mFrameLayout != null && mFrameLayout.get() != null;
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
     * Toggle badge item.
     *
     * @return this, to allow builder pattern
     */
    public ShapeBadgeItem toggle() {
        return toggle(true);
    }

    /**
     * Toggle badge item.
     *
     * @param animate whether to animate the change
     * @return this, to allow builder pattern
     */
    public ShapeBadgeItem toggle(boolean animate) {
        if (mIsHidden) {
            return show(animate);
        } else {
            return hide(animate);
        }
    }

    /**
     * Show badge item.
     *
     * @return this, to allow builder pattern
     */
    public ShapeBadgeItem show() {
        return show(true);
    }

    /**
     * Show badge item.
     *
     * @param animate whether to animate the change
     * @return this, to allow builder pattern
     */
    public ShapeBadgeItem show(boolean animate) {
        mIsHidden = false;
        if (isWeakReferenceValid()) {
            FrameLayout frameLayout = mFrameLayout.get();
            if (animate) {
                frameLayout.setScaleX(0);
                frameLayout.setScaleY(0);
                frameLayout.setVisibility(View.VISIBLE);
                ViewPropertyAnimatorCompat animatorCompat = ViewCompat.animate(frameLayout);
                animatorCompat.cancel();
                animatorCompat.setDuration(mAnimationDuration);
                animatorCompat.scaleX(1).scaleY(1);
                animatorCompat.setListener(null);
                animatorCompat.start();
            } else {
                frameLayout.setScaleX(1);
                frameLayout.setScaleY(1);
                frameLayout.setVisibility(View.VISIBLE);
            }
        }
        return this;
    }

    /**
     * Hide badge item.
     *
     * @return this, to allow builder pattern
     */
    public ShapeBadgeItem hide() {
        return hide(true);
    }

    protected ShapeBadgeItem setFrameLayout(FrameLayout mFrameLayout) {
        this.mFrameLayout = new WeakReference<>(mFrameLayout);
        return this;
    }

    /**
     * Hide badge item.
     *
     * @param animate whether to animate the change
     * @return this, to allow builder pattern
     */
    public ShapeBadgeItem hide(boolean animate) {
        mIsHidden = true;
        if (isWeakReferenceValid()) {
            FrameLayout textView = mFrameLayout.get();
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
        return this;
    }

    /**
     * Is hidden boolean.
     *
     * @return if the badge is hidden
     */
    public boolean isHidden() {
        return mIsHidden;
    }

    /**
     * Sets radius of badge.
     *
     * @param radius the radius
     */
    public ShapeBadgeItem setDimen(int radius) {
        this.mDimen = radius;
        return this;
    }

    /**
     * Get radius int.
     *
     * @return the int
     */
    public int getDimen() {
        return this.mDimen;
    }

    public int getShape() {
        return shape;
    }

    public ShapeBadgeItem setShape(int shape) {
        this.shape = shape;
        return this;
    }

    public ShapeBadgeItem setMargins(int l, int t, int r, int b) {
        this.marginArr = new int[]{l, t, r, b};
        return this;
    }


    public int[] getMargins() {
        return marginArr;
    }
}
