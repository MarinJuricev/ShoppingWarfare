package com.marinj.shoppingwarfare.feature.cart.presentation.mapper

import com.marinj.shoppingwarfare.feature.cart.buildCartItem
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem.Content
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem.Header
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CartItemToUiCartItemMapperTest {

    private val sut = CartItemToUiCartItemMapper()

    @Test
    fun `map SHOULD return UiCartItem Header as the first item in the list`() = runTest {
        val cartItem = buildCartItem(
            providedCategoryName = CATEGORY_NAME,
            providedId = ID,
            providedName = NAME,
            providedQuantity = QUANTITY.toUInt(),
            providedIsInBasket = IS_IN_BASKET,
        )

        val result = sut.map(listOf(cartItem))
        val expectedResult = Header(id = CATEGORY_NAME, categoryName = CATEGORY_NAME)

        result.first() shouldBe expectedResult
    }

    @Test
    fun `map SHOULD return UiCartItem Content as the second item in the list`() = runTest {
        val cartItem = buildCartItem(
            providedCategoryName = CATEGORY_NAME,
            providedId = ID,
            providedName = NAME,
            providedQuantity = QUANTITY.toUInt(),
            providedIsInBasket = IS_IN_BASKET,
        )

        val result = sut.map(listOf(cartItem))
        val expectedResult = Content(
            id = ID,
            categoryName = CATEGORY_NAME,
            name = NAME,
            quantity = QUANTITY,
            isInBasket = IS_IN_BASKET,
        )

        result[1] shouldBe expectedResult
    }
}

private const val CATEGORY_NAME = "categoryName"
private const val ID = "id"
private const val NAME = "name`"
private const val QUANTITY = 5
private const val IS_IN_BASKET = false
