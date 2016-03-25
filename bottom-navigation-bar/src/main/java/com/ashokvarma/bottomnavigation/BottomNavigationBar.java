package com.ashokvarma.bottomnavigation;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.IntDef;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ashokvarma.bottomnavigation.behaviour.BottomVerticalScrollBehavior;
import com.ashokvarma.bottomnavigation.utils.Utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/**
 * Class description
 *
 * @author ashokvarma
 * @version 1.0
 * @see FrameLayout
 * @see <a href="https://www.google.com/design/spec/components/bottom-navigation.html">Google Bottom Navigation Component</a>
 * @since 19 Mar 2016
 */
@CoordinatorLayout.DefaultBehavior(BottomVerticalScrollBehavior.class)
public class BottomNavigationBar extends FrameLayout {

    public static final int MODE_DEFAULT = 0;
    public static final int MODE_CLASSIC = 1;
    public static final int MODE_SHIFTING = 2;

    @IntDef({MODE_DEFAULT, MODE_CLASSIC, MODE_SHIFTING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public static final int BACKGROUND_STYLE_DEFAULT = 0;
    public static final int BACKGROUND_STYLE_STATIC = 1;
    public static final int BACKGROUND_STYLE_RIPPLE = 2;

    @IntDef({BACKGROUND_STYLE_DEFAULT, BACKGROUND_STYLE_STATIC, BACKGROUND_STYLE_RIPPLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BackgroundStyle {
    }

    @Mode
    private int mMode = MODE_DEFAULT;
    @BackgroundStyle
    private int mBackgroundStyle = BACKGROUND_STYLE_DEFAULT;

    private boolean mScrollable = false;

    private static final int MIN_SIZE = 3;
    private static final int MAX_SIZE = 5;

    ArrayList<BottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    ArrayList<BottomNavigationTab> bottomNavigationTabs = new ArrayList<>();

    private static final int DEFAULT_SELECTED_POSITION = -1;
    private int mSelectedPosition = DEFAULT_SELECTED_POSITION;
    private int mFirstSelectedPosition = 0;
    private OnTabSelectedListener mTabSelectedListener;

    private int mActiveColor;
    private int mInActiveColor;
    private int mBackgroundColor;

    private FrameLayout mBackgroundOverlay;
    private FrameLayout mContainer;
    private LinearLayout mTabContainer;

    private int mAnimationDuration = 200;
    private int mRippleAnimationDuration = 500;

    public BottomNavigationBar(Context context) {
        super(context);
        init();
    }

    public BottomNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BottomNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BottomNavigationBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getContext().getResources().getDimension(R.dimen.bottom_navigation_height)));

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View parentView = inflater.inflate(R.layout.bottom_navigation_bar_container, this, true);
        mBackgroundOverlay = (FrameLayout) parentView.findViewById(R.id.bottom_navigation_bar_overLay);
        mContainer = (FrameLayout) parentView.findViewById(R.id.bottom_navigation_bar_container);
        mTabContainer = (LinearLayout) parentView.findViewById(R.id.bottom_navigation_bar_item_container);

        mActiveColor = Utils.fetchContextColor(getContext(), R.attr.colorAccent);
        mBackgroundColor = Color.WHITE;
        mInActiveColor = Color.LTGRAY;

        ViewCompat.setElevation(this, getContext().getResources().getDimension(R.dimen.bottom_navigation_elevation));
        setClipToPadding(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public BottomNavigationBar addItem(BottomNavigationItem item) {
        bottomNavigationItems.add(item);
        return this;
    }

    public BottomNavigationBar removeItem(BottomNavigationItem item) {
        bottomNavigationItems.remove(item);
        return this;
    }

    public BottomNavigationBar setMode(@Mode int mode) {
        this.mMode = mode;
        return this;
    }

    public BottomNavigationBar setBackgroundStyle(@BackgroundStyle int backgroundStyle) {
        this.mBackgroundStyle = backgroundStyle;
        return this;
    }

    public BottomNavigationBar setActiveColor(@ColorRes int activeColor) {
        this.mActiveColor = getContext().getResources().getColor(activeColor);
        return this;
    }

    public BottomNavigationBar setActiveColor(String activeColorCode) {
        this.mActiveColor = Color.parseColor(activeColorCode);
        return this;
    }

    public BottomNavigationBar setInActiveColor(@ColorRes int inActiveColor) {
        this.mInActiveColor = getContext().getResources().getColor(inActiveColor);
        return this;
    }

    public BottomNavigationBar setInActiveColor(String inActiveColorCode) {
        this.mInActiveColor = Color.parseColor(inActiveColorCode);
        return this;
    }

    public BottomNavigationBar setBarBackgroundColor(@ColorRes int backgroundColor) {
        this.mBackgroundColor = getContext().getResources().getColor(backgroundColor);
        return this;
    }

    public BottomNavigationBar setBarBackgroundColor(String backgroundColorCode) {
        this.mBackgroundColor = Color.parseColor(backgroundColorCode);
        return this;
    }

    public BottomNavigationBar setFirstSelectedPosition(int firstSelectedPosition) {
        this.mFirstSelectedPosition = firstSelectedPosition;
        return this;
    }

    /**
     * will be public once all bugs are ressolved.
     */
    private BottomNavigationBar setScrollable(boolean scrollable) {
        mScrollable = scrollable;
        return this;
    }

    public BottomNavigationBar setTabSelectedListener(OnTabSelectedListener tabSelectedListener) {
        this.mTabSelectedListener = tabSelectedListener;
        return this;
    }

    public void initialise() {
        if (bottomNavigationItems.size() > 0) {
            mTabContainer.removeAllViews();
            if (mMode == MODE_DEFAULT) {
                if (bottomNavigationItems.size() <= MIN_SIZE) {
                    mMode = MODE_CLASSIC;
                } else {
                    mMode = MODE_SHIFTING;
                }
            }
            if (mBackgroundStyle == BACKGROUND_STYLE_DEFAULT) {
                if (mMode == MODE_CLASSIC) {
                    mBackgroundStyle = BACKGROUND_STYLE_STATIC;
                } else {
                    mBackgroundStyle = BACKGROUND_STYLE_RIPPLE;
                }
            }

            if (mBackgroundStyle == BACKGROUND_STYLE_STATIC) {
                mBackgroundOverlay.setBackgroundColor(mBackgroundColor);
                mContainer.setBackgroundColor(mBackgroundColor);
            }

            int screenWidth = Utils.getScreenWidth(getContext());

            if (mMode == MODE_CLASSIC) {

                int widths[] = BottomNavigationHelper.getClassicMeasurements(getContext(), screenWidth, bottomNavigationItems.size(), mScrollable);
                int itemWidth = widths[0];

                for (BottomNavigationItem currentItem : bottomNavigationItems) {
                    ClassicBottomNavigationTab bottomNavigationTab = new ClassicBottomNavigationTab(getContext());
                    setUpTab(bottomNavigationTab, currentItem, itemWidth, itemWidth);
                }

            } else if (mMode == MODE_SHIFTING) {

                int widths[] = BottomNavigationHelper.getShiftingMeasurements(getContext(), screenWidth, bottomNavigationItems.size(), mScrollable);

                int itemWidth = widths[0];
                int itemActiveWidth = widths[1];

                for (BottomNavigationItem currentItem : bottomNavigationItems) {
                    ShiftingBottomNavigationTab bottomNavigationTab = new ShiftingBottomNavigationTab(getContext());
                    setUpTab(bottomNavigationTab, currentItem, itemWidth, itemActiveWidth);
                }
            }

            if (bottomNavigationTabs.size() > mFirstSelectedPosition) {
                selectTabInternal(mFirstSelectedPosition, true, true);
            } else if (bottomNavigationTabs.size() > 0) {
                selectTabInternal(0, true, true);
            }
        }
    }

    private void setUpTab(BottomNavigationTab bottomNavigationTab, BottomNavigationItem currentItem, int itemWidth, int itemActiveWidth) {
        bottomNavigationTab.setInactiveWidth(itemWidth);
        bottomNavigationTab.setActiveWidth(itemActiveWidth);
        bottomNavigationTab.setPosition(bottomNavigationItems.indexOf(currentItem));

        bottomNavigationTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomNavigationTab bottomNavigationTabView = (BottomNavigationTab) v;
                selectTabInternal(bottomNavigationTabView.getPosition(), false, true);
            }
        });

        bottomNavigationTabs.add(bottomNavigationTab);

        BottomNavigationHelper.bindTabWithData(currentItem, bottomNavigationTab, this);

        bottomNavigationTab.initialise(mBackgroundStyle == BACKGROUND_STYLE_STATIC);

        mTabContainer.addView(bottomNavigationTab);
    }

