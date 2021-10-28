package com.marinj.shoppingwarfare.feature.category.list.presentation.mapper

import androidx.compose.ui.graphics.Color
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

private const val ID = "id"
private const val TITLE = "title"
private const val BACKGROUND_COLOR = 0
private const val TITLE_COLOR = 1

class CategoryToUiCategoryMapperTest {

    private lateinit var sut: CategoryToUiCategoryMapper

    @Before
    fun setUp() {
        sut = CategoryToUiCategoryMapper()
    }

    @Test
    fun `map should map id`() {
        val category = mockk<Category>(relaxed = true).apply {
            every { id } answers { ID }
        }

        val actualResult = sut.map(category)

        assertThat(actualResult.id).isEqualTo(ID)
    }

    @Test
    fun `map should map title`() {
        val category = mockk<Category>(relaxed = true).apply {
            every { title } answers { TITLE }
        }

        val actualResult = sut.map(category)

        assertThat(actualResult.title).isEqualTo(TITLE)
    }

    @Test
    fun `map should map backgroundColor`() {
        val category = mockk<Category>(relaxed = true).apply {
            every { backgroundColor } answers { BACKGROUND_COLOR }
        }

        val actualResult = sut.map(category)
        val expectedColor = Color(BACKGROUND_COLOR)

        assertThat(actualResult.backgroundColor).isEqualTo(expectedColor)
    }

    @Test
    fun `map should map titleColor`() {
        val category = mockk<Category>(relaxed = true).apply {
            every { titleColor } answers { TITLE_COLOR }
        }

        val actualResult = sut.map(category)
        val expectedColor = Color(TITLE_COLOR)

        assertThat(actualResult.titleColor).isEqualTo(expectedColor)
    }
}
