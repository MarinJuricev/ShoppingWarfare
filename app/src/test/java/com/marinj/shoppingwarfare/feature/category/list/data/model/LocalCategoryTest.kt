package com.marinj.shoppingwarfare.feature.category.list.data.model

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.takeRightOrNull
import com.marinj.shoppingwarfare.fixtures.category.buildCategory
import com.marinj.shoppingwarfare.fixtures.category.buildLocalCategory
import org.junit.Test

class LocalCategoryTest {

    @Test
    fun `toDomain SHOULD map id`() {
        val localCategory = buildLocalCategory(
            providedCategoryId = ID,
            providedTitle = TITLE,
        )

        val actualResult = localCategory.toDomain()

        assertThat(actualResult.takeRightOrNull()?.id).isEqualTo(ID)
    }

    @Test
    fun `toDomain SHOULD map title`() {
        val localCategory = buildLocalCategory(providedTitle = TITLE)

        val actualResult = localCategory.toDomain()

        assertThat(actualResult.takeRightOrNull()?.title).isEqualTo(TITLE)
    }

    @Test
    fun `toDomain SHOULD map backgroundColor`() {
        val localCategory = buildLocalCategory(
            providedBackgroundColor = BACKGROUND_COLOR,
            providedTitle = TITLE,
        )

        val actualResult = localCategory.toDomain()

        assertThat(actualResult.takeRightOrNull()?.backgroundColor).isEqualTo(BACKGROUND_COLOR)
    }

    @Test
    fun `toDomain SHOULD map titleColor`() {
        val localCategory = buildLocalCategory(
            providedTitleColor = TITLE_COLOR,
            providedTitle = TITLE,
        )

        val actualResult = localCategory.toDomain()

        assertThat(actualResult.takeRightOrNull()?.titleColor).isEqualTo(TITLE_COLOR)
    }

    @Test
    fun `toLocal SHOULD map id`() {
        val category = buildCategory(providedId = ID)

        val actualResult = category?.toLocal()

        assertThat(actualResult?.categoryId).isEqualTo(ID)
    }

    @Test
    fun `toLocal SHOULD map title`() {
        val category = buildCategory(providedTitle = TITLE)

        val actualResult = category?.toLocal()

        assertThat(actualResult?.title).isEqualTo(TITLE)
    }

    @Test
    fun `toLocal SHOULD map backgroundColor`() {
        val category = buildCategory(providedBackgroundColor = BACKGROUND_COLOR)

        val actualResult = category?.toLocal()

        assertThat(actualResult?.backgroundColor).isEqualTo(BACKGROUND_COLOR)
    }

    @Test
    fun `toLocal SHOULD map titleColor`() {
        val category = buildCategory(providedTitleColor = TITLE_COLOR)

        val actualResult = category?.toLocal()

        assertThat(actualResult?.titleColor).isEqualTo(TITLE_COLOR)
    }
}

private const val ID = "id"
private const val TITLE = "title"
private const val BACKGROUND_COLOR = 0
private const val TITLE_COLOR = 1
