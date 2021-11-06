package com.marinj.shoppingwarfare.feature.category.createcategory.presentation.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryViewEffect.CreateCategoryViewFailure
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

private const val ERROR_MESSAGE = "errorMessage"

class FailureToCreateCategoryViewEffectMapperTest {

    private lateinit var sut: FailureToCreateCategoryEffectMapper

    @Before
    fun setUp() {
        sut = FailureToCreateCategoryEffectMapper()
    }

    @Test
    fun `map should return CreateCategoryFailure when origin is ErrorMessage`() {
        val origin = mockk<Failure.ErrorMessage>()
        every { origin.errorMessage } answers { ERROR_MESSAGE }

        val actualResult = sut.map(origin)
        val expectedResult = CreateCategoryViewFailure(ERROR_MESSAGE)

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `map should return CreateCategoryFailure when origin is Unknown`() {
        val mapperErrorMessage = "Unknown Error Occurred, please try again later"
        val origin = mockk<Failure.ErrorMessage>()
        every { origin.errorMessage } answers { mapperErrorMessage }

        val actualResult = sut.map(origin)
        val expectedResult = CreateCategoryViewFailure(mapperErrorMessage)

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}
