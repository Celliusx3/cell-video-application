package com.cellstudio.cellvideo

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented loading_ripple_scale_interpolator, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under loading_ripple_scale_interpolator.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.cellstudio.cellvideo", appContext.packageName)
    }
}
