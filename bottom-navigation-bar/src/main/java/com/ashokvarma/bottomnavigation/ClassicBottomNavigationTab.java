package com.ashokvarma.bottomnavigation;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
class ClassicBottomNavigationTab extends BottomNavigationTab {

    float labelScale;

    public ClassicBottomNavigationTab(Context context) {
        super(context);
    }

    public ClassicBottomNavigationTab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClassicBottomNavigationTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClassicBottomNavigationTab(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    void init() {
        paddingTopActive = (int) getResources().getDimension(R.dimen.classic_height_top_padding_active);
        paddingTopInActive = (int) getResources().getDimension(R.dimen.classic_height_top_padding_inactive);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.classic_bottom_navigation_item, this, true);
        containerView = view.findViewById(R.id.classic_bottom_navigation_container);
        labelView = (TextView) view.findViewById(R.id.classic_bottom_navigation_title);
        iconView = (ImageView) view.findViewById(R.id.classic_bottom_navigation_icon);

        labelScale = getResources().getDimension(R.dimen.classic_label_active) / getResources().getDimension(R.dimen.classic_label_inactive);

        super.init();
    }

    @Override
    public void select(boolean setActiveColor, int animationDuration) {

        labelView.animate().scaleX(labelScale).scaleY(labelScale).setDuration(animationDuration).start();
//        labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.classic_label_active));
        super.select(setActiveColor, animationDuration);
    }

    @Override
    public void unSelect(boolean setActiveColor, int animationDuration) {
        labelView.animate().scaleX(1).scaleY(1).setDuration(animationDuration).start();
//        labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.classic_label_inactive));
        super.unSelect(setActiveColor, animationDuration);
    }

    @Override
    public void initialise() {

    }
}
