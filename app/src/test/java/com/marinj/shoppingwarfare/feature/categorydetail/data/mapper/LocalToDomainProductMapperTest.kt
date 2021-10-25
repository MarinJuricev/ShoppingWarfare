package com.marinj.shoppingwarfare.feature.categorydetail.data.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalCategoryProducts
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalProduct
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

private const val ID = "id"
private const val CATEGORY_ID = "categoryId"
private const val CATEGORY_NAME = "fruits"
private const val NAME = "name"

class LocalToDomainProductMapperTest {

    private lateinit var sut: LocalCategoryProductsListToDomainProductMapper

    @Before
    fun setUp() {
        sut = LocalCategoryProductsListToDomainProductMapper()
    }

    @Test
    fun `map should map productId`() {
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
    fun `map should map categoryProductId`() {
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
    fun `map should map categoryName`() {
        val localProduct = mockk<LocalProduct>(relaxed = true).apply {
            every { categoryName } answers { CATEGORY_NAME }
        }
        val listOfLocalProducts = listOf(localProduct)
        val localCategoryProducts = mockk<LocalCategoryProducts>(relaxed = true).apply {
            every { productList } answers { listOfLocalProducts }
        }
        val listOfLocalCategoryProducts = listOf(localCategoryProducts)

        val actualResult = sut.map(listOfLocalCategoryProducts)

        assertThat(actualResult.first().categoryName).isEqualTo(CATEGORY_NAME)
    }

    @Test
    fun `map should map name`() {
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