    public void clearAll() {
        mTabContainer.removeAllViews();
        bottomNavigationTabs.clear();
        bottomNavigationItems.clear();
        mBackgroundOverlay.setBackgroundColor(Color.TRANSPARENT);
        mContainer.setBackgroundColor(Color.TRANSPARENT);
        mSelectedPosition = DEFAULT_SELECTED_POSITION;
    }

    public void selectTab(int newPosition) {
        selectTab(newPosition, true);
    }

    public void selectTab(int newPosition, boolean callListener) {
        selectTabInternal(newPosition, false, callListener);
    }

    private void selectTabInternal(int newPosition, boolean firstTab, boolean callListener) {
        if (callListener)
            sendListenerCall(mSelectedPosition, newPosition);
        if (mSelectedPosition != newPosition) {
            if (mBackgroundStyle == BACKGROUND_STYLE_STATIC) {
                if (mSelectedPosition != -1)
                    bottomNavigationTabs.get(mSelectedPosition).unSelect(true, mAnimationDuration);
                bottomNavigationTabs.get(newPosition).select(true, mAnimationDuration);
            } else if (mBackgroundStyle == BACKGROUND_STYLE_RIPPLE) {
                if (mSelectedPosition != -1)
                    bottomNavigationTabs.get(mSelectedPosition).unSelect(false, mAnimationDuration);
                bottomNavigationTabs.get(newPosition).select(false, mAnimationDuration);

                BottomNavigationTab clickedView = bottomNavigationTabs.get(newPosition);
                if (firstTab) {
                    mContainer.setBackgroundColor(clickedView.getActiveColor());
                } else {
                    BottomNavigationHelper.setBackgroundWithRipple(clickedView, mContainer, mBackgroundOverlay, clickedView.getActiveColor(), mRippleAnimationDuration);
                }
            }
            mSelectedPosition = newPosition;
        }
    }

    private void sendListenerCall(int oldPosition, int newPosition) {
        if (mTabSelectedListener != null && oldPosition != -1) {
            if (oldPosition == newPosition) {
                mTabSelectedListener.onTabReselected(newPosition);
            } else {
                mTabSelectedListener.onTabSelected(newPosition);
                mTabSelectedListener.onTabUnselected(newPosition);
            }
        }
    }

    public int getActiveColor() {
        return mActiveColor;
    }

    public int getInActiveColor() {
        return mInActiveColor;
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public int getCurrentSelectedPosition() {
        return mSelectedPosition;
    }

    /**
     * Callback interface invoked when a tab's selection state changes.
     */
    public interface OnTabSelectedListener {

        /**
         * Called when a tab enters the selected state.
         *
         * @param position The position of the tab that was selected
         */
        public void onTabSelected(int position);

        /**
         * Called when a tab exits the selected state.
         *
         * @param position The position of the tab that was unselected
         */
        public void onTabUnselected(int position);

        /**
         * Called when a tab that is already selected is chosen again by the user. Some applications
         * may use this action to return to the top level of a category.
         *
         * @param position The position of the tab that was reselected.
         */
        public void onTabReselected(int position);
    }
}
