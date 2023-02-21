package com.example.testing

import com.google.common.truth.Truth
import me.yamlee.testing.BaseUnitTest
import me.yamlee.testing.samples.MathUtils
import org.junit.Test

class MathUtilsTest : BaseUnitTest() {
    @Test
    fun add() {
        val result = MathUtils.add(1, 1)
        Truth.assertThat(result).isEqualTo(2)
    }
}