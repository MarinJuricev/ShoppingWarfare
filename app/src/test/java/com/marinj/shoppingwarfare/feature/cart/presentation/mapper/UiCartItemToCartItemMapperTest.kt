package com.marinj.shoppingwarfare.feature.cart.presentation.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem
import org.junit.Before
import org.junit.Test

private const val CATEGORY_NAME = "categoryName"
private const val ID = "id"
private const val NAME = "name`"
private const val QUANTITY = 5
private const val IS_IN_BASKET = false

class UiCartItemToCartItemMapperTest {

    private lateinit var sut: UiCartItemToCartItemMapper

    @Before
    fun setUp() {
        sut = UiCartItemToCartItemMapper()
    }

    @Test
    fun `map should return list of cartItems`() {
        val origin = listOf(
            UiCartItem.Header(id = CATEGORY_NAME, categoryName = CATEGORY_NAME),
            UiCartItem.Content(
                id = ID,
                name = NAME,
                categoryName = CATEGORY_NAME,
                quantity = QUANTITY,
                isInBasket = IS_IN_BASKET
            )
        )

        val result = sut.map(origin)
        val expectedResult = listOf(
            CartItem(
                id = ID,
                name = NAME,
                categoryName = CATEGORY_NAME,
                quantity = QUANTITY,
                isInBasket = IS_IN_BASKET
            )
        )

        assertThat(result).isEqualTo(expectedResult)
    }
}
