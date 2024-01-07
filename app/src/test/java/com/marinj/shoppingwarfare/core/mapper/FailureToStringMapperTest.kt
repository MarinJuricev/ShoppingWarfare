package com.marinj.shoppingwarfare.core.mapper

import com.marinj.shoppingwarfare.core.result.Failure
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class FailureToStringMapperTest {

    private val sut = FailureToStringMapper()

    @Test
    fun `map SHOULD return provided errorMessage when failure is of type ErrorMessage`() {
        val errorMessage = "errorMessage"
        val origin = Failure.ErrorMessage(errorMessage)

        val actualResult = sut.map(origin)

        actualResult shouldBe errorMessage
    }

    @Test
    fun `map SHOULD return generic error when failure is not of type ErrorMessage`() {
        val origin = Failure.Unknown

        val actualResult = sut.map(origin)
        val expectedResult = "Unknown Error Occurred, please try again later"

        actualResult shouldBe expectedResult
    }
}
