package com.marinj.shoppingwarfare.core.model

import arrow.core.left
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.model.NonEmptyString.Companion.NonEmptyString
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import org.junit.Test

class NonEmptyStringTest {

    @Test
    fun `of SHOULD return Left WHEN provided value is null`() {
        val expectedResult = ErrorMessage("NonEmptyString can not be null or empty").left()

        val result = NonEmptyString(valueToValidate = null)

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `of SHOULD return Left with tag WHEN provided value is null and tag is provided`() {
        val expectedResult = ErrorMessage("$TAG can not be null or empty").left()

        val result = NonEmptyString(valueToValidate = null, tag = TAG)

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `of SHOULD return Left WHEN provided value is empty`() {
        val expectedResult = ErrorMessage("NonEmptyString can not be null or empty").left()

        val result = NonEmptyString(valueToValidate = "")

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `of SHOULD return Left WHEN provided value is blank`() {
        val expectedResult = ErrorMessage("NonEmptyString can not be null or empty").left()

        val result = NonEmptyString(valueToValidate = "         ")

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `of SHOULD return Right WHEN provided value passes validation`() {
        val someValue = "someValue"

        val result = NonEmptyString(valueToValidate = someValue)

        result.map { mappedValue ->
            assertThat(mappedValue.value).isEqualTo(someValue)
        }
    }
}

private const val TAG = "TAG"
