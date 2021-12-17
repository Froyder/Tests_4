package com.geekbrains.tests

import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.view.details.DetailsActivity
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsActivityEspressoTest {

    private lateinit var scenario: ActivityScenario<DetailsActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(DetailsActivity::class.java)
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
    fun activityTextView_NotNull() {
        scenario.onActivity {
            val totalCountTextView = it.findViewById<TextView>(R.id.detailsFragmentTextView)
            TestCase.assertNotNull(totalCountTextView)
        }
    }

    @Test
    fun activityTextView_HasText() {
        onView(withId(R.id.detailsFragmentTextView)).check(setAssertionText(TEST_NUMBER_OF_RESULTS_ZERO))
    }

    @Test
    fun activityTextView_IsDisplayed() {
        viewIsDisplayed(R.id.detailsFragmentTextView)
    }

    @Test
    fun activityTextView_IsCompletelyDisplayed() {
        onView(withId(R.id.detailsFragmentTextView)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun activityButtons_AreEffectiveVisible() {
        onView(withId(R.id.incrementButton)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.decrementButton)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun activityButtonIncrement_IsWorking() {
        clickOnView(R.id.incrementButton)
        onView(withId(R.id.detailsFragmentTextView)).check(setAssertionText(TEST_NUMBER_OF_RESULTS_PLUS_1))
    }

    @Test
    fun activityButtonDecrement_IsWorking() {
        clickOnView(R.id.decrementButton)
        onView(withId(R.id.detailsFragmentTextView)).check(setAssertionText(TEST_NUMBER_OF_RESULTS_MINUS_1))
    }

    @After
    fun close() {
        scenario.close()
    }
}
