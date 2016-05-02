package com.ashokvarma.bottomnavigation;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Class description : Holds and manages data for badges
 * (i.e data structure which holds all data to paint a badge and updates badges when changes are made)
 *
 * @author ashokvarma
 * @version 1.0
 * @since 21 Apr 2016
 */
public class BadgeItem {

    private int mBackgroundColorResource;
    private String mBackgroundColorCode;
    private int mBackgroundColor = Color.RED;

    private int mTextColorResource;
    private String mTextColorCode;
    private int mTextColor = Color.WHITE;

    private CharSequence mText;

    private int mBorderColorResource;
    private String mBorderColorCode;
    private int mBorderColor = Color.WHITE;

    private int mBorderWidth = 0;

    private int mGravity = Gravity.TOP | Gravity.END;
    private boolean mHideOnSelect;

    private WeakReference<TextView> mTextViewRef;

    private boolean mIsHidden = false;

    private int mAnimationDuration = 200;


    ///////////////////////////////////////////////////////////////////////////
    // Public setter methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @param colorResource resource for background color
     * @return this, to allow builder pattern
     */
    public BadgeItem setBackgroundColorResource(@ColorRes int colorResource) {
        this.mBackgroundColorResource = colorResource;
        refreshDrawable();
        return this;
    }

    /**
     * @param colorCode color code for background color
     * @return this, to allow builder pattern
     */
    public BadgeItem setBackgroundColor(@Nullable String colorCode) {
        this.mBackgroundColorCode = colorCode;
        refreshDrawable();
        return this;
    }

    /**
     * @param color background color
     * @return this, to allow builder pattern
     */
    public BadgeItem setBackgroundColor(int color) {
        this.mBackgroundColor = color;
        refreshDrawable();
        return this;
    }

    /**
     * @param colorResource resource for text color
     * @return this, to allow builder pattern
     */
    public BadgeItem setTextColorResource(@ColorRes int colorResource) {
        this.mTextColorResource = colorResource;
        setTextColor();
        return this;
    }

    /**
     * @param colorCode color code for text color
     * @return this, to allow builder pattern
     */
    public BadgeItem setTextColor(@Nullable String colorCode) {
        this.mTextColorCode = colorCode;
        setTextColor();
        return this;
    }

    /**
     * @param color text color
     * @return this, to allow builder pattern
     */
    public BadgeItem setTextColor(int color) {
        this.mTextColor = color;
        setTextColor();
        return this;
    }

    /**
     * @param text text to be set in badge (this shouldn't be empty text)
     * @return this, to allow builder pattern
     */
    public BadgeItem setText(@Nullable CharSequence text) {
        this.mText = text;
        if (isWeakReferenceValid()) {
            TextView textView = mTextViewRef.get();
            if (!TextUtils.isEmpty(text)) {
                textView.setText(text);
            }
        }
        return this;
    }

    /**
     * @param colorResource resource for border color
     * @return this, to allow builder pattern
     */
    public BadgeItem setBorderColorResource(@ColorRes int colorResource) {
        this.mBorderColorResource = colorResource;
        refreshDrawable();
        return this;
    }

    /**
     * @param colorCode color code for border color
     * @return this, to allow builder pattern
     */
    public BadgeItem setBorderColor(@Nullable String colorCode) {
        this.mBorderColorCode = colorCode;
        refreshDrawable();
        return this;
    }

    /**
     * @param color border color
     * @return this, to allow builder pattern
     */
    public BadgeItem setBorderColor(int color) {
        this.mBorderColor = color;
        refreshDrawable();
        return this;
    }

    /**
     * @param borderWidth border width in pixels
     * @return this, to allow builder pattern
     */
    public BadgeItem setBorderWidth(int borderWidth) {
        this.mBorderWidth = borderWidth;
        refreshDrawable();
        return this;
    }

    /**
     * @param gravity gravity of badge (TOP|LEFT ..etc)
     * @return this, to allow builder pattern
     */
    public BadgeItem setGravity(int gravity) {
        this.mGravity = gravity;
        if (isWeakReferenceValid()) {
            TextView textView = mTextViewRef.get();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.gravity = gravity;
            textView.setLayoutParams(layoutParams);
        }
        return this;
    }

