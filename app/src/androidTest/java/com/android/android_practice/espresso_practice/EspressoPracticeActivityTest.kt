package com.android.android_practice.espresso_practice

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.android.android_practice.PracticeAppEspressoIdlingResource
import com.android.android_practice.PracticeEspressoCountingIdlingResource
import com.android.android_practice.R
import com.android.android_practice.recyclerview.StudentRecyclerViewAdapter.StudentViewHolder
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class EspressoPracticeActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(EspressoPracticeActivity::class.java)

    @get:Rule
    var intentRule = IntentsRule()

    //would ideally use some DI / test double to provide dependency
    val repo = EspressoPracticeRepo()

    val testIdlingResource = PracticeAppEspressoIdlingResource("test")

    @Before
    fun onBefore() {
        IdlingRegistry.getInstance().register(PracticeEspressoCountingIdlingResource.countingIdlingResource)
    }

    @After
    fun onAfter() {
        IdlingRegistry.getInstance().unregister(PracticeEspressoCountingIdlingResource.countingIdlingResource)
    }

    @Test
    fun test_screen_title_is_displayed() {
        onView(ViewMatchers.withText("Espresso Hangout Room"))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    /*
    * - the first item is Student("james Bun" , "23" , "1")
    *   so recyclerview test is based on the instance
    *
    * - using onData(objectMatcher).dataOptions.perform(..).check(..) to test where :
    *   -> objectMatcher could be : anyOf(..),instanceOf(..) e.t.c from the hamcrest Matchers class
    *   -> dataOptions could be : inAdapterView(..) , atPosition(..) or onChildView(..)
    *
    *  - ViewActions can vary from
    *   -> NavigationDrawerActions , RecyclerViewActions , PickerActions , ViewPagerActions e.t.c
    */
    @Test
    fun test_navigate_to_detail_activity_with_details_view_visible()  {
        val student = repo.getStudents()[0]

        onView(ViewMatchers.withId(R.id.student_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<StudentViewHolder>(0,ViewActions.click())
            )

        //another way to do the test is to check against the id and verify using the adapter data model
        onView(ViewMatchers.withText("James Bun"))
            .check(ViewAssertions.matches(isDisplayed()))

        onView(ViewMatchers.withText("23"))
            .check(ViewAssertions.matches(isDisplayed()))

        onView(ViewMatchers.withText("1"))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    /*
    * - test intents using either :
    *   -> Intending(..) : you get to stub intents result response for the activity under test that you (similar to 'Mockito.when()' )
    *
    *   -> Intended(..) : you use IntentMatchers to match certain attributes of the passed intent i.e hasCategory , hastExtra , hasAction , toPackage e.t.c
    */
    @Test
    fun test_navigate_to_detail_activity_with_right_passed_intents() {
        val student = repo.getStudents()[0]

        onView(ViewMatchers.withId(R.id.student_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<StudentViewHolder>(0,ViewActions.click())
            )

        intended(IntentMatchers.hasExtra("name",student.name))

        intended(IntentMatchers.hasExtra("id",student.id))

        intended(IntentMatchers.hasExtra("age",student.age))
    }
}