package com.example.bakingapp;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;


import androidx.test.espresso.intent.rule.IntentsTestRule;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;





@LargeTest
@RunWith(AndroidJUnit4.class)
public class TabletTest {

    @Rule
    public IntentsTestRule<MainActivity> mActivityTestRule = new IntentsTestRule<>(MainActivity.class);

    // This test only works with tablets
    @Test
    public void tabletTest() {
        onView(ViewMatchers.withId(R.id.recipe_tablet)).check(matches(isDisplayed()));
    }

}

