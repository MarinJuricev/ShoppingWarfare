package com.marinj.shoppingwarfare.feature.category.presentation.mapper

import androidx.compose.ui.graphics.Color
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.presentation.model.UiCategory
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val TITLE = "title"
private const val BACKGROUND_COLOR = 0
private const val TITLE_COLOR = 1

@ExperimentalCoroutinesApi
class CategoryToUiCategoryMapperTest {

    private lateinit var sut: Mapper<UiCategory, Category>

    @Before
    fun setUp() {
        sut = CategoryToUiCategoryMapper()
    }

    @Test
    fun `map should map title`() = runBlockingTest {
        val category = mockk<Category>(relaxed = true).apply {
            every { title } answers { TITLE }
        }

        val actualResult = sut.map(category)

        assertThat(actualResult.title).isEqualTo(TITLE)
    }

    @Test
    fun `map should map backgroundColor`() = runBlockingTest {
        val category = mockk<Category>(relaxed = true).apply {
            every { backgroundColor } answers { BACKGROUND_COLOR }
        }

        val actualResult = sut.map(category)
        val expectedColor = Color(BACKGROUND_COLOR)

        assertThat(actualResult.backgroundColor).isEqualTo(expectedColor)
    }

    @Test
    fun `map should map titleColor`() = runBlockingTest {
        val category = mockk<Category>(relaxed = true).apply {
            every { titleColor } answers { TITLE_COLOR }
        }

        val actualResult = sut.map(category)
        val expectedColor = Color(TITLE_COLOR)

        assertThat(actualResult.titleColor).isEqualTo(expectedColor)
    }

}