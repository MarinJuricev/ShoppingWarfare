package com.marinj.shoppingwarfare.core.model

import arrow.core.left
import com.marinj.shoppingwarfare.core.model.ResourceColor.Companion.ResourceColor
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import io.kotest.matchers.shouldBe
import org.junit.Test

class ResourceColorTest {

    @Test
    fun `ResourceColor should return Left WHEN valueToValidate is null`() {
        val valueToValidate = null
        val expectedResult = ErrorMessage("ResourceColor can not be null").left()

        val result = ResourceColor(valueToValidate = valueToValidate)

        result shouldBe expectedResult
    }

    @Test
    fun `ResourceColor should return Right WHEN valueToValidate passes the validation`() {
        val valueToValidate = 1

        val result = ResourceColor(valueToValidate = valueToValidate)

        result.map { mappedValued ->
            mappedValued.value shouldBe valueToValidate
        }
    }
}
