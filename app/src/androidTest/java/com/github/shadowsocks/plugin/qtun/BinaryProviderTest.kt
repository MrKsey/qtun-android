package com.github.shadowsocks.plugin.qtun

import android.content.ContentResolver
import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.io.FileNotFoundException

@RunWith(AndroidJUnit4::class)
class BinaryProviderTest {

    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun providerIsRegistered() {
        val providerInfo = context.packageManager.resolveContentProvider(
            "com.github.shadowsocks.plugin.qtun.BinaryProvider",
            0
        )
        assertNotNull("BinaryProvider should be registered", providerInfo)
    }

    @Test
    fun openFileReturnsValidDescriptorForQtunClient() {
        val uri = Uri.parse("content://com.github.shadowsocks.plugin.qtun.BinaryProvider/qtun-client")
        val resolver: ContentResolver = context.contentResolver
        val pfd = resolver.openFileDescriptor(uri, "r")
        assertNotNull("ParcelFileDescriptor should not be null", pfd)
        pfd!!.close()
    }

    @Test(expected = FileNotFoundException::class)
    fun openFileThrowsForInvalidPath() {
        val uri = Uri.parse("content://com.github.shadowsocks.plugin.qtun.BinaryProvider/invalid-path")
        val resolver: ContentResolver = context.contentResolver
        resolver.openFileDescriptor(uri, "r")
    }

    @Test
    fun getExecutableReturnsLibqtunPath() {
        val providerInfo = context.packageManager.resolveContentProvider(
            "com.github.shadowsocks.plugin.qtun.BinaryProvider",
            0
        )
        assertNotNull(providerInfo)
        // Verify the native library directory contains libqtun.so
        val nativeLibDir = context.applicationInfo.nativeLibraryDir
        val expectedPath = "$nativeLibDir/libqtun.so"
        assertTrue(
            "Executable path should end with /libqtun.so",
            expectedPath.endsWith("/libqtun.so")
        )
    }
}
