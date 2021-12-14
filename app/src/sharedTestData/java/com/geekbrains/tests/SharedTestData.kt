package com.geekbrains.tests

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers

internal const val TEST_NUMBER = 42
internal const val TEST_NUMBER_OF_RESULTS_ZERO = "Number of results: 0"
internal const val TEST_NUMBER_OF_RESULTS_PLUS_1 = "Number of results: 1"
internal const val TEST_NUMBER_OF_RESULTS_MINUS_1 = "Number of results: -1"
internal const val TIMEOUT = 5000L
internal const val SEARCH_EDIT_TEXT = "searchEditText"
internal const val SEARCH_BUTTON = "searchActivityButton"
internal const val TO_DETAILS_BUTTON = "toDetailsActivityButton"
internal const val INCREMENT_BUTTON = "incrementButton"
internal const val DECREMENT_BUTTON = "decrementButton"
internal const val MAIN_TEXT_VIEW = "mainCountTextView"
internal const val DETAILS_TEXT_VIEW = "detailsCountTextView"
internal const val DEFAULT_VALUE = 0

fun setContext(): Context {
    return ApplicationProvider.getApplicationContext()
}

fun setAssertionText(text: String): ViewAssertion {
    return ViewAssertions.matches(ViewMatchers.withText(text))
}

fun clickOnView(id: Int): ViewInteraction? {
    return Espresso.onView(ViewMatchers.withId(id)).perform(ViewActions.click())
}

fun viewIsDisplayed (id: Int): ViewInteraction? {
    return Espresso.onView(ViewMatchers.withId(id))
        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
}