package com.marinj.shoppingwarfare.feature.categorydetail.data.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.Product
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

private const val ID = "id"
private const val CATEGORY_ID = "categoryId"
private const val CATEGORY_NAME = "fruits"
private const val NAME = "name"

class DomainToLocalProductMapperTest {

    private lateinit var sut: DomainToLocalCategoryItemMapper

    @Before
    fun setUp() {
        sut = DomainToLocalCategoryItemMapper()
    }

    @Test
    fun `map should map id`() {
        val categoryItem = mockk<Product>(relaxed = true).apply {
            every { id } answers { ID }
        }

        val actualResult = sut.map(categoryItem)

        assertThat(actualResult.productId).isEqualTo(ID)
    }

    @Test
    fun `map should map categoryId`() {
        val categoryItem = mockk<Product>(relaxed = true).apply {
            every { categoryId } answers { CATEGORY_ID }
        }

        val actualResult = sut.map(categoryItem)

        assertThat(actualResult.categoryProductId).isEqualTo(CATEGORY_ID)
    }

    @Test
    fun `map should map categoryName`() {
        val categoryItem = mockk<Product>(relaxed = true).apply {
            every { categoryName } answers { CATEGORY_NAME }
        }

        val actualResult = sut.map(categoryItem)

        assertThat(actualResult.categoryName).isEqualTo(CATEGORY_NAME)
    }

    @Test
    fun `map should map name`() {
        val categoryItem = mockk<Product>(relaxed = true).apply {
            every { name } answers { NAME }
        }

        val actualResult = sut.map(categoryItem)

        assertThat(actualResult.name).isEqualTo(NAME)
    }
}
