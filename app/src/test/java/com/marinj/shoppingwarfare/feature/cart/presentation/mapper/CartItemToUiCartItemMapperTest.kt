package com.marinj.shoppingwarfare.feature.cart.presentation.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

private const val CATEGORY_NAME = "categoryName"
private const val ID = "id"
private const val NAME = "name`"
private const val QUANTITY = 5
private const val IS_IN_BASKET = false

class CartItemToUiCartItemMapperTest {

    private lateinit var sut: CartItemToUiCartItemMapper

    @Before
    fun setUp() {
        sut = CartItemToUiCartItemMapper()
    }

    @Test
    fun `map should return UiCartItem Header as the first item in the list`() {
        val cartItems = mockk<CartItem>().apply {
            every { categoryName } returns CATEGORY_NAME
            every { id } returns ID
            every { name } returns NAME
            every { quantity } returns QUANTITY
            every { isInBasket } returns IS_IN_BASKET
        }

        val result = sut.map(listOf(cartItems))
        val expectedResult = UiCartItem.Header(id = CATEGORY_NAME, categoryName = CATEGORY_NAME)

        assertThat(result.first()).isEqualTo(expectedResult)
    }

    @Test
    fun `map should return UiCartItem Content as the second item in the list`() {
        val cartItems = mockk<CartItem>().apply {
            every { categoryName } returns CATEGORY_NAME
            every { id } returns ID
            every { name } returns NAME
            every { quantity } returns QUANTITY
            every { isInBasket } returns IS_IN_BASKET
        }

        val result = sut.map(listOf(cartItems))
        val expectedResult = UiCartItem.Content(
            id = ID,
            categoryName = CATEGORY_NAME,
            name = NAME,
            quantity = QUANTITY,
            isInBasket = IS_IN_BASKET
        )

        assertThat(result[1]).isEqualTo(expectedResult)
    }
}
