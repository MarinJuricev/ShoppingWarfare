package com.marinj.shoppingwarfare.core.model

import arrow.core.left
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.model.ResourceColor.Companion.ResourceColor
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import org.junit.Test

class ResourceColorTest {

    @Test
    fun `ResourceColor should return Left WHEN valueToValidate is null`() {
        val valueToValidate = null
        val expectedResult = ErrorMessage("ResourceColor can not be null").left()

        val result = ResourceColor(valueToValidate = valueToValidate)

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `ResourceColor should return Left WHEN valueToValidate is zero`() {
        val valueToValidate = 0
        val expectedResult = ErrorMessage("ResourceColor value must be a positive number, got: $valueToValidate").left()

        val result = ResourceColor(valueToValidate = valueToValidate)

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `ResourceColor should return Left WHEN valueToValidate is a negative number`() {
        val valueToValidate = -1
        val expectedResult = ErrorMessage("ResourceColor value must be a positive number, got: $valueToValidate").left()

        val result = ResourceColor(valueToValidate = valueToValidate)

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `ResourceColor should return Right WHEN valueToValidate passes the validation`() {
        val valueToValidate = 1

        val result = ResourceColor(valueToValidate = valueToValidate)

        result.map { mappedValued ->
            assertThat(mappedValued.value).isEqualTo(valueToValidate)
        }
    }
}
