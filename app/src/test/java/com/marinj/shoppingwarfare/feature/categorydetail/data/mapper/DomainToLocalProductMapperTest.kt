package com.marinj.shoppingwarfare.feature.categorydetail.data.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalProduct
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.Product
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val ID = "id"
private const val CATEGORY_ID = "categoryId"
private const val CATEGORY_NAME = "fruits"
private const val NAME = "name"

@ExperimentalCoroutinesApi
class DomainToLocalProductMapperTest {

    private lateinit var sut: Mapper<LocalProduct, Product>

    @Before
    fun setUp() {
        sut = DomainToLocalCategoryItemMapper()
    }

    @Test
    fun `map should map id`() = runBlockingTest {
        val categoryItem = mockk<Product>(relaxed = true).apply {
            every { id } answers { ID }
        }

        val actualResult = sut.map(categoryItem)

        assertThat(actualResult.productId).isEqualTo(ID)
    }

    @Test
    fun `map should map categoryId`() = runBlockingTest {
        val categoryItem = mockk<Product>(relaxed = true).apply {
            every { categoryId } answers { CATEGORY_ID }
        }

        val actualResult = sut.map(categoryItem)

        assertThat(actualResult.categoryProductId).isEqualTo(CATEGORY_ID)
    }

    @Test
    fun `map should map categoryName`() = runBlockingTest {
        val categoryItem = mockk<Product>(relaxed = true).apply {
            every { categoryName } answers { CATEGORY_NAME }
        }

        val actualResult = sut.map(categoryItem)

        assertThat(actualResult.categoryName).isEqualTo(CATEGORY_NAME)
    }

    @Test
    fun `map should map name`() = runBlockingTest {
        val categoryItem = mockk<Product>(relaxed = true).apply {
            every { name } answers { NAME }
        }

        val actualResult = sut.map(categoryItem)

        assertThat(actualResult.name).isEqualTo(NAME)
    }
}
