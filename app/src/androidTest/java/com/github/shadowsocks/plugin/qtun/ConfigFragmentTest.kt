package com.github.shadowsocks.plugin.qtun

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.shadowsocks.plugin.PluginOptions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConfigFragmentTest {

    @Test
    fun defaultHostIsBingDotCom() {
        val scenario = launchFragmentInContainer<ConfigFragment>(
            themeResId = com.github.shadowsocks.plugin.R.style.Theme_Shadowsocks
        )
        scenario.onFragment { fragment ->
            val options = fragment.options
            // Default value should be omitted from PluginOptions
            assertFalse("host key should be absent for default value", options.containsKey("host"))
        }
    }

    @Test
    fun onInitializePluginOptionsSetsCustomHost() {
        val scenario = launchFragmentInContainer<ConfigFragment>(
            themeResId = com.github.shadowsocks.plugin.R.style.Theme_Shadowsocks
        )
        scenario.onFragment { fragment ->
            val pluginOptions = PluginOptions()
            pluginOptions["host"] = "example.com"
            fragment.onInitializePluginOptions(pluginOptions)
        }
        scenario.onFragment { fragment ->
            val options = fragment.options
            assertEquals("example.com", options["host"])
        }
    }

    @Test
    fun optionsProducesCorrectPluginOptionsWithCustomHost() {
        val scenario = launchFragmentInContainer<ConfigFragment>(
            themeResId = com.github.shadowsocks.plugin.R.style.Theme_Shadowsocks
        )
        scenario.onFragment { fragment ->
            val pluginOptions = PluginOptions()
            pluginOptions["host"] = "custom.host.org"
            fragment.onInitializePluginOptions(pluginOptions)
        }
        scenario.onFragment { fragment ->
            val options = fragment.options
            assertEquals("custom.host.org", options["host"])
        }
    }

    @Test
    fun optionsOmitsHostWhenDefault() {
        val scenario = launchFragmentInContainer<ConfigFragment>(
            themeResId = com.github.shadowsocks.plugin.R.style.Theme_Shadowsocks
        )
        scenario.onFragment { fragment ->
            val pluginOptions = PluginOptions()
            pluginOptions["host"] = "bing.com"
            fragment.onInitializePluginOptions(pluginOptions)
        }
        scenario.onFragment { fragment ->
            val options = fragment.options
            assertFalse(
                "host key should be absent when value equals default",
                options.containsKey("host")
            )
        }
    }
}
