package com.ashokvarma.bottomnavigation;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.IntDef;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ashokvarma.bottomnavigation.behaviour.BottomVerticalScrollBehavior;
import com.ashokvarma.bottomnavigation.utils.Utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/**
 * Class description : This class is used to draw the layout and this acts like a bridge between
 * library and app, all details can be modified via this class.
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

    private static final Interpolator INTERPOLATOR = new LinearOutSlowInInterpolator();
    private ViewPropertyAnimatorCompat mTranslationAnimator;

    private boolean mScrollable = false;

    private static final int MIN_SIZE = 3;
    private static final int MAX_SIZE = 5;

    ArrayList<BottomNavigationItem> mBottomNavigationItems = new ArrayList<>();
    ArrayList<BottomNavigationTab> mBottomNavigationTabs = new ArrayList<>();

    private static final int DEFAULT_SELECTED_POSITION = -1;
    private int mSelectedPosition = DEFAULT_SELECTED_POSITION;
    private int mFirstSelectedPosition = 0;
    private OnTabSelectedListener mTabSelectedListener;

    private int mActiveColor;
    private int mInActiveColor;
    private int mBackgroundColor;
    private int mBadgeBackgroundColor;
    private int mBadgeTextColor;

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

    /**
     * This method initiates the bottom Navigation bar and assigns default values
     */
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
        mBadgeBackgroundColor = Color.RED;
        mBadgeTextColor = Color.WHITE;

        ViewCompat.setElevation(this, getContext().getResources().getDimension(R.dimen.bottom_navigation_elevation));
        setClipToPadding(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Used to add a new tab.
     *
     * @param item bottom navigation tab details
     * @return this, to allow builder pattern
     */
    public BottomNavigationBar addItem(BottomNavigationItem item) {
        mBottomNavigationItems.add(item);
        return this;
    }

    /**
     * Used to remove a tab.
     * you should call initialise() after this to see the results effected.
     *
     * @param item bottom navigation tab details
     * @return this, to allow builder pattern
     */
    public BottomNavigationBar removeItem(BottomNavigationItem item) {
        mBottomNavigationItems.remove(item);
        return this;
    }

    /**
     * @param mode any of the three Modes supported by library
     * @return this, to allow builder pattern
     */
    public BottomNavigationBar setMode(@Mode int mode) {
        this.mMode = mode;
        return this;
    }

    /**
     * @param backgroundStyle any of the three Background Styles supported by library
     * @return this, to allow builder pattern
     */
    public BottomNavigationBar setBackgroundStyle(@BackgroundStyle int backgroundStyle) {
        this.mBackgroundStyle = backgroundStyle;
        return this;
    }

    /**
     * @param activeColor res code for the default active color
     * @return this, to allow builder pattern
     */
    public BottomNavigationBar setActiveColor(@ColorRes int activeColor) {
        this.mActiveColor = getContext().getResources().getColor(activeColor);
        return this;
    }

    /**
     * @param activeColorCode color code in string format for the default active color
     * @return this, to allow builder pattern
     */
    public BottomNavigationBar setActiveColor(String activeColorCode) {
        this.mActiveColor = Color.parseColor(activeColorCode);
        return this;
    }

    /**
     * @param inActiveColor res code for the default in-active color
     * @return this, to allow builder pattern
     */
    public BottomNavigationBar setInActiveColor(@ColorRes int inActiveColor) {
        this.mInActiveColor = getContext().getResources().getColor(inActiveColor);
        return this;
    }

    /**
     * @param inActiveColorCode color code in string format for the default in-active color
     * @return this, to allow builder pattern
     */
    public BottomNavigationBar setInActiveColor(String inActiveColorCode) {
        this.mInActiveColor = Color.parseColor(inActiveColorCode);
        return this;
    }

    /**
     * @param badgeBackground res code for the default badge background color
     * @return this, to allow builder pattern
     */
    public BottomNavigationBar setBadgeBackgroundColor(@ColorRes int badgeBackground) {
        this.mBadgeBackgroundColor = getContext().getResources().getColor(badgeBackground);
        return this;
    }

    /**
     * @param badgeBackground color code in string format for the default badge background color
     * @return this, to allow builder pattern
     */
    public BottomNavigationBar setBadgeBackgroundColor(String badgeBackground) {
        this.mBadgeBackgroundColor = Color.parseColor(badgeBackground);
        return this;
    }

    /**
     * @param badgeTextColor res code for the default badge text color
     * @return this, to allow builder pattern
     */
    public BottomNavigationBar setBadgeTextColor(@ColorRes int badgeTextColor) {
        this.mBadgeTextColor = getContext().getResources().getColor(badgeTextColor);
        return this;
    }

    /**
     * @param badgeTextColor color code in string format for the default badge text color
     * @return this, to allow builder pattern
     */
    public BottomNavigationBar setBadgeTextColor(String badgeTextColor) {
        this.mBadgeTextColor = Color.parseColor(badgeTextColor);
        return this;
    }

    /**
     * @param backgroundColor res code for the default background color
     * @return this, to allow builder pattern
     */
    public BottomNavigationBar setBarBackgroundColor(@ColorRes int backgroundColor) {
        this.mBackgroundColor = getContext().getResources().getColor(backgroundColor);
        return this;
    }

    /**
     * @param backgroundColorCode color code in string format for the default background color
     * @return this, to allow builder pattern
     */
    public BottomNavigationBar setBarBackgroundColor(String backgroundColorCode) {
        this.mBackgroundColor = Color.parseColor(backgroundColorCode);
        return this;
    }

    /**
     * @param firstSelectedPosition position of tab that needs to be selected by default
     * @return this, to allow builder pattern
     */
    public BottomNavigationBar setFirstSelectedPosition(int firstSelectedPosition) {
        this.mFirstSelectedPosition = firstSelectedPosition;
        return this;
    }

    /**
     * ripple animation will be 2.5 times this animation duration.
     *
     * @param animationDuration animation duration for tab animations
     * @return this, to allow builder pattern
     */
    public BottomNavigationBar setAnimationDuration(int animationDuration) {
        this.mAnimationDuration = animationDuration;
        this.mRippleAnimationDuration = (int) (animationDuration * 2.5);
        return this;
    }

    /**
     * will be public once all bugs are ressolved.
     */
    private BottomNavigationBar setScrollable(boolean scrollable) {
        mScrollable = scrollable;
        return this;
    }

    /**
     * @param tabSelectedListener callback listener for tabs
     * @return this, to allow builder pattern
     */
    public BottomNavigationBar setTabSelectedListener(OnTabSelectedListener tabSelectedListener) {
        this.mTabSelectedListener = tabSelectedListener;
        return this;
    }

    /**
     * This method should be called at the end of all customisation method.
     * This method will take all changes in to consideration and redraws tabs.
     */
    public void initialise() {
        if (mBottomNavigationItems.size() > 0) {
            mTabContainer.removeAllViews();
            if (mMode == MODE_DEFAULT) {
                if (mBottomNavigationItems.size() <= MIN_SIZE) {
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

                int widths[] = BottomNavigationHelper.getClassicMeasurements(getContext(), screenWidth, mBottomNavigationItems.size(), mScrollable);
                int itemWidth = widths[0];

                for (BottomNavigationItem currentItem : mBottomNavigationItems) {
                    ClassicBottomNavigationTab bottomNavigationTab = new ClassicBottomNavigationTab(getContext());
                    setUpTab(bottomNavigationTab, currentItem, itemWidth, itemWidth);
                }

            } else if (mMode == MODE_SHIFTING) {

                int widths[] = BottomNavigationHelper.getShiftingMeasurements(getContext(), screenWidth, mBottomNavigationItems.size(), mScrollable);

                int itemWidth = widths[0];
                int itemActiveWidth = widths[1];

                for (BottomNavigationItem currentItem : mBottomNavigationItems) {
                    ShiftingBottomNavigationTab bottomNavigationTab = new ShiftingBottomNavigationTab(getContext());
                    setUpTab(bottomNavigationTab, currentItem, itemWidth, itemActiveWidth);
                }
            }

            if (mBottomNavigationTabs.size() > mFirstSelectedPosition) {
                selectTabInternal(mFirstSelectedPosition, true, true);
            } else if (mBottomNavigationTabs.size() > 0) {
                selectTabInternal(0, true, true);
            }
        }
    }

    /**
     * Internal method to setup tabs
     *
     * @param bottomNavigationTab Tab item
     * @param currentItem         data structure for tab item
     * @param itemWidth           tab item in-active width
     * @param itemActiveWidth     tab item active width
     */
    private void setUpTab(BottomNavigationTab bottomNavigationTab, BottomNavigationItem currentItem, int itemWidth, int itemActiveWidth) {
        bottomNavigationTab.setInactiveWidth(itemWidth);
        bottomNavigationTab.setActiveWidth(itemActiveWidth);
        bottomNavigationTab.setPosition(mBottomNavigationItems.indexOf(currentItem));

        bottomNavigationTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomNavigationTab bottomNavigationTabView = (BottomNavigationTab) v;
                selectTabInternal(bottomNavigationTabView.getPosition(), false, true);
            }
        });

        mBottomNavigationTabs.add(bottomNavigationTab);

        BottomNavigationHelper.bindTabWithData(currentItem, bottomNavigationTab, this);

        bottomNavigationTab.initialise(mBackgroundStyle == BACKGROUND_STYLE_STATIC);

        mTabContainer.addView(bottomNavigationTab);
    }

    /**
     * Clears all stored data and this helps to re-initialise tabs from scratch
     */
    public void clearAll() {
        mTabContainer.removeAllViews();
        mBottomNavigationTabs.clear();
        mBottomNavigationItems.clear();
        mBackgroundOverlay.setBackgroundColor(Color.TRANSPARENT);
        mContainer.setBackgroundColor(Color.TRANSPARENT);
        mSelectedPosition = DEFAULT_SELECTED_POSITION;
    }

    /**
     * @param newPosition to select a tab after bottom navigation bar is initialised
     */
    public void selectTab(int newPosition) {
        selectTab(newPosition, true);
    }

    /**
     * @param newPosition  to select a tab after bottom navigation bar is initialised
     * @param callListener should this change call listener callbacks
     */
    public void selectTab(int newPosition, boolean callListener) {
        selectTabInternal(newPosition, false, callListener);
    }

    /**
     * Internal Method to select a tab
     *
     * @param newPosition  to select a tab after bottom navigation bar is initialised
     * @param firstTab     if firstTab the no ripple animation will be done
     * @param callListener is listener callbacks enabled for this change
     */
    private void selectTabInternal(int newPosition, boolean firstTab, boolean callListener) {
        if (callListener)
            sendListenerCall(mSelectedPosition, newPosition);
        if (mSelectedPosition != newPosition) {
            if (mBackgroundStyle == BACKGROUND_STYLE_STATIC) {
                if (mSelectedPosition != -1)
                    mBottomNavigationTabs.get(mSelectedPosition).unSelect(true, mAnimationDuration);
                mBottomNavigationTabs.get(newPosition).select(true, mAnimationDuration);
            } else if (mBackgroundStyle == BACKGROUND_STYLE_RIPPLE) {
                if (mSelectedPosition != -1)
                    mBottomNavigationTabs.get(mSelectedPosition).unSelect(false, mAnimationDuration);
                mBottomNavigationTabs.get(newPosition).select(false, mAnimationDuration);

                BottomNavigationTab clickedView = mBottomNavigationTabs.get(newPosition);
                if (firstTab) {
                    mContainer.setBackgroundColor(clickedView.getActiveColor());
                } else {
                    BottomNavigationHelper.setBackgroundWithRipple(clickedView, mContainer, mBackgroundOverlay, clickedView.getActiveColor(), mRippleAnimationDuration);
                }
            }
            mSelectedPosition = newPosition;
        }
    }

    /**
     * Internal method used to send callbacks to listener
     *
     * @param oldPosition old selected tab position
     * @param newPosition newly selected tab position
     */
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

    /**
     * hide with animation
     */
    public void hide() {
        hide(true);
    }

    /**
     * @param animate is animation enabled for hide
     */
    public void hide(boolean animate) {
        setTranslationY(this.getHeight(), animate);
    }

    /**
     * unHide with animation
     */
    public void unHide() {
        unHide(true);
    }

    /**
     * @param animate is animation enabled for unHide
     */
    public void unHide(boolean animate) {
        setTranslationY(0, animate);
    }

    /**
     * @param offset  offset needs to be set
     * @param animate is animation enabled for translation
     */
    private void setTranslationY(int offset, boolean animate) {
        if (animate) {
            animateOffset(offset);
        } else {
            if (mTranslationAnimator != null) {
                mTranslationAnimator.cancel();
            }
            this.setTranslationY(offset);
        }
    }

    /**
     * Internal Method
     * <p/>
     * used to set animation and
     * takes care of cancelling current animation
     * and sets duration and interpolator for animation
     *
     * @param offset translation offset that needs to set with animation
     */
    private void animateOffset(final int offset) {
        if (mTranslationAnimator == null) {
            mTranslationAnimator = ViewCompat.animate(this);
            mTranslationAnimator.setDuration(mRippleAnimationDuration);
            mTranslationAnimator.setInterpolator(INTERPOLATOR);
        } else {
            mTranslationAnimator.cancel();
        }
        mTranslationAnimator.translationY(offset).start();
    }

    /**
     * @return activeColor
     */
    public int getActiveColor() {
        return mActiveColor;
    }

    /**
     * @return in-active color
     */
    public int getInActiveColor() {
        return mInActiveColor;
    }

    public int getBadgeBackgroundColor() {
        return mBadgeBackgroundColor;
    }

    public int getBadgeTextColor() {
        return mBadgeTextColor;
    }

    /**
     * @return background color
     */
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    /**
     * @return current selected position
     */
    public int getCurrentSelectedPosition() {
        return mSelectedPosition;
    }

    /**
     * Set the badge number for tab
     * @param tabPosition tab's position
     * @param badgeCount badge count
     */
    public void setBadgeCountOnTab(int tabPosition, String badgeCount) {
        mBottomNavigationItems.get(tabPosition).setBadgeCount(badgeCount);
        mBottomNavigationTabs.get(tabPosition).setBadge(badgeCount);
    }

    /**
     * Set the badge number for tab
     * @param tabPosition tab's position
     * @param badgeCount badge count
     */
    public void setBadgeCountOnTab(int tabPosition, int badgeCount) {
        setBadgeCountOnTab(tabPosition, String.valueOf(badgeCount));
    }

    /**
     * Return the badge number for tab
     * @param tabPosition Tab's position
     * @return {@link String} current badge count
     */
    public String getBadgeCountFromTab(int tabPosition) {
        return mBottomNavigationItems.get(tabPosition).getBadgeCount();
    }

    /**
     * @return animation duration
     */
    public int getAnimationDuration() {
        return mAnimationDuration;
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
