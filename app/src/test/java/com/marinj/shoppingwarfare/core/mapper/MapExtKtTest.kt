package com.marinj.shoppingwarfare.core.mapper

import com.marinj.shoppingwarfare.core.ext.getOrEmpty
import com.marinj.shoppingwarfare.core.ext.getOrFalse
import com.marinj.shoppingwarfare.core.ext.getOrZero
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import org.junit.Test

class MapExtKtTest {

    @Test
    fun `getOrEmpty SHOULD return value from map when key is present in the map`() {
        val sut = mapOf(KEY to "value")

        val result = sut.getOrEmpty(KEY)

        result shouldBe "value"
    }

    @Test
    fun `getOrEmpty SHOULD return empty string from map when key is not present in the map`() {
        val sut = mapOf(KEY to "value")

        val result = sut.getOrEmpty("randomKey")

        result.shouldBeEmpty()
    }

    @Test
    fun `getOrZero SHOULD return value from map when key is present in the map`() {
        val sut = mapOf(KEY to 1)

        val result = sut.getOrZero(KEY)

        result shouldBe 1
    }

    @Test
    fun `getOrZero SHOULD return empty string from map when key is not present in the map`() {
        val sut = mapOf(KEY to 1)

        val result = sut.getOrZero("randomKey")

        result shouldBe 0
    }

    @Test
    fun `getOrFalse SHOULD return value from map when key is present in the map`() {
        val sut = mapOf(KEY to true)

        val result = sut.getOrFalse(KEY)

        result.shouldBeTrue()
    }

    @Test
    fun `getOrFalse SHOULD return empty string from map when key is not present in the map`() {
        val sut = mapOf(KEY to true)

        val result = sut.getOrFalse("randomKey")

        result.shouldBeFalse()
    }
}

private const val KEY = "key"
