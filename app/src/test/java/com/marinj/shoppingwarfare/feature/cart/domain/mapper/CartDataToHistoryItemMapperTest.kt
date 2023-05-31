package com.marinj.shoppingwarfare.feature.cart.domain.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.cart.buildCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryCartItem
import kotlinx.coroutines.test.runTest
import org.junit.Test


class CartDataToHistoryItemMapperTest {

    private val sut = CartDataToHistoryItemMapper(
        uuidGenerator = UUID_GENERATOR,
        timeStampGenerator = TIME_STAMP_GENERATOR,
    )

    @Test
    fun `map SHOULD map id`() = runTest {
        val cartItems: List<CartItem> = emptyList()

        val result = sut.map(cartItems, CART_NAME, RECEIPT_PATH)

        assertThat(result.id).isEqualTo(UUID)
    }

    @Test
    fun `map SHOULD map receiptPath`() = runTest {
        val cartItems: List<CartItem> = emptyList()

        val result = sut.map(cartItems, CART_NAME, RECEIPT_PATH)

        assertThat(result.receiptPath).isEqualTo(RECEIPT_PATH)
    }

    @Test
    fun `map SHOULD map cartName`() = runTest {
        val cartItems: List<CartItem> = emptyList()

        val result = sut.map(cartItems, CART_NAME, RECEIPT_PATH)

        assertThat(result.cartName).isEqualTo(CART_NAME)
    }

    @Test
    fun `map SHOULD map timeStamp`() = runTest {
        val cartItems: List<CartItem> = emptyList()

        val result = sut.map(cartItems, CART_NAME, RECEIPT_PATH)

        assertThat(result.timestamp).isEqualTo(TIMESTAMP)
    }

    @Test
    fun `map SHOULD map historyCartItems`() = runTest {
        val cartItems = listOf(
            buildCartItem(
                providedId = ID,
                providedCategoryName = CATEGORY_NAME,
                providedName = NAME,
                providedQuantity = QUANTITY.toUInt(),
            ),
        )
        val expectedResult = listOf(
            HistoryCartItem(
                id = ID,
                categoryName = CATEGORY_NAME,
                name = NAME,
                quantity = QUANTITY,
            ),
        )

        val result = sut.map(cartItems, CART_NAME, RECEIPT_PATH)

        assertThat(result.historyCartItems).isEqualTo(expectedResult)
    }
}

private const val UUID = "42"
private const val TIMESTAMP = 100L
private const val ID = "id"
private const val CATEGORY_NAME = "categoryName"
private const val NAME = "name"
private const val QUANTITY = 5
private const val RECEIPT_PATH = "receiptPath"
private const val CART_NAME = "cartName"

private val UUID_GENERATOR = { UUID }
private val TIME_STAMP_GENERATOR = { TIMESTAMP }
