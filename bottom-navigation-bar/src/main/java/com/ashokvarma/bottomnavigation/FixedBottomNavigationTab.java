package com.ashokvarma.bottomnavigation;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

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
        labelView = view.findViewById(R.id.fixed_bottom_navigation_title);
        iconView = view.findViewById(R.id.fixed_bottom_navigation_icon);
        iconContainerView = view.findViewById(R.id.fixed_bottom_navigation_icon_container);
        badgeView = view.findViewById(R.id.fixed_bottom_navigation_badge);

        labelScale = getResources().getDimension(R.dimen.fixed_label_inactive) / getResources().getDimension(R.dimen.fixed_label_active);

        super.init();
    }

    @Override
    public void select(boolean setActiveColor, int animationDuration) {
        labelView.animate().scaleX(1).scaleY(1).setDuration(animationDuration).start();
//        labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fixed_label_active));
        super.select(setActiveColor, animationDuration);
    }

    @Override
    public void unSelect(boolean setActiveColor, int animationDuration) {
        labelView.animate().scaleX(labelScale).scaleY(labelScale).setDuration(animationDuration).start();
//        labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fixed_label_inactive));
        super.unSelect(setActiveColor, animationDuration);
    }

    @Override
    protected void setNoTitleIconContainerParams(FrameLayout.LayoutParams layoutParams) {
        layoutParams.height = getContext().getResources().getDimensionPixelSize(R.dimen.fixed_no_title_icon_container_height);
        layoutParams.width = getContext().getResources().getDimensionPixelSize(R.dimen.fixed_no_title_icon_container_width);
    }

    @Override
    protected void setNoTitleIconParams(LayoutParams layoutParams) {
        layoutParams.height = getContext().getResources().getDimensionPixelSize(R.dimen.fixed_no_title_icon_height);
        layoutParams.width = getContext().getResources().getDimensionPixelSize(R.dimen.fixed_no_title_icon_width);
    }
}
