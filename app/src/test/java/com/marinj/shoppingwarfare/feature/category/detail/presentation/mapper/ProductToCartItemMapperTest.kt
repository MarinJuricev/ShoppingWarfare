package com.marinj.shoppingwarfare.feature.category.detail.presentation.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

private const val ID = "id"
private const val NAME = "name"
private const val CATEGORY_NAME = "fruits"

class ProductToCartItemMapperTest {

    private lateinit var sut: ProductToCartItemMapper

    @Before
    fun setUp() {
        sut = ProductToCartItemMapper()
    }

    @Test
    fun `map should map id`() = runTest {
        val product = mockk<Product>(relaxed = true).apply {
            every { id } returns ID
        }

        val actualResult = sut.map(product)

        assertThat(actualResult.id).isEqualTo(ID)
    }

    @Test
    fun `map should map name`() {
        val product = mockk<Product>(relaxed = true).apply {
            every { name } returns NAME
        }

        val actualResult = sut.map(product)

        assertThat(actualResult.name).isEqualTo(NAME)
    }

    @Test
    fun `map should map categoryName`() {
        val product = mockk<Product>(relaxed = true).apply {
            every { categoryName } returns CATEGORY_NAME
        }

        val actualResult = sut.map(product)

        assertThat(actualResult.categoryName).isEqualTo(CATEGORY_NAME)
    }

    @Test
    fun `map should map quantity`() {
        val product = mockk<Product>(relaxed = true)

        val actualResult = sut.map(product)

        assertThat(actualResult.quantity).isEqualTo(DEFAULT_QUANTITY)
    }

    @Test
    fun `map should map isInBasket`() {
        val product = mockk<Product>(relaxed = true)

        val actualResult = sut.map(product)

        assertThat(actualResult.isInBasket).isEqualTo(DEFAULT_IS_IN_BASKET)
    }
}

private const val DEFAULT_QUANTITY = 1
private const val DEFAULT_IS_IN_BASKET = false
