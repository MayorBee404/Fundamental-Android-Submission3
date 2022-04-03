package com.dicoding.ittelkom.githubuser

import android.view.KeyEvent
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.dicoding.ittelkom.githubuser.ui.listuser.ListUser
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val dummySearch = "MayorBee"

    @Before
    fun setup(){
        ActivityScenario.launch(ListUser::class.java)
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.dicoding.ittelkom.githubuser", appContext.packageName)
        onView(withId(R.id.searchView)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText(dummySearch), pressImeActionButton())

    }
}