package com.ashokvarma.bottomnavigation;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Class description
 *
 * @author ashokvarma
 * @version 1.0
 * @see BottomNavigationTab
 * @since 19 Mar 2016
 */
class FixedBottomNavigationTab extends BottomNavigationTab {

    float labelScale;
    float labelActiveSize;
    float labelInactiveSize;

    public FixedBottomNavigationTab(Context context) {
        super(context);
    }

    public FixedBottomNavigationTab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedBottomNavigationTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FixedBottomNavigationTab(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    void init() {
        paddingTopActive = (int) getResources().getDimension(R.dimen.fixed_height_top_padding_active);
        paddingTopInActive = (int) getResources().getDimension(R.dimen.fixed_height_top_padding_inactive);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fixed_bottom_navigation_item, this, true);
        containerView = view.findViewById(R.id.fixed_bottom_navigation_container);
        iconLayout = (FrameLayout) view.findViewById(R.id.fixed_bottom_navigation_layout);
        labelView = (TextView) view.findViewById(R.id.fixed_bottom_navigation_title);
        iconView = (ImageView) view.findViewById(R.id.fixed_bottom_navigation_icon);
        badgeView = (TextView) view.findViewById(R.id.fixed_bottom_navigation_badge);

        labelActiveSize = getResources().getDimension(R.dimen.fixed_label_active);
        labelInactiveSize = getResources().getDimension(R.dimen.fixed_label_inactive);
        labelScale = labelInactiveSize / labelActiveSize;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            labelView.setScaleX(labelScale);
            labelView.setScaleY(labelScale);
        } else {
            labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, labelInactiveSize);
        }

        super.init();
    }

    @Override
    public void select(boolean setActiveColor, int animationDuration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            labelView.animate().scaleX(1).scaleY(1).setDuration(animationDuration);
        } else {
            labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, labelActiveSize);
        }
        super.select(setActiveColor, animationDuration);
    }

    @Override
    public void unSelect(boolean setActiveColor, int animationDuration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            labelView.animate().scaleX(labelScale).scaleY(labelScale).setDuration(animationDuration);
        } else {
            labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, labelInactiveSize);
        }
        super.unSelect(setActiveColor, animationDuration);
    }

//    @Override
//    public void initialise(boolean setActiveColor) {
//        super.initialise(setActiveColor);
//    }
}
