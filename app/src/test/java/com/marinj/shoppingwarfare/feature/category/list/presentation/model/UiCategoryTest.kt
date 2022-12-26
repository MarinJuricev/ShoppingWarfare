package com.marinj.shoppingwarfare.feature.category.list.presentation.model

import androidx.compose.ui.graphics.Color
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.UiCategory.Companion.toUi
import com.marinj.shoppingwarfare.fixtures.category.buildCategory
import com.marinj.shoppingwarfare.fixtures.category.buildUiCategory
import org.junit.Test

class UiCategoryTest {

    @Test
    fun `toDomain SHOULD return Left WHEN title is empty`() {
        val expectedResult = Failure.ErrorMessage("Title can not be empty or null got: ").buildLeft()
        val uiCategory = buildUiCategory(providedTitle = "")

        val result = uiCategory.toDomain()

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `toDomain SHOULD return Left WHEN title is blank`() {
        val expectedResult = Failure.ErrorMessage("Title can not be empty or null got:  ").buildLeft()
        val uiCategory = buildUiCategory(providedTitle = " ")

        val result = uiCategory.toDomain()

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `toDomain SHOULD return Right WHEN category validation passes `() {
        val expectedResult = buildCategory(providedTitle = TITLE).buildRight()
        val uiCategory = buildUiCategory(providedTitle = TITLE)

        val result = uiCategory.toDomain()

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `toUi SHOULD map Id`() {
        val expectedResult = ID
        val category = buildCategory(providedId = ID)

        val result = category.toUi()

        assertThat(result.id).isEqualTo(expectedResult)
    }

    @Test
    fun `toUi SHOULD map title`() {
        val expectedResult = TITLE
        val category = buildCategory(providedTitle = TITLE)

        val result = category.toUi()

        assertThat(result.title).isEqualTo(expectedResult)
    }

    @Test
    fun `toUi SHOULD map backgroundColor`() {
        val expectedResult = Color(BACKGROUND_COLOR)
        val category = buildCategory(providedBackgroundColor = BACKGROUND_COLOR)

        val result = category.toUi()

        assertThat(result.backgroundColor).isEqualTo(expectedResult)
    }

    @Test
    fun `toUi SHOULD map titleColor`() {
        val expectedResult = Color(TITLE_COLOR)
        val category = buildCategory(providedBackgroundColor = TITLE_COLOR)

        val result = category.toUi()

        assertThat(result.titleColor).isEqualTo(expectedResult)
    }
}

private const val ID = "id"
private const val TITLE = "title"
private const val BACKGROUND_COLOR = 0
private const val TITLE_COLOR = 0