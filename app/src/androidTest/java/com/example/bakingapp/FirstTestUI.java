package com.example.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.action.ViewActions;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.rule.ActivityTestRule;

import com.example.bakingapp.util.constantUTL;
import android.support.test.espresso.contrib.RecyclerViewActions;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static org.hamcrest.core.IsNot.not;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class FirstTestUI {

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(
            MainActivity.class);

    @Before
    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void FirstTestUI(){

        // Let the UI load completely first
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Recyclerview scroll to position
        onView(ViewMatchers.withId(R.id.rv_recipes)).perform(RecyclerViewActions.scrollToPosition(4));

        //Perform Recyclerview click on item at position
        onView(withId(R.id.rv_recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Check if intent (RecipeActivity to RecipeDetailsActivity) has RECIPE_INTENT_EXTRA
        intended(hasExtraWithKey(constantUTL.RECIPE_INTENT_EXTRA));

        //Perform click action on start cooking button
        onView(withId(R.id.btn_start_cooking)).perform(ViewActions.click());

        //Check if intent (RecipeDetailsActivity to CookingActivity) has RECIPE_INTENT_EXTRA
        intended(hasComponent(controllerActivity.class.getName()));
    }

}