    /**
     * @param hideOnSelect if true hides badge on tab selection
     * @return this, to allow builder pattern
     */
    public BadgeItem setHideOnSelect(boolean hideOnSelect) {
        this.mHideOnSelect = hideOnSelect;
        return this;
    }

    /**
     * @param animationDuration hide and show animation time
     * @return this, to allow builder pattern
     */
    public BadgeItem setAnimationDuration(int animationDuration) {
        this.mAnimationDuration = animationDuration;
        return this;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Library only access method
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Internal method used to update view when ever changes are made
     *
     * @param mTextView badge textView
     * @return this, to allow builder pattern
     */
    protected BadgeItem setTextView(TextView mTextView) {
        this.mTextViewRef = new WeakReference<>(mTextView);
        return this;
    }

    /**
     * @param context to fetch color
     * @return background color
     */
    protected int getBackgroundColor(Context context) {
        if (this.mBackgroundColorResource != 0) {
            return context.getResources().getColor(mBackgroundColorResource);
        } else if (!TextUtils.isEmpty(mBackgroundColorCode)) {
            return Color.parseColor(mBackgroundColorCode);
        } else {
            return mBackgroundColor;
        }
    }

    /**
     * @param context to fetch color
     * @return text color
     */
    protected int getTextColor(Context context) {
        if (this.mTextColorResource != 0) {
            return context.getResources().getColor(mTextColorResource);
        } else if (!TextUtils.isEmpty(mTextColorCode)) {
            return Color.parseColor(mTextColorCode);
        } else {
            return mTextColor;
        }
    }

    /**
     * @return text that needs to be set in badge
     */
    protected CharSequence getText() {
        return mText;
    }

    /**
     * @param context to fetch color
     * @return border color
     */
    protected int getBorderColor(Context context) {
        if (this.mBorderColorResource != 0) {
            return context.getResources().getColor(mBorderColorResource);
        } else if (!TextUtils.isEmpty(mBorderColorCode)) {
            return Color.parseColor(mBorderColorCode);
        } else {
            return mBorderColor;
        }
    }

    /**
     * @return border width
     */
    protected int getBorderWidth() {
        return mBorderWidth;
    }

    /**
     * @return gravity of badge
     */
    protected int getGravity() {
        return mGravity;
    }

    /**
     * @return should hide on selection ?
     */
    protected boolean isHideOnSelect() {
        return mHideOnSelect;
    }

    /**
     * @return reference to text-view
     */
    protected WeakReference<TextView> getTextView() {
        return mTextViewRef;
    }


    ///////////////////////////////////////////////////////////////////////////
    // Internal Methods
    ///////////////////////////////////////////////////////////////////////////

    private void refreshDrawable() {
        if (isWeakReferenceValid()) {
            TextView textView = mTextViewRef.get();
            textView.setBackgroundDrawable(BottomNavigationHelper.getBadgeDrawable(this, textView.getContext()));
        }
    }

    private void setTextColor() {
        if (isWeakReferenceValid()) {
            TextView textView = mTextViewRef.get();
            textView.setTextColor(getTextColor(textView.getContext()));
        }
    }

    private boolean isWeakReferenceValid() {
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
    public BadgeItem toggle() {
        return toggle(true);
    }

    /**
     * @param animate whether to animate the change
     * @return this, to allow builder pattern
     */
    public BadgeItem toggle(boolean animate) {
        if (mIsHidden) {
            return show(animate);
        } else {
            return hide(animate);
        }
    }

    /**
     * @return this, to allow builder pattern
     */
    public BadgeItem show() {
        return show(true);
    }

    /**
     * @param animate whether to animate the change
     * @return this, to allow builder pattern
     */
    public BadgeItem show(boolean animate) {
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
        return this;
    }

    /**
     * @return this, to allow builder pattern
     */
    public BadgeItem hide() {
        return hide(true);
    }

    /**
     * @param animate whether to animate the change
     * @return this, to allow builder pattern
     */
    public BadgeItem hide(boolean animate) {
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
        return this;
    }

    /**
     * @return if the badge is hidden
     */
    public boolean isHidden() {
        return mIsHidden;
    }
}
