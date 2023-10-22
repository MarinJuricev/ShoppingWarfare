package com.marinj.shoppingwarfare.feature.category.list.data.model

import com.marinj.shoppingwarfare.core.model.NonEmptyString.Companion.NonEmptyString
import com.marinj.shoppingwarfare.core.model.ResourceColor.Companion.ResourceColor
import com.marinj.shoppingwarfare.fixtures.category.buildCategory
import com.marinj.shoppingwarfare.fixtures.category.buildLocalCategory
import io.kotest.matchers.shouldBe
import org.junit.Test

class LocalCategoryTest {

    @Test
    fun `toDomain SHOULD map id`() {
        val expectedResult = NonEmptyString(valueToValidate = ID).getOrNull()
        val localCategory = buildLocalCategory(providedCategoryId = ID)

        val actualResult = localCategory.toDomain()

        actualResult.getOrNull()?.id shouldBe expectedResult
    }

    @Test
    fun `toDomain SHOULD map title`() {
        val expectedResult = NonEmptyString(valueToValidate = TITLE).getOrNull()
        val localCategory = buildLocalCategory(providedTitle = TITLE)

        val actualResult = localCategory.toDomain()

        actualResult.getOrNull()?.title shouldBe expectedResult
    }

    @Test
    fun `toDomain SHOULD map backgroundColor`() {
        val expectedResult = ResourceColor(valueToValidate = BACKGROUND_COLOR).getOrNull()
        val localCategory = buildLocalCategory(providedBackgroundColor = BACKGROUND_COLOR)

        val actualResult = localCategory.toDomain()

        actualResult.getOrNull()?.backgroundColor shouldBe expectedResult
    }

    @Test
    fun `toDomain SHOULD map titleColor`() {
        val expectedResult = ResourceColor(valueToValidate = TITLE_COLOR).getOrNull()
        val localCategory = buildLocalCategory(providedTitleColor = TITLE_COLOR)

        val actualResult = localCategory.toDomain()

        actualResult.getOrNull()?.titleColor shouldBe expectedResult
    }

    @Test
    fun `toLocal SHOULD map id`() {
        val category = buildCategory(providedCategoryId = ID)

        val actualResult = category.toLocal()

        actualResult.categoryId shouldBe ID
    }

    @Test
    fun `toLocal SHOULD map title`() {
        val category = buildCategory(providedTitle = TITLE)

        val actualResult = category.toLocal()

        actualResult.title shouldBe TITLE
    }

    @Test
    fun `toLocal SHOULD map backgroundColor`() {
        val category = buildCategory(providedBackgroundColor = BACKGROUND_COLOR)

        val actualResult = category.toLocal()

        actualResult.backgroundColor shouldBe BACKGROUND_COLOR
    }

    @Test
    fun `toLocal SHOULD map titleColor`() {
        val category = buildCategory(providedTitleColor = TITLE_COLOR)

        val actualResult = category.toLocal()

        actualResult.titleColor shouldBe TITLE_COLOR
    }
}

private const val ID = "id"
private const val TITLE = "title"
private const val BACKGROUND_COLOR = 1
private const val TITLE_COLOR = 2
