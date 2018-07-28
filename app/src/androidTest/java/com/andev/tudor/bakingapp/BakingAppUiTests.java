package com.andev.tudor.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.andev.tudor.bakingapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class BakingAppUiTests {

    @Rule
    public ActivityTestRule<MainActivity> mTestRule = new ActivityTestRule<>(com.andev.tudor.bakingapp.ui.MainActivity.class, false, true);

    @Test
    public void check_brownies_recipe_name() {
        onView(ViewMatchers.withId(R.id.recipe_cards_rv)).perform(RecyclerViewActions.scrollToPosition(2));
        onView(ViewMatchers.withText("Brownies")).check(matches(isDisplayed()));
    }

    @Test
    public void check_recipe_steps_display() {
        onView(ViewMatchers.withId(R.id.recipe_cards_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipe_steps_rv)).check(matches(isDisplayed()));
    }

    @Test
    public void check_recipe_ingredients_card_display() {
        onView(ViewMatchers.withId(R.id.recipe_cards_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipe_ingredients_cv)).check(matches(isDisplayed()));
    }

    public void check_item_steps_display() {
        onView(ViewMatchers.withId(R.id.recipe_cards_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipe_steps_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.step_description_tv)).check(matches(isDisplayed()));
    }
    @Test
    public void check_ingredients_display() {
        onView(ViewMatchers.withId(R.id.recipe_cards_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipe_ingredients_cv)).perform(click());
        onView(withId(R.id.ingredients_rv)).check(matches(isDisplayed()));
    }

}
