package com.geekbrains.tests

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.view.search.MainActivity
import junit.framework.TestCase
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed() {
        TestCase.assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun activityElements_NotNull() {
        scenario.onActivity {
            val searchEditText = it.findViewById<EditText>(R.id.searchEditText)
            val detailsButton = it.findViewById<Button>(R.id.toDetailsActivityButton)
            val mainCountTextView = it.findViewById<TextView>(R.id.mainCountTextView)

            TestCase.assertNotNull(searchEditText)
            TestCase.assertNotNull(detailsButton)
            TestCase.assertNotNull(mainCountTextView)
        }
    }

    @Test
    fun activityElements_HasText() {
        onView(withId(R.id.searchEditText)).check(setAssertionText(""))
        onView(withId(R.id.toDetailsActivityButton)).check(matches(withText(R.string.to_details)))
    }

    @Test
    fun activityElements_IsDisplayed() {
        viewIsDisplayed(R.id.searchEditText)
        viewIsDisplayed(R.id.toDetailsActivityButton)
    }

    @Test
    fun activityTextView_IsCompletelyDisplayed() {
        onView(withId(R.id.searchEditText)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.toDetailsActivityButton)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun activityElements_VisibilityStatus() {
        onView(withId(R.id.searchEditText)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.toDetailsActivityButton)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.mainCountTextView)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
    }

    @Test
    fun activitySearch_IsWorking() {
        clickOnView(R.id.searchEditText)

        onView(withId(R.id.searchEditText)).perform(replaceText("algol"), closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())

        if (BuildConfig.TYPE == MainActivity.FAKE) {
            onView(withId(R.id.mainCountTextView)).check(setAssertionText("Number of results: 42"))
        } else {
            onView(isRoot()).perform(delay())
            onView(withId(R.id.mainCountTextView)).check(setAssertionText("Number of results: 2693"))
        }
    }

    @Test
    fun activityToDetailsButton_IsWorking() {
        clickOnView(R.id.toDetailsActivityButton)

        //проверяем, что после нажатия на кнопку на экране появились элементы из другой активити
        viewIsDisplayed(R.id.detailsCountTextView)
        viewIsDisplayed(R.id.incrementButton)
        viewIsDisplayed(R.id.decrementButton)
    }

    private fun delay(): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $2 seconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(2000)
            }
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}
