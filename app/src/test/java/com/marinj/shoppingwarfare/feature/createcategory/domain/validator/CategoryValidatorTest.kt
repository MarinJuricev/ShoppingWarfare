package com.marinj.shoppingwarfare.feature.createcategory.domain.validator

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import org.junit.Before
import org.junit.Test

class CategoryValidatorTest {

    private lateinit var sut: CategoryValidator

    @Before
    fun setUp() {
        sut = CategoryValidator()
    }

    @Test
    fun `validate should return FailureErrorMessage when title is null`() {
        val actualResult = sut.validate(title = null, categoryColor = 1)
        val expectedResult = ErrorMessage("Title can't be empty").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `validate should return FailureErrorMessage when title is empty`() {
        val actualResult = sut.validate(title = "", categoryColor = 1)
        val expectedResult = ErrorMessage("Title can't be empty").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `validate should return FailureErrorMessage when categoryColor is null`() {
        val actualResult = sut.validate(title = "title", categoryColor = null)
        val expectedResult = ErrorMessage("Category color can't be empty").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `validate should return RightUnit when validation is a success`() {
        val actualResult = sut.validate(title = "title", categoryColor = 1)
        val expectedResult = Unit.buildRight()

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}