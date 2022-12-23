package com.marinj.shoppingwarfare.feature.cart.data.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

private const val ID = "id"
private const val NAME = "name"
private const val CATEGORY_NAME = "fruits"
private const val QUANTITY = 1
private const val IS_IN_BASKET = false

class DomainToLocalCartItemMapperTest {

    private lateinit var sut: DomainToLocalCartItemMapper

    @Before
    fun setUp() {
        sut = DomainToLocalCartItemMapper()
    }

    @Test
    fun `map SHOULD map cartItemId`() {
        val cartItem = mockk<CartItem>(relaxed = true).apply {
            every { id } returns ID
        }

        val actualResult = sut.map(cartItem)

        assertThat(actualResult.cartItemId).isEqualTo(ID)
    }

    @Test
    fun `map SHOULD map name`() {
        val cartItem = mockk<CartItem>(relaxed = true).apply {
            every { name } returns NAME
        }

        val actualResult = sut.map(cartItem)

        assertThat(actualResult.name).isEqualTo(NAME)
    }

    @Test
    fun `map SHOULD map categoryName`() {
        val cartItem = mockk<CartItem>(relaxed = true).apply {
            every { categoryName } returns CATEGORY_NAME
        }

        val actualResult = sut.map(cartItem)

        assertThat(actualResult.categoryName).isEqualTo(CATEGORY_NAME)
    }

    @Test
    fun `map SHOULD map quantity`() {
        val cartItem = mockk<CartItem>(relaxed = true).apply {
            every { quantity } returns QUANTITY
        }

        val actualResult = sut.map(cartItem)

        assertThat(actualResult.quantity).isEqualTo(QUANTITY)
    }

    @Test
    fun `map SHOULD map isInBasket`() {
        val cartItem = mockk<CartItem>(relaxed = true).apply {
            every { isInBasket } returns IS_IN_BASKET
        }

        val actualResult = sut.map(cartItem)

        assertThat(actualResult.isInBasket).isEqualTo(IS_IN_BASKET)
    }
}
