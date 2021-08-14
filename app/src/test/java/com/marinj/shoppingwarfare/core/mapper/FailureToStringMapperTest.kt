package com.marinj.shoppingwarfare.core.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FailureToStringMapperTest {

    private lateinit var sut: Mapper<String, Failure>

    @Before
    fun setUp() {
        sut = FailureToStringMapper()
    }

    @Test
    fun `map should return provided errorMessage when failure is of type ErrorMessage`() =
        runBlockingTest {
            val errorMessage = "errorMessage"
            val origin = Failure.ErrorMessage(errorMessage)

            val actualResult = sut.map(origin)

            assertThat(actualResult).isEqualTo(errorMessage)
        }

    @Test
    fun `map should return generic error when failure is not of type ErrorMessage`() =
        runBlockingTest {
            val origin = Failure.Unknown

            val actualResult = sut.map(origin)
            val expectedResult = "Unknown Error Occurred, please try again later"

            assertThat(actualResult).isEqualTo(expectedResult)
        }
}