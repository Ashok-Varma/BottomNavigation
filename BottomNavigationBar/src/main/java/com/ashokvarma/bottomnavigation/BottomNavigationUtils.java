package com.ashokvarma.bottomnavigation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Class description
 *
 * @author ashokvarma
 * @version 1.0
 * @since 19 Mar 2016
 */
class BottomNavigationUtils {

    public static int[] getClassicMeasurements(Context context, int screenWidth, int noOfTabs, boolean scrollable) {

        int result[] = new int[2];

        int minWidth = (int) context.getResources().getDimension(R.dimen.classic_min_width_small_views);
        int maxWidth = (int) context.getResources().getDimension(R.dimen.classic_min_width);

        int itemWidth = screenWidth / noOfTabs;

        if (itemWidth < minWidth && scrollable) {
            itemWidth = (int) context.getResources().getDimension(R.dimen.classic_min_width);
        } else if (itemWidth > maxWidth) {
            itemWidth = maxWidth;
        }

        result[0] = itemWidth;

        return result;
    }

    public static int[] getShiftingMeasurements(Context context, int screenWidth, int noOfTabs, boolean scrollable) {

        int result[] = new int[2];

        int minWidth = (int) context.getResources().getDimension(R.dimen.shifting_min_width_inactive);
        int maxWidth = (int) context.getResources().getDimension(R.dimen.shifting_max_width_inactive);

        double minPossibleWidth = minWidth * (noOfTabs + 0.5);
        double maxPossibleWidth = maxWidth * (noOfTabs + 0.75);
        int itemWidth;
        int itemActiveWidth;

        if (screenWidth < minPossibleWidth) {
            if (scrollable) {
                itemWidth = minWidth;
                itemActiveWidth = (int) (minWidth * 1.5);
            } else {
                itemWidth = (int) (screenWidth / (noOfTabs + 0.5));
                itemActiveWidth = (int) (itemWidth * 1.5);
            }
        } else if (screenWidth > maxPossibleWidth) {
            itemWidth = maxWidth;
            itemActiveWidth = (int) (itemWidth * 1.75);
        } else {
            double minPossibleWidth1 = minWidth * (noOfTabs + 0.625);
            double minPossibleWidth2 = minWidth * (noOfTabs + 0.75);
            itemWidth = (int) (screenWidth / (noOfTabs + 0.5));
            itemActiveWidth = (int) (itemWidth * 1.5);
            if (screenWidth > minPossibleWidth1) {
                itemWidth = (int) (screenWidth / (noOfTabs + 0.625));
                itemActiveWidth = (int) (itemWidth * 1.625);
                if (screenWidth > minPossibleWidth2) {
                    itemWidth = (int) (screenWidth / (noOfTabs + 0.75));
                    itemActiveWidth = (int) (itemWidth * 1.75);
                }
            }
        }

        result[0] = itemWidth;
        result[1] = itemActiveWidth;

        return result;
    }

    public static void bindTabWithData(BottomNavigationItem bottomNavigationItem, BottomNavigationTab bottomNavigationTab, BottomNavigationBar bottomNavigationBar) {

        Context context = bottomNavigationBar.getContext();

        bottomNavigationTab.setLabel(bottomNavigationItem.getTitle(context));
        bottomNavigationTab.setIcon(bottomNavigationItem.getIcon(context));

        int activeColor = bottomNavigationItem.getActiveColor(context);
        int inActiveColor = bottomNavigationItem.getInActiveColor(context);

        if (activeColor != -1) {
            bottomNavigationTab.setActiveColor(activeColor);
        } else {
            bottomNavigationTab.setActiveColor(bottomNavigationBar.getActiveColor());
        }

        if (inActiveColor != -1) {
            bottomNavigationTab.setInactiveColor(inActiveColor);
        } else {
            bottomNavigationTab.setInactiveColor(bottomNavigationBar.getInActiveColor());
        }

        bottomNavigationTab.setItemBackgroundColor(bottomNavigationBar.getBackgroundColor());
    }

    public static void setBackgroundWithRipple(View clickedView, final View backgroundView,
                                               final View bgOverlay, final int newColor, int animationTime) {
        int centerX = (int) (clickedView.getX() + (clickedView.getMeasuredWidth() / 2));
        int centerY = clickedView.getMeasuredHeight() / 2;
        int finalRadius = backgroundView.getWidth();

        backgroundView.clearAnimation();
        bgOverlay.clearAnimation();

        Animator circularReveal;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circularReveal = ViewAnimationUtils
                    .createCircularReveal(bgOverlay, centerX, centerY, 0, finalRadius);
        } else {
            bgOverlay.setAlpha(0);
            circularReveal = ObjectAnimator.ofFloat(bgOverlay, "alpha", 0, 1);
        }

        circularReveal.setDuration(animationTime);
        circularReveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                onCancel();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                onCancel();
            }

            private void onCancel() {
                backgroundView.setBackgroundColor(newColor);
                bgOverlay.setVisibility(View.GONE);
            }
        });

        bgOverlay.setBackgroundColor(newColor);
        bgOverlay.setVisibility(View.VISIBLE);
        circularReveal.start();
    }
}
