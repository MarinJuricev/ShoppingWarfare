package com.marinj.shoppingwarfare.feature.category.detail.presentation.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.fixtures.category.buildProduct
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ProductToCartItemMapperTest {

    private val sut = ProductToCartItemMapper()

    @Test
    fun `map should map id`() = runTest {
        val product = buildProduct(providedProductId = ID)

        val actualResult = sut.map(product)

        assertThat(actualResult.id).isEqualTo(ID)
    }

    @Test
    fun `map should map name`() {
        val product = buildProduct(providedName = NAME)

        val actualResult = sut.map(product)

        assertThat(actualResult.name).isEqualTo(NAME)
    }

    @Test
    fun `map should map categoryName`() {
        val product = buildProduct(providedName = NAME)

        val actualResult = sut.map(product)

        assertThat(actualResult.categoryName).isEqualTo(CATEGORY_NAME)
    }

    @Test
    fun `map should map quantity`() {
        val product = buildProduct(providedName = NAME)

        val actualResult = sut.map(product)

        assertThat(actualResult.quantity).isEqualTo(DEFAULT_QUANTITY)
    }

    @Test
    fun `map should map isInBasket`() {
        val product = buildProduct(providedName = NAME)

        val actualResult = sut.map(product)

        assertThat(actualResult.isInBasket).isEqualTo(DEFAULT_IS_IN_BASKET)
    }
}

private const val DEFAULT_QUANTITY = 1
private const val DEFAULT_IS_IN_BASKET = false
private const val ID = "id"
private const val NAME = "name"
private const val CATEGORY_NAME = "fruits"
