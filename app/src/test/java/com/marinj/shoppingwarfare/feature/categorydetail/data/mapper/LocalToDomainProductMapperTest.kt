package com.marinj.shoppingwarfare.feature.categorydetail.data.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalCategoryProducts
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
private const val NAME = "name"

@ExperimentalCoroutinesApi
class LocalToDomainProductMapperTest {

    private lateinit var sut: Mapper<List<Product>, List<LocalCategoryProducts>>

    @Before
    fun setUp() {
        sut = LocalCategoryProductsListToDomainProductMapper()
    }

    @Test
    fun `map should map productId`() = runBlockingTest {
        val localProduct = mockk<LocalProduct>(relaxed = true).apply {
            every { productId } answers { ID }
        }
        val listOfLocalProducts = listOf(localProduct)
        val localCategoryProducts = mockk<LocalCategoryProducts>(relaxed = true).apply {
            every { productList } answers { listOfLocalProducts }
        }
        val listOfLocalCategoryProducts = listOf(localCategoryProducts)

        val actualResult = sut.map(listOfLocalCategoryProducts)

        assertThat(actualResult.first().id).isEqualTo(ID)
    }

    @Test
    fun `map should map categoryProductId`() = runBlockingTest {
        val localProduct = mockk<LocalProduct>(relaxed = true).apply {
            every { categoryProductId } answers { CATEGORY_ID }
        }
        val listOfLocalProducts = listOf(localProduct)
        val localCategoryProducts = mockk<LocalCategoryProducts>(relaxed = true).apply {
            every { productList } answers { listOfLocalProducts }
        }
        val listOfLocalCategoryProducts = listOf(localCategoryProducts)

        val actualResult = sut.map(listOfLocalCategoryProducts)

        assertThat(actualResult.first().categoryId).isEqualTo(CATEGORY_ID)
    }

    @Test
    fun `map should map name`() = runBlockingTest {
        val localProduct = mockk<LocalProduct>(relaxed = true).apply {
            every { name } answers { NAME }
        }
        val listOfLocalProducts = listOf(localProduct)
        val localCategoryProducts = mockk<LocalCategoryProducts>(relaxed = true).apply {
            every { productList } answers { listOfLocalProducts }
        }
        val listOfLocalCategoryProducts = listOf(localCategoryProducts)

        val actualResult = sut.map(listOfLocalCategoryProducts)

        assertThat(actualResult.first().name).isEqualTo(NAME)
    }
}
