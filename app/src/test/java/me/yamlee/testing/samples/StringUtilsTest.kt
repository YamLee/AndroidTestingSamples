package me.yamlee.testing.samples


import com.google.common.truth.Truth
import me.yamlee.testing.AndroidUnitTest
import org.junit.Test


class StringUtilsTest : AndroidUnitTest() {
    @Test
    fun getApplicationName() {
        val result = StringUtils.getApplicationName(applicationContext)
        Truth.assertThat(result).isEqualTo("AndroidTestingSamples")
    }
}