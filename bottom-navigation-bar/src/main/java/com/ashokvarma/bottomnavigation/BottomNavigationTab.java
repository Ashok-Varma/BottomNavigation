package com.ashokvarma.bottomnavigation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    private static final int ANIMATION_DURATION = 400;

    protected int paddingTopActive;
    protected int paddingTopInActive;

    protected int mPosition;
    protected int mActiveColor;
    protected int mInActiveColor;
    protected int mBackgroundColor;
    protected int mActiveWidth;
    protected int mInActiveWidth;
    protected Drawable mCompactIcon;
    protected String mLabel;

    boolean isActive = false;

    View containerView;
    TextView labelView;
    RelativeLayout badgeContainer;
    TextView badgeView;
    ImageView iconView;

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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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

    public void setLabel(String label) {
        mLabel = label;
        labelView.setText(label);
    }

    public void setBadge(String count) {

        if (count != null) {

            if (count.equalsIgnoreCase("0")) {
                //hide the badge
                ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(badgeContainer, "scaleX", 1f, 1.1f, 0f);
                ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(badgeContainer, "scaleY", 1f, 1.1f, 0f);
                scaleDownX.setDuration(ANIMATION_DURATION);
                scaleDownY.setDuration(ANIMATION_DURATION);

                AnimatorSet scaleDown = new AnimatorSet();
                scaleDown.play(scaleDownX).with(scaleDownY);

                scaleDown.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        badgeContainer.setVisibility(GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

                scaleDown.start();
            } else {
                //show the badge
                badgeContainer.setVisibility(VISIBLE);
                badgeContainer.setScaleY(0f);
                badgeContainer.setScaleX(0f);

                badgeView.setText(count);

                //bouncy effect, over scale to 1.1 then down scale to 0.9 and stabilize at 1f
                ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(badgeContainer, "scaleX", 0f, 1.1f, 0.9f, 1f);
                ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(badgeContainer, "scaleY", 0f, 1.1f, 0.9f, 1f);
                scaleUpX.setDuration(ANIMATION_DURATION);
                scaleUpY.setDuration(ANIMATION_DURATION);

                AnimatorSet scaleUp = new AnimatorSet();
                scaleUp.play(scaleUpX).with(scaleUpY);
                scaleUp.start();

                badgeView.setText(count);
            }
        }
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

    public int getPosition() {
        return mPosition;
    }

    public void setBadgeBackground(int color) {
        ((GradientDrawable) badgeContainer.getBackground()).setColor(color);
    }

    public void setBadgeDrawable(int drawable) {
        badgeContainer.setBackgroundResource(drawable);
    }

    public void setBadgeTextColor(int color) {
        badgeView.setTextColor(color);
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
    }


    public void initialise(boolean setActiveColor) {
        iconView.setSelected(false);
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
