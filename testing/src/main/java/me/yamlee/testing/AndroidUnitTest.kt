package me.yamlee.testing

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.runner.RunWith

/**
 * 需依赖Android Framework资源的测试，例如context等一些api，
 * 需要继承此类
 */
@RunWith(AndroidJUnit4::class)
abstract class AndroidUnitTest {
    protected lateinit var applicationContext: Context

    @Before
    open fun setup() {
        applicationContext = ApplicationProvider.getApplicationContext()
    }
}