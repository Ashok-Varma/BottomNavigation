package com.ashokvarma.bottomnavigation;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Class description
 *
 * @author ashokvarma
 * @version 1.0
 * @see BottomNavigationTab
 * @since 19 Mar 2016
 */
class ShiftingBottomNavigationTab extends BottomNavigationTab {

    public ShiftingBottomNavigationTab(Context context) {
        super(context);
    }

    public ShiftingBottomNavigationTab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShiftingBottomNavigationTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ShiftingBottomNavigationTab(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    void init() {
        paddingTopActive = (int) getResources().getDimension(R.dimen.shifting_height_top_padding_active);
        paddingTopInActive = (int) getResources().getDimension(R.dimen.shifting_height_top_padding_inactive);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.shifting_bottom_navigation_item, this, true);
        containerView = view.findViewById(R.id.shifting_bottom_navigation_container);
        labelView = (TextView) view.findViewById(R.id.shifting_bottom_navigation_title);
        iconView = (ImageView) view.findViewById(R.id.shifting_bottom_navigation_icon);
        badgeView = (TextView) view.findViewById(R.id.shifting_bottom_navigation_badge);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            labelView.setScaleX(0);
            labelView.setScaleY(0);
        } else {
            labelView.setVisibility(View.GONE);
        }

        super.init();
    }

    @Override
    public void select(boolean setActiveColor, int animationDuration) {
        ResizeWidthAnimation anim = new ResizeWidthAnimation(this, mActiveWidth);
        anim.setDuration(animationDuration);
        this.startAnimation(anim);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            labelView.animate().scaleY(1).scaleX(1).setDuration(animationDuration);
        } else {
            labelView.setVisibility(View.VISIBLE);
        }

        super.select(setActiveColor, animationDuration);
    }

    @Override
    public void unSelect(boolean setActiveColor, int animationDuration) {
        ResizeWidthAnimation anim = new ResizeWidthAnimation(this, mInActiveWidth);
        anim.setDuration(animationDuration);
        this.startAnimation(anim);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            labelView.animate().scaleY(0).scaleX(0).setDuration(0);
        } else {
            labelView.setVisibility(View.GONE);
        }

        super.unSelect(setActiveColor, animationDuration);
    }

//    @Override
//    public void initialise(boolean setActiveColor) {
//        super.initialise(setActiveColor);
//    }

    public class ResizeWidthAnimation extends Animation {
        private int mWidth;
        private int mStartWidth;
        private View mView;

        public ResizeWidthAnimation(View view, int width) {
            mView = view;
            mWidth = width;
            mStartWidth = view.getWidth();
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            mView.getLayoutParams().width = mStartWidth + (int) ((mWidth - mStartWidth) * interpolatedTime);
            mView.requestLayout();
        }

//        @Override
//        public void initialize(int width, int height, int parentWidth, int parentHeight) {
//            super.initialize(width, height, parentWidth, parentHeight);
//        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }

}
