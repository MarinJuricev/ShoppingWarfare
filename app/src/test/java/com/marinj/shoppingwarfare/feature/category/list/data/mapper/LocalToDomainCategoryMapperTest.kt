package com.marinj.shoppingwarfare.feature.category.list.data.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.category.list.data.model.LocalCategory
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

private const val ID = "id"
private const val TITLE = "title"
private const val BACKGROUND_COLOR = 0
private const val TITLE_COLOR = 1

class LocalToDomainCategoryMapperTest {

    private lateinit var sut: LocalToDomainCategoryMapper

    @Before
    fun setUp() {
        sut = LocalToDomainCategoryMapper()
    }

    @Test
    fun `map should map id`() {
        val localCategory = mockk<LocalCategory>(relaxed = true).apply {
            every { categoryId } answers { ID }
        }

        val actualResult = sut.map(localCategory)

        assertThat(actualResult.id).isEqualTo(ID)
    }

    @Test
    fun `map should map title`() {
        val localCategory = mockk<LocalCategory>(relaxed = true).apply {
            every { title } answers { TITLE }
        }

        val actualResult = sut.map(localCategory)

        assertThat(actualResult.title).isEqualTo(TITLE)
    }

    @Test
    fun `map should map backgroundColor`() {
        val localCategory = mockk<LocalCategory>(relaxed = true).apply {
            every { backgroundColor } answers { BACKGROUND_COLOR }
        }

        val actualResult = sut.map(localCategory)

        assertThat(actualResult.backgroundColor).isEqualTo(BACKGROUND_COLOR)
    }

    @Test
    fun `map should map titleColor`() {
        val localCategory = mockk<LocalCategory>(relaxed = true).apply {
            every { titleColor } answers { TITLE_COLOR }
        }

        val actualResult = sut.map(localCategory)

        assertThat(actualResult.titleColor).isEqualTo(TITLE_COLOR)
    }
}
