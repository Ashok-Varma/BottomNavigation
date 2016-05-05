package com.ashokvarma.bottomnavigation;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Class description
 *
 * @author ashokvarma
 * @version 1.0
 * @see FrameLayout
 * @since 19 Mar 2016
 */
class BottomNavigationTab extends FrameLayout {


    protected int paddingTopActive;
    protected int paddingTopInActive;

    protected int mPosition;
    protected int mActiveColor;
    protected int mInActiveColor;
    protected int mBackgroundColor;
    protected int mActiveWidth;
    protected int mInActiveWidth;

    protected Drawable mCompactIcon;
    protected Drawable mCompactInActiveIcon;
    protected boolean isInActiveIconSet = false;
    protected String mLabel;

    protected BadgeItem badgeItem;

    boolean isActive = false;

    View containerView;
    TextView labelView;
    ImageView iconView;
    TextView badgeView;

    public BottomNavigationTab(Context context) {
        super(context);
        init();
    }

    public BottomNavigationTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BottomNavigationTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BottomNavigationTab(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setActiveWidth(int activeWidth) {
        mActiveWidth = activeWidth;
    }

    public void setInactiveWidth(int inactiveWidth) {
        mInActiveWidth = inactiveWidth;
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = mInActiveWidth;
        setLayoutParams(params);
    }

    public void setIcon(Drawable icon) {
        mCompactIcon = DrawableCompat.wrap(icon);
    }

    public void setInactiveIcon(Drawable icon) {
        mCompactInActiveIcon = DrawableCompat.wrap(icon);
        isInActiveIconSet = true;
    }

    public void setLabel(String label) {
        mLabel = label;
        labelView.setText(label);
    }

    public void setActiveColor(int activeColor) {
        mActiveColor = activeColor;
    }

    public int getActiveColor() {
        return mActiveColor;
    }

    public void setInactiveColor(int inActiveColor) {
        mInActiveColor = inActiveColor;
        labelView.setTextColor(inActiveColor);
    }

    public void setItemBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public void setBadgeItem(BadgeItem badgeItem) {
        this.badgeItem = badgeItem;
    }

    public int getPosition() {
        return mPosition;
    }

    public void select(boolean setActiveColor, int animationDuration) {
        isActive = true;

        ValueAnimator animator = ValueAnimator.ofInt(containerView.getPaddingTop(), paddingTopActive);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                containerView.setPadding(containerView.getPaddingLeft(),
                        (Integer) valueAnimator.getAnimatedValue(),
                        containerView.getPaddingRight(),
                        containerView.getPaddingBottom());
            }
        });
        animator.setDuration(animationDuration);
        animator.start();

        iconView.setSelected(true);
        if (setActiveColor) {
            labelView.setTextColor(mActiveColor);
        } else {
            labelView.setTextColor(mBackgroundColor);
        }

        if (badgeItem != null) {
            badgeItem.select();
        }
    }

    public void unSelect(boolean setActiveColor, int animationDuration) {
        isActive = false;

        ValueAnimator animator = ValueAnimator.ofInt(containerView.getPaddingTop(), paddingTopInActive);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                containerView.setPadding(containerView.getPaddingLeft(),
                        (Integer) valueAnimator.getAnimatedValue(),
                        containerView.getPaddingRight(),
                        containerView.getPaddingBottom());
            }
        });
        animator.setDuration(animationDuration);
        animator.start();

        labelView.setTextColor(mInActiveColor);
        iconView.setSelected(false);

        if (badgeItem != null) {
            badgeItem.unSelect();
        }
    }

    public void initialise(boolean setActiveColor) {
        iconView.setSelected(false);
        if (isInActiveIconSet) {
            StateListDrawable states = new StateListDrawable();
            states.addState(new int[]{android.R.attr.state_selected},
                    mCompactIcon);
            states.addState(new int[]{-android.R.attr.state_selected},
                    mCompactInActiveIcon);
            states.addState(new int[]{},
                    mCompactInActiveIcon);
            iconView.setImageDrawable(states);
        } else {
            if (setActiveColor) {
                DrawableCompat.setTintList(mCompactIcon, new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_selected}, //1
                                new int[]{-android.R.attr.state_selected}, //2
                                new int[]{}
                        },
                        new int[]{
                                mActiveColor, //1
                                mInActiveColor, //2
                                mInActiveColor //3
                        }
                ));
            } else {
                DrawableCompat.setTintList(mCompactIcon, new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_selected}, //1
                                new int[]{-android.R.attr.state_selected}, //2
                                new int[]{}
                        },
                        new int[]{
                                mBackgroundColor, //1
                                mInActiveColor, //2
                                mInActiveColor //3
                        }
                ));
            }
            iconView.setImageDrawable(mCompactIcon);
        }
    }
}
