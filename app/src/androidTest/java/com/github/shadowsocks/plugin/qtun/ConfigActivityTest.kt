package com.github.shadowsocks.plugin.qtun

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConfigActivityTest {

    @Test
    fun activityLaunchesSuccessfully() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(context, ConfigActivity::class.java).apply {
            putExtra("com.github.shadowsocks.plugin.EXTRA_OPTIONS", "")
        }
        val scenario = ActivityScenario.launch<ConfigActivity>(intent)
        scenario.onActivity { activity ->
            assert(activity != null)
        }
        scenario.close()
    }

    @Test
    fun toolbarIsDisplayedWithTitle() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(context, ConfigActivity::class.java).apply {
            putExtra("com.github.shadowsocks.plugin.EXTRA_OPTIONS", "")
        }
        val scenario = ActivityScenario.launch<ConfigActivity>(intent)
        onView(withId(com.github.shadowsocks.plugin.R.id.toolbar))
            .check(matches(isDisplayed()))
        scenario.close()
    }

    @Test
    fun applyMenuItemExists() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(context, ConfigActivity::class.java).apply {
            putExtra("com.github.shadowsocks.plugin.EXTRA_OPTIONS", "")
        }
        val scenario = ActivityScenario.launch<ConfigActivity>(intent)
        onView(withContentDescription("Apply"))
            .check(matches(isDisplayed()))
        scenario.close()
    }
}
