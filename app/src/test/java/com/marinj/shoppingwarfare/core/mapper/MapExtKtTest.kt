package com.marinj.shoppingwarfare.core.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.ext.getOrEmpty
import com.marinj.shoppingwarfare.core.ext.getOrFalse
import com.marinj.shoppingwarfare.core.ext.getOrZero
import org.junit.Test

class MapExtKtTest {

    @Test
    fun `getOrEmpty SHOULD return value from map when key is present in the map`() {
        val sut = mapOf(KEY to "value")

        val result = sut.getOrEmpty(KEY)

        assertThat(result).isEqualTo("value")
    }

    @Test
    fun `getOrEmpty SHOULD return empty string from map when key is not present in the map`() {
        val sut = mapOf(KEY to "value")

        val result = sut.getOrEmpty("randomKey")

        assertThat(result).isEmpty()
    }

    @Test
    fun `getOrZero SHOULD return value from map when key is present in the map`() {
        val sut = mapOf(KEY to 1)

        val result = sut.getOrZero(KEY)

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `getOrZero SHOULD return empty string from map when key is not present in the map`() {
        val sut = mapOf(KEY to 1)

        val result = sut.getOrZero("randomKey")

        assertThat(result).isEqualTo(0)
    }

    @Test
    fun `getOrFalse SHOULD return value from map when key is present in the map`() {
        val sut = mapOf(KEY to true)

        val result = sut.getOrFalse(KEY)

        assertThat(result).isTrue()
    }

    @Test
    fun `getOrFalse SHOULD return empty string from map when key is not present in the map`() {
        val sut = mapOf(KEY to true)

        val result = sut.getOrFalse("randomKey")

        assertThat(result).isFalse()
    }
}

private const val KEY = "key"
