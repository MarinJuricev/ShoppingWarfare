package com.marinj.shoppingwarfare.feature.categorydetail.data.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalCategoryItem
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.CategoryItem
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val ID = "id"
private const val NAME = "name"

@ExperimentalCoroutinesApi
class DomainToLocalCategoryItemMapperTest {

    private lateinit var sut: Mapper<LocalCategoryItem, CategoryItem>

    @Before
    fun setUp() {
        sut = DomainToLocalCategoryItemMapper()
    }

    @Test
    fun `map should map id`() = runBlockingTest {
        val categoryItem = mockk<CategoryItem>(relaxed = true).apply {
            every { id } answers { ID }
        }

        val actualResult = sut.map(categoryItem)

        assertThat(actualResult.categoryItemId).isEqualTo(ID)
    }

    @Test
    fun `map should map name`() = runBlockingTest {
        val categoryItem = mockk<CategoryItem>(relaxed = true).apply {
            every { name } answers { NAME }
        }

        val actualResult = sut.map(categoryItem)

        assertThat(actualResult.name).isEqualTo(NAME)
    }
}
