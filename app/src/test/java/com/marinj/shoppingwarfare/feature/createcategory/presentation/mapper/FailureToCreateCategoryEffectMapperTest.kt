package com.marinj.shoppingwarfare.feature.createcategory.presentation.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEffect
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEffect.CreateCategoryFailure
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val ERROR_MESSAGE = "errorMessage"

@ExperimentalCoroutinesApi
class FailureToCreateCategoryEffectMapperTest {

    private lateinit var sut: Mapper<CreateCategoryEffect, Failure>

    @Before
    fun setUp() {
        sut = FailureToCreateCategoryEffectMapper()
    }

    @Test
    fun `map should return CreateCategoryFailure when origin is ErrorMessage`() = runBlockingTest {
        val origin = mockk<Failure.ErrorMessage>()
        every { origin.errorMessage } answers { ERROR_MESSAGE }

        val actualResult = sut.map(origin)
        val expectedResult = CreateCategoryFailure(ERROR_MESSAGE)

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `map should return CreateCategoryFailure when origin is Unknown`() = runBlockingTest {
        val mapperErrorMessage = "Unknown Error Occurred, please try again later"
        val origin = mockk<Failure.ErrorMessage>()
        every { origin.errorMessage } answers { mapperErrorMessage }

        val actualResult = sut.map(origin)
        val expectedResult = CreateCategoryFailure(mapperErrorMessage)

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}