package com.ashokvarma.sample;

import android.support.test.rule.ActivityTestRule;

import com.ashokvarma.bottomnavigation.sample.HomeActivity;
import com.ashokvarma.bottomnavigation.sample.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Class description
 *
 * @author ashokvarma
 * @version 1.0
 * @see
 * @since 26 May 2016
 */
public class HomeScreenTest {

    @Rule
    public ActivityTestRule<HomeActivity> mHomeActivityTestRule = new ActivityTestRule<>(HomeActivity.class);


    @Test
    public void scrollHome_test() throws Exception {
        onView(withId(R.id.nested_scroll_view))
                .perform(Utils.swipeUpSlow());

        onView(isRoot()).perform(Utils.waitId(R.id.nested_scroll_view, 150 * 1000));

        onView(withId(R.id.nested_scroll_view))
                .perform(Utils.swipeDownSlow());

        onView(withId(R.id.auto_hide))
                .perform(click());

        onView(isRoot()).perform(Utils.waitId(R.id.nested_scroll_view, 150 * 1000));
    }


}
