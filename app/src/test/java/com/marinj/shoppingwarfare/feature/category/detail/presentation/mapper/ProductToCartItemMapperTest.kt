package com.marinj.shoppingwarfare.feature.category.detail.presentation.mapper

import com.marinj.shoppingwarfare.fixtures.category.buildProduct
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class ProductToCartItemMapperTest {

    private val sut = ProductToCartItemMapper()

    @Test
    fun `map should map id`() = runTest {
        val product = buildProduct(providedProductId = ID)

        val actualResult = sut.map(product)

        actualResult.getOrNull()?.id?.value shouldBe ID
    }

    @Test
    fun `map should map name`() {
        val product = buildProduct(providedName = NAME)

        val actualResult = sut.map(product)

        actualResult.getOrNull()?.name?.value shouldBe NAME
    }

    @Test
    fun `map should map categoryName`() {
        val product = buildProduct(providedCategoryName = CATEGORY_NAME)

        val actualResult = sut.map(product)

        actualResult.getOrNull()?.categoryName?.value shouldBe CATEGORY_NAME
    }

    @Test
    fun `map should map quantity`() {
        val product = buildProduct(providedName = NAME)

        val actualResult = sut.map(product)

        actualResult.getOrNull()?.quantity?.toInt() shouldBe DEFAULT_QUANTITY
    }

    @Test
    fun `map should map isInBasket`() {
        val product = buildProduct(providedName = NAME)

        val actualResult = sut.map(product)

        actualResult.getOrNull()?.isInBasket shouldBe DEFAULT_IS_IN_BASKET
    }
}

private const val DEFAULT_QUANTITY = 1
private const val DEFAULT_IS_IN_BASKET = false
private const val ID = "id"
private const val NAME = "name"
private const val CATEGORY_NAME = "fruits"
