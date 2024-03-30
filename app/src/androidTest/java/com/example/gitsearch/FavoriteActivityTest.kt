package com.example.gitsearch

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gitsearch.feature.favorite.FavoriteActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class FavoriteActivityTest {

    @Before
    fun setup() {
        ActivityScenario.launch(FavoriteActivity::class.java)
    }

    @Test
    fun testRecyclerViewDisplayed() {
        onView(withId(R.id.rv_favorite)).check(matches(isDisplayed()))
    }

    @Test
    fun testTitleDisplayed() {
        onView(withText(R.string.label_favorite_users)).check(matches(isDisplayed()))
    }

}