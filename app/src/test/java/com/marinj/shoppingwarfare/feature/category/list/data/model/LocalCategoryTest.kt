package com.marinj.shoppingwarfare.feature.category.list.data.model

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.fixtures.category.buildCategory
import com.marinj.shoppingwarfare.fixtures.category.buildLocalCategory
import org.junit.Test

class LocalCategoryTest {

    @Test
    fun `toDomain should map id`() {
        val localCategory = buildLocalCategory(providedCategoryId = ID)

        val actualResult = localCategory.toDomain()

        assertThat(actualResult.id).isEqualTo(ID)
    }

    @Test
    fun `toDomain should map title`() {
        val localCategory = buildLocalCategory(providedTitle = TITLE)

        val actualResult = localCategory.toDomain()

        assertThat(actualResult.title).isEqualTo(TITLE)
    }

    @Test
    fun `toDomain should map backgroundColor`() {
        val localCategory = buildLocalCategory(providedBackgroundColor = BACKGROUND_COLOR)

        val actualResult = localCategory.toDomain()

        assertThat(actualResult.backgroundColor).isEqualTo(BACKGROUND_COLOR)
    }

    @Test
    fun `toDomain should map titleColor`() {
        val localCategory = buildLocalCategory(providedTitleColor = TITLE_COLOR)

        val actualResult = localCategory.toDomain()

        assertThat(actualResult.titleColor).isEqualTo(TITLE_COLOR)
    }

    @Test
    fun `toLocal should map id`() {
        val category = buildCategory(providedId = ID)

        val actualResult = category.toLocal()

        assertThat(actualResult.categoryId).isEqualTo(ID)
    }

    @Test
    fun `toLocal should map title`() {
        val category = buildCategory(providedTitle = TITLE)

        val actualResult = category.toLocal()

        assertThat(actualResult.title).isEqualTo(TITLE)
    }

    @Test
    fun `toLocal should map backgroundColor`() {
        val category = buildCategory(providedBackgroundColor = BACKGROUND_COLOR)

        val actualResult = category.toLocal()

        assertThat(actualResult.backgroundColor).isEqualTo(BACKGROUND_COLOR)
    }

    @Test
    fun `toLocal should map titleColor`() {
        val category = buildCategory(providedTitleColor = TITLE_COLOR)

        val actualResult = category.toLocal()

        assertThat(actualResult.titleColor).isEqualTo(TITLE_COLOR)
    }
}

private const val ID = "id"
private const val TITLE = "title"
private const val BACKGROUND_COLOR = 0
private const val TITLE_COLOR = 1
