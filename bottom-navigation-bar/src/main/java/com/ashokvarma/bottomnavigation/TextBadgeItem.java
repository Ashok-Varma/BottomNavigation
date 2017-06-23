package com.ashokvarma.bottomnavigation;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.TextView;

/**
 * Class description
 *
 * @author ashokvarma
 * @version 1.0
 * @see
 * @since 23 Jun 2017
 */
public class TextBadgeItem extends BadgeItem<TextBadgeItem> {

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

    @Override
    TextBadgeItem getSubInstance() {
        return this;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Public setter methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @param colorResource resource for background color
     * @return this, to allow builder pattern
     */
    public TextBadgeItem setBackgroundColorResource(@ColorRes int colorResource) {
        this.mBackgroundColorResource = colorResource;
        refreshDrawable();
        return this;
    }

    /**
     * @param colorCode color code for background color
     * @return this, to allow builder pattern
     */
    public TextBadgeItem setBackgroundColor(@Nullable String colorCode) {
        this.mBackgroundColorCode = colorCode;
        refreshDrawable();
        return this;
    }

    /**
     * @param color background color
     * @return this, to allow builder pattern
     */
    public TextBadgeItem setBackgroundColor(int color) {
        this.mBackgroundColor = color;
        refreshDrawable();
        return this;
    }

    /**
     * @param colorResource resource for text color
     * @return this, to allow builder pattern
     */
    public TextBadgeItem setTextColorResource(@ColorRes int colorResource) {
        this.mTextColorResource = colorResource;
        setTextColor();
        return this;
    }

    /**
     * @param colorCode color code for text color
     * @return this, to allow builder pattern
     */
    public TextBadgeItem setTextColor(@Nullable String colorCode) {
        this.mTextColorCode = colorCode;
        setTextColor();
        return this;
    }

    /**
     * @param color text color
     * @return this, to allow builder pattern
     */
    public TextBadgeItem setTextColor(int color) {
        this.mTextColor = color;
        setTextColor();
        return this;
    }

    /**
     * @param text text to be set in badge (this shouldn't be empty text)
     * @return this, to allow builder pattern
     */
    public TextBadgeItem setText(@Nullable CharSequence text) {
        this.mText = text;
        if (isWeakReferenceValid()) {
            TextView textView = getTextView().get();
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
    public TextBadgeItem setBorderColorResource(@ColorRes int colorResource) {
        this.mBorderColorResource = colorResource;
        refreshDrawable();
        return this;
    }

    /**
     * @param colorCode color code for border color
     * @return this, to allow builder pattern
     */
    public TextBadgeItem setBorderColor(@Nullable String colorCode) {
        this.mBorderColorCode = colorCode;
        refreshDrawable();
        return this;
    }

    /**
     * @param color border color
     * @return this, to allow builder pattern
     */
    public TextBadgeItem setBorderColor(int color) {
        this.mBorderColor = color;
        refreshDrawable();
        return this;
    }

    /**
     * @param borderWidth border width in pixels
     * @return this, to allow builder pattern
     */
    public TextBadgeItem setBorderWidth(int borderWidth) {
        this.mBorderWidth = borderWidth;
        refreshDrawable();
        return this;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Library only access method
    ///////////////////////////////////////////////////////////////////////////


    @Override
    void bindToBottomTabInternal(TextBadgeItem badgeItem, BottomNavigationTab bottomNavigationTab) {
        Context context = bottomNavigationTab.getContext();

        GradientDrawable shape = badgeItem.getBadgeDrawable(context);
        bottomNavigationTab.badgeView.setBackgroundDrawable(shape);
        bottomNavigationTab.badgeView.setTextColor(badgeItem.getTextColor(context));
        bottomNavigationTab.badgeView.setText(badgeItem.getText());
    }

    ///////////////////////////////////////////////////////////////////////////
    // Class only access methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @param context to fetch color
     * @return background color
     */
    private int getBackgroundColor(Context context) {
        if (this.mBackgroundColorResource != 0) {
            return ContextCompat.getColor(context, mBackgroundColorResource);
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
    private int getTextColor(Context context) {
        if (this.mTextColorResource != 0) {
            return ContextCompat.getColor(context, mTextColorResource);
        } else if (!TextUtils.isEmpty(mTextColorCode)) {
            return Color.parseColor(mTextColorCode);
        } else {
            return mTextColor;
        }
    }

    /**
     * @return text that needs to be set in badge
     */
    private CharSequence getText() {
        return mText;
    }

    /**
     * @param context to fetch color
     * @return border color
     */
    private int getBorderColor(Context context) {
        if (this.mBorderColorResource != 0) {
            return ContextCompat.getColor(context, mBorderColorResource);
        } else if (!TextUtils.isEmpty(mBorderColorCode)) {
            return Color.parseColor(mBorderColorCode);
        } else {
            return mBorderColor;
        }
    }

    /**
     * @return border width
     */
    private int getBorderWidth() {
        return mBorderWidth;
    }


    ///////////////////////////////////////////////////////////////////////////
    // Internal Methods
    ///////////////////////////////////////////////////////////////////////////

    private void refreshDrawable() {
        if (isWeakReferenceValid()) {
            TextView textView = getTextView().get();
            textView.setBackgroundDrawable(getBadgeDrawable(textView.getContext()));
        }
    }

    private void setTextColor() {
        if (isWeakReferenceValid()) {
            TextView textView = getTextView().get();
            textView.setTextColor(getTextColor(textView.getContext()));
        }
    }

    /**
     * @param context to fetch color
     * @return return the background drawable
     */
    private GradientDrawable getBadgeDrawable(Context context) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(context.getResources().getDimensionPixelSize(R.dimen.badge_corner_radius));
        shape.setColor(getBackgroundColor(context));
        shape.setStroke(getBorderWidth(), getBorderColor(context));
        return shape;
    }
}