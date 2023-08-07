package com.marinj.shoppingwarfare.feature.category.list.domain.model

import arrow.core.left
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category.Companion.Category
import org.junit.Test

class CategoryTest {

    @Test
    fun `Category SHOULD return Left WHEN title is null`() {
        val expectedResult = ErrorMessage("title can not be null or empty").left()

        val result = Category(
            id = ID,
            title = null,
            backgroundColor = BACKGROUND_COLOR,
            titleColor = TITLE_COLOR,
        )

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `Category SHOULD return Left WHEN title is empty`() {
        val expectedResult = ErrorMessage("title can not be null or empty").left()

        val result = Category(
            id = ID,
            title = "",
            backgroundColor = BACKGROUND_COLOR,
            titleColor = TITLE_COLOR,
        )

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `Category SHOULD return Left WHEN title is blank`() {
        val expectedResult = ErrorMessage("title can not be null or empty").left()

        val result = Category(
            id = ID,
            title = "      ",
            backgroundColor = BACKGROUND_COLOR,
            titleColor = TITLE_COLOR,
        )

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `Category SHOULD return Left WHEN backgroundColor is null`() {
        val expectedResult = ErrorMessage("backgroundColor can not be null").left()

        val result = Category(
            id = ID,
            title = TITLE,
            backgroundColor = null,
            titleColor = TITLE_COLOR,
        )

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `Category SHOULD return Left WHEN titleColor is null`() {
        val expectedResult = ErrorMessage("titleColor can not be null").left()

        val result = Category(
            id = ID,
            title = TITLE,
            backgroundColor = BACKGROUND_COLOR,
            titleColor = null,
        )

        assertThat(result).isEqualTo(expectedResult)
    }
}

private const val ID = "id"
private const val TITLE = "title"
private const val BACKGROUND_COLOR = 0
private const val TITLE_COLOR = 1
