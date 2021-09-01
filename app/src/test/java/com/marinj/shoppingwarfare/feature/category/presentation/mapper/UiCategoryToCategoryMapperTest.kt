package com.marinj.shoppingwarfare.feature.category.presentation.mapper

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

private const val ID = "id"
private const val TITLE = "TITLE"
private const val COLOR_ID = 1

@ExperimentalCoroutinesApi
class UiCategoryToCategoryMapperTest {

    private lateinit var sut: Mapper<Category, UiCategory>

    @Before
    fun setUp() {
        sut = UiCategoryToCategoryMapper()
    }

    @Test
    fun `map should map id`() = runBlockingTest {
        val uiCategory = mockk<UiCategory>(relaxed = true).apply {
            every { id } answers { ID }
        }

        val actualResult = sut.map(uiCategory)

        assertThat(actualResult.id).isEqualTo(ID)
    }

    @Test
    fun `map should map title`() = runBlockingTest {
        val uiCategory = mockk<UiCategory>(relaxed = true).apply {
            every { title } answers { TITLE }
        }

        val actualResult = sut.map(uiCategory)

        assertThat(actualResult.title).isEqualTo(TITLE)
    }

//    TODO: Investigate why this throws an exception
//    @Test
//    fun `map should map backgroundColor`() = runBlockingTest {
//        val color = Color.Black
//        val convertedColor = color.toArgb()
//        val uiCategory = mockk<UiCategory>(relaxed = true).apply {
//            every { backgroundColor } answers { color }
//        }
//
//        val actualResult = sut.map(uiCategory)
//
//        assertThat(actualResult.backgroundColor).isEqualTo(convertedColor)
//    }
//
//    @Test
//    fun `map should map titleColor`() = runBlockingTest {
//        val color = Color.Cyan
//        val convertedColor = color.toArgb()
//        val uiCategory = mockk<UiCategory>(relaxed = true).apply {
//            coEvery { titleColor } coAnswers { color }
//        }
//
//        val actualResult = sut.map(uiCategory)
//
//        assertThat(actualResult.titleColor).isEqualTo(convertedColor)
//    }
}
