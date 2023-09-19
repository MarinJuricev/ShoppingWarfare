package com.marinj.shoppingwarfare.feature.cart.data.model

import com.marinj.shoppingwarfare.feature.cart.buildCartItem
import com.marinj.shoppingwarfare.feature.cart.buildLocalCartItem
import com.marinj.shoppingwarfare.feature.cart.buildRemoteCartItem
import org.junit.Assert.assertEquals
import org.junit.Test

class RemoteCartItemTest {

    @Test
    fun `toLocal SHOULD map cartItemId`() {
        val expectedResult = CART_ITEM_ID
        val sut = buildRemoteCartItem(
            providedId = expectedResult,
        )

        val actual = sut.toLocal()

        assertEquals(expectedResult, actual.cartItemId)
    }

    @Test
    fun `toLocal SHOULD map categoryName`() {
        val expectedResult = CATEGORY_NAME
        val sut = buildRemoteCartItem(
            providedCategoryName = expectedResult,
        )

        val actual = sut.toLocal()

        assertEquals(expectedResult, actual.cartItemId)
    }

    @Test
    fun `toLocal SHOULD map name`() {
        val expectedResult = NAME
        val sut = buildRemoteCartItem(
            providedName = expectedResult,
        )

        val actual = sut.toLocal()

        assertEquals(expectedResult, actual.name)
    }

    @Test
    fun `toLocal SHOULD map quantity`() {
        val expectedResult = QUANTITY
        val sut = buildRemoteCartItem(
            providedQuantity = expectedResult,
        )

        val actual = sut.toLocal()

        assertEquals(expectedResult, actual.quantity)
    }

    @Test
    fun `toLocal SHOULD map isInBasket`() {
        val expectedResult = IN_BASKET
        val sut = buildRemoteCartItem(
            providedIsInBasket = expectedResult,
        )

        val actual = sut.toLocal()

        assertEquals(expectedResult, actual.isInBasket)
    }

    @Test
    fun `toRemote SHOULD map cartItemId`() {
        val expectedResult = CART_ITEM_ID
        val sut = buildCartItem(
            providedId = expectedResult,
        )

        val actual = sut.toRemote()

        assertEquals(expectedResult, actual.cartItemId)
    }

    @Test
    fun `toRemote SHOULD map categoryName`() {
        val expectedResult = CATEGORY_NAME
        val sut = buildCartItem(
            providedCategoryName = expectedResult,
        )

        val actual = sut.toRemote()

        assertEquals(expectedResult, actual.categoryName)
    }

    @Test
    fun `toRemote SHOULD map name`(){
        val expectedResult = NAME
        val sut = buildCartItem(
            providedName = expectedResult,
        )

        val actual = sut.toRemote()

        assertEquals(expectedResult, actual.name)
    }

    @Test
    fun `toRemote SHOULD map quantity`() {
        val expectedResult = QUANTITY
        val sut = buildCartItem(
            providedQuantity = expectedResult,
        )

        val actual = sut.toRemote()

        assertEquals(expectedResult.toInt(), actual.quantity)
    }

    @Test
    fun `toRemote SHOULD map isInBasket`() {
        val expectedResult = IN_BASKET
        val sut = buildCartItem(
            providedIsInBasket = expectedResult,
        )

        val actual = sut.toRemote()

        assertEquals(expectedResult, actual.inBasket)
    }
}

private const val CART_ITEM_ID = "cartItemId"
private const val CATEGORY_NAME = "categoryName"
private const val NAME = "name"
private const val QUANTITY = 1u
private const val IN_BASKET = true