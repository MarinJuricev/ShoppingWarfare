package com.marinj.shoppingwarfare.feature.category.createcategory.domain.validator

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.createcategory.domain.usecase.ValidateCategory
import org.junit.Before
import org.junit.Test

class ValidateCategoryTest {

    private lateinit var sut: ValidateCategory

    @Before
    fun setUp() {
        sut = ValidateCategory()
    }

    @Test
    fun `invoke should return FailureErrorMessage when title is null`() {
        val actualResult = sut(title = null, categoryColor = 1, titleColor = 1)
        val expectedResult = ErrorMessage("Title can't be empty").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `invoke should return FailureErrorMessage when title is empty`() {
        val actualResult = sut(title = "", categoryColor = 1, titleColor = 1)
        val expectedResult = ErrorMessage("Title can't be empty").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `invoke should return FailureErrorMessage when categoryColor is null`() {
        val actualResult = sut(title = "title", categoryColor = null, titleColor = 1)
        val expectedResult = ErrorMessage("Category color can't be empty").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `invoke should return FailureErrorMessage when titleColor is null`() {
        val actualResult = sut(title = "title", categoryColor = 1, titleColor = null)
        val expectedResult = ErrorMessage("Title color can't be empty").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `invoke should return RightUnit when validation is a success`() {
        val actualResult = sut(title = "title", categoryColor = 1, titleColor = 1)
        val expectedResult = Unit.buildRight()

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}
