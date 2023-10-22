package com.marinj.shoppingwarfare.core.model

import arrow.core.left
import com.marinj.shoppingwarfare.core.model.NonEmptyString.Companion.NonEmptyString
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import io.kotest.matchers.shouldBe
import org.junit.Test

class NonEmptyStringTest {

    @Test
    fun `of SHOULD return Left WHEN provided value is null`() {
        val expectedResult = ErrorMessage("NonEmptyString can not be null or empty").left()

        val result = NonEmptyString(valueToValidate = null)

        result shouldBe expectedResult
    }

    @Test
    fun `of SHOULD return Left with tag WHEN provided value is null and tag is provided`() {
        val expectedResult = ErrorMessage("$TAG can not be null or empty").left()

        val result = NonEmptyString(valueToValidate = null, tag = TAG)

        result shouldBe expectedResult
    }

    @Test
    fun `of SHOULD return Left WHEN provided value is empty`() {
        val expectedResult = ErrorMessage("NonEmptyString can not be null or empty").left()

        val result = NonEmptyString(valueToValidate = "")

        result shouldBe expectedResult
    }

    @Test
    fun `of SHOULD return Left WHEN provided value is blank`() {
        val expectedResult = ErrorMessage("NonEmptyString can not be null or empty").left()

        val result = NonEmptyString(valueToValidate = "         ")

        result shouldBe expectedResult
    }

    @Test
    fun `of SHOULD return Right WHEN provided value passes validation`() {
        val someValue = "someValue"

        val result = NonEmptyString(valueToValidate = someValue)

        result.map { mappedValue ->
            mappedValue.value shouldBe someValue
        }
    }
}

private const val TAG = "TAG"
