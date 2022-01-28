package com.marinj.shoppingwarfare.feature.cart.data.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.cart.data.model.LocalCartItem
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

private const val ID = "id"
private const val NAME = "name"
private const val CATEGORY_NAME = "fruits"
private const val QUANTITY = 1

class LocalToDomainCartItemMapperTest {

    private lateinit var sut: LocalToDomainCartItemMapper

    @Before
    fun setUp() {
        sut = LocalToDomainCartItemMapper()
    }

    @Test
    fun `map should map id`() {
        val localCartItem = mockk<LocalCartItem>(relaxed = true).apply {
            every { cartItemId } returns ID
        }

        val actualResult = sut.map(localCartItem)

        assertThat(actualResult.id).isEqualTo(ID)
    }

    @Test
    fun `map should map name`() {
        val localCartItem = mockk<LocalCartItem>(relaxed = true).apply {
            every { name } returns NAME
        }

        val actualResult = sut.map(localCartItem)

        assertThat(actualResult.name).isEqualTo(NAME)
    }

    @Test
    fun `map should map categoryName`() {
        val localCartItem = mockk<LocalCartItem>(relaxed = true).apply {
            every { categoryName } returns CATEGORY_NAME
        }

        val actualResult = sut.map(localCartItem)

        assertThat(actualResult.categoryName).isEqualTo(CATEGORY_NAME)
    }

    @Test
    fun `map should map quantity`() {
        val localCartItem = mockk<LocalCartItem>(relaxed = true).apply {
            every { quantity } returns QUANTITY
        }

        val actualResult = sut.map(localCartItem)

        assertThat(actualResult.quantity).isEqualTo(QUANTITY)
    }

    @Test
    fun `map should map isInQuantity`() {
        val localCartItem = mockk<LocalCartItem>(relaxed = true)

        val actualResult = sut.map(localCartItem)

        assertThat(actualResult.isInBasket).isFalse()
    }
}
