package com.ashokvarma.sample;

import android.view.View;

import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;

import org.hamcrest.Matcher;

import java.util.concurrent.TimeoutException;

import static androidx.test.espresso.action.ViewActions.actionWithAssertions;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Class description
 *
 * @author ashokvarma
 * @version 1.0
 * @see
 * @since 26 May 2016
 */
public class Utils {
    /**
     * The distance of a swipe's start position from the view's edge, in terms of the view's length.
     * We do not start the swipe exactly on the view's edge, but somewhat more inward, since swiping
     * from the exact edge may behave in an unexpected way (e.g. may open a navigation drawer).
     */
    private static final float EDGE_FUZZ_FACTOR = 0.083f;


    /**
     * Perform action of waiting for a specific view id.
     */
    public static ViewAction waitId(final int viewId, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for a specific view with id <" + viewId + "> during " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                final Matcher<View> viewMatcher = withId(viewId);

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);

                // timeout happens
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }

    /**
     * Returns an action that performs a swipe right-to-left across the vertical center of the
     * view. The swipe doesn't start at the very edge of the view, but is a bit offset.<br>
     * <br>
     * View constraints:
     * <ul>
     * <li>must be displayed on screen
     * <ul>
     */
    public static ViewAction swipeLeftSlow() {
        return actionWithAssertions(new GeneralSwipeAction(Swipe.SLOW,
                translate(GeneralLocation.CENTER_RIGHT, -EDGE_FUZZ_FACTOR, 0),
                GeneralLocation.CENTER_LEFT, Press.FINGER));
    }

    /**
     * Returns an action that performs a swipe left-to-right across the vertical center of the
     * view. The swipe doesn't start at the very edge of the view, but is a bit offset.<br>
     * <br>
     * View constraints:
     * <ul>
     * <li>must be displayed on screen
     * <ul>
     */
    public static ViewAction swipeRightSlow() {
        return actionWithAssertions(new GeneralSwipeAction(Swipe.SLOW,
                translate(GeneralLocation.CENTER_LEFT, EDGE_FUZZ_FACTOR, 0),
                GeneralLocation.CENTER_RIGHT, Press.FINGER));
    }

    /**
     * Returns an action that performs a swipe top-to-bottom across the horizontal center of the view.
     * The swipe doesn't start at the very edge of the view, but has a bit of offset.<br>
     * <br>
     * View constraints:
     * <ul>
     * <li>must be displayed on screen
     * <ul>
     */
    public static ViewAction swipeDownSlow() {
        return actionWithAssertions(new GeneralSwipeAction(Swipe.SLOW,
                translate(GeneralLocation.TOP_CENTER, 0, EDGE_FUZZ_FACTOR),
                GeneralLocation.BOTTOM_CENTER, Press.FINGER));
    }

    /**
     * Returns an action that performs a swipe bottom-to-top across the horizontal center of the view.
     * The swipe doesn't start at the very edge of the view, but has a bit of offset.<br>
     * <br>
     * View constraints:
     * <ul>
     * <li>must be displayed on screen
     * <ul>
     */
    public static ViewAction swipeUpSlow() {
        return actionWithAssertions(new GeneralSwipeAction(Swipe.SLOW,
                translate(GeneralLocation.BOTTOM_CENTER, 0, -EDGE_FUZZ_FACTOR),
                GeneralLocation.TOP_CENTER, Press.FINGER));
    }

    /**
     * Translates the given coordinates by the given distances. The distances are given in term
     * of the view's size -- 1.0 means to translate by an amount equivalent to the view's length.
     */
    static CoordinatesProvider translate(final CoordinatesProvider coords,
                                         final float dx, final float dy) {
        return new CoordinatesProvider() {
            @Override
            public float[] calculateCoordinates(View view) {
                float xy[] = coords.calculateCoordinates(view);
                xy[0] += dx * view.getWidth();
                xy[1] += dy * view.getHeight();
                return xy;
            }
        };
    }
}
