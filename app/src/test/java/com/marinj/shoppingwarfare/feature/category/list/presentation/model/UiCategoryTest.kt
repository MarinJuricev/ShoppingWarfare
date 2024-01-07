package com.marinj.shoppingwarfare.feature.category.list.presentation.model

import androidx.compose.ui.graphics.Color
import arrow.core.left
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.fixtures.category.buildCategory
import com.marinj.shoppingwarfare.fixtures.category.buildUiCategory
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class UiCategoryTest {

    @Test
    fun `toDomain SHOULD return Left WHEN title is empty`() {
        val expectedResult = Failure.ErrorMessage("title can not be null or empty").left()
        val uiCategory = buildUiCategory(providedTitle = "")

        val result = uiCategory.toDomain()

        result shouldBe expectedResult
    }

    @Test
    fun `toDomain SHOULD return Left WHEN title is blank`() {
        val expectedResult = Failure.ErrorMessage("title can not be null or empty").left()
        val uiCategory = buildUiCategory(providedTitle = " ")

        val result = uiCategory.toDomain()

        result shouldBe expectedResult
    }

    @Test
    fun `toDomain SHOULD return Right WHEN category validation passes`() {
        val expectedResult = buildCategory(
            providedBackgroundColor = BACKGROUND_COLOR,
            providedTitleColor = TITLE_COLOR,
        ).right()
        val uiCategory = buildUiCategory(
            providedBackgroundColor = BACKGROUND_COLOR,
            providedTitleColor = TITLE_COLOR,
        )

        val result = uiCategory.toDomain()

        result shouldBe expectedResult
    }

    @Test
    fun `toUi SHOULD map Id`() {
        val expectedResult = ID
        val category = buildCategory(providedCategoryId = ID)

        val result = category.toUi()

        result.id shouldBe expectedResult
    }

    @Test
    fun `toUi SHOULD map title`() {
        val expectedResult = TITLE
        val category = buildCategory(providedTitle = TITLE)

        val result = category.toUi()

        result.title shouldBe expectedResult
    }

    @Test
    fun `toUi SHOULD map backgroundColor`() {
        val expectedResult = Color(BACKGROUND_COLOR)
        val category = buildCategory(providedBackgroundColor = BACKGROUND_COLOR)

        val result = category.toUi()

        result.backgroundColor shouldBe expectedResult
    }

    @Test
    fun `toUi SHOULD map titleColor`() {
        val expectedResult = Color(TITLE_COLOR)
        val category = buildCategory(providedTitleColor = TITLE_COLOR)

        val result = category.toUi()

        result.titleColor shouldBe expectedResult
    }
}

private const val ID = "id"
private const val TITLE = "title"
private const val BACKGROUND_COLOR = 1
private const val TITLE_COLOR = 1
