package com.marinj.shoppingwarfare.feature.cart.presentation.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

private const val CATEGORY_NAME = "fruits"

class CartItemsToCartDataMapperTest {

    private lateinit var sut: CartItemsToCartDataMapper

    @Before
    fun setUp() {
        sut = CartItemsToCartDataMapper()
    }

    @Test
    fun `map should return a map containing the categoryName and the according CartItem`() {
        val cartItem = mockk<CartItem>(relaxed = true).apply {
            every { categoryName } returns CATEGORY_NAME
        }
        val cartItemList = listOf(cartItem)

        val actualResult = sut.map(cartItemList)
        val expectedResult = mapOf(CATEGORY_NAME to cartItemList)

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}
