package com.android.android_practice

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    @get:Rule
    var rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_view_button_options_are_shown() {
        onView(withId(R.id.showEspressoPractice))
            .check(matches(ViewMatchers.withText("Espresso Practice")))
    }

    @Test
    fun test_show_espresso_practice_option_button_is_Enabled() {
        onView(withId(R.id.showRecyclerViewPractice))
            .perform(
                ViewActions.scrollTo()
            )
            .check(matches(isEnabled()))
    }
}