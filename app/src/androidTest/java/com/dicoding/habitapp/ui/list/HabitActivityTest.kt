package com.dicoding.habitapp.ui.list

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.dicoding.habitapp.ui.add.AddHabitActivity
import com.dicoding.habitapp.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HabitActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(HabitListActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testAddHabitFAB() {
        // Click on the FAB to add a new habit
        onView(withId(R.id.fab)).perform(click())

        // Verify that the AddHabitActivity is displayed
        Intents.intended(hasComponent(AddHabitActivity::class.java.name))
    }
}
