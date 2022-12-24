package com.marinj.shoppingwarfare.feature.category.list.presentation.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.UiCategory
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

private const val ID = "id"
private const val TITLE = "TITLE"

class UiCategoryToCategoryMapperTest {

    private lateinit var sut: UiCategoryToCategoryMapper

    @Before
    fun setUp() {
        sut = UiCategoryToCategoryMapper()
    }

    @Test
    fun `map should map id`() {
        val uiCategory = mockk<UiCategory>(relaxed = true).apply {
            every { id } answers { ID }
        }

        val actualResult = sut.map(uiCategory)

        assertThat(actualResult?.id).isEqualTo(ID)
    }

    @Test
    fun `map should map title`() {
        val uiCategory = mockk<UiCategory>(relaxed = true).apply {
            every { title } answers { TITLE }
        }

        val actualResult = sut.map(uiCategory)

        assertThat(actualResult?.title).isEqualTo(TITLE)
    }

//    TODO: Investigate why this throws an exception
//    @Test
//    fun `map should map backgroundColor`()  {
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
//    fun `map should map titleColor`()  {
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
