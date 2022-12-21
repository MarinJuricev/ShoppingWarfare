package com.marinj.shoppingwarfare.feature.cart.domain.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryCartItem
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

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

class CartDataToHistoryItemMapperTest {

    private lateinit var sut: CartDataToHistoryItemMapper

    @Before
    fun setUp() {
        sut = CartDataToHistoryItemMapper(
            uuidGenerator = UUID_GENERATOR,
            timeStampGenerator = TIME_STAMP_GENERATOR,
        )
    }

    @Test
    fun `map should map id`() = runTest {
        val cartItems: List<CartItem> = emptyList()

        val result = sut.map(cartItems, CART_NAME, RECEIPT_PATH)

        assertThat(result.id).isEqualTo(UUID)
    }

    @Test
    fun `map should map receiptPath`() = runTest {
        val cartItems: List<CartItem> = emptyList()

        val result = sut.map(cartItems, CART_NAME, RECEIPT_PATH)

        assertThat(result.receiptPath).isEqualTo(RECEIPT_PATH)
    }

    @Test
    fun `map should map cartName`() = runTest {
        val cartItems: List<CartItem> = emptyList()

        val result = sut.map(cartItems, CART_NAME, RECEIPT_PATH)

        assertThat(result.cartName).isEqualTo(CART_NAME)
    }

    @Test
    fun `map should map timeStamp`() = runTest {
        val cartItems: List<CartItem> = emptyList()

        val result = sut.map(cartItems, CART_NAME, RECEIPT_PATH)

        assertThat(result.timestamp).isEqualTo(TIMESTAMP)
    }

    @Test
    fun `map should map historyCartItems`() = runTest {
        val cartItem = mockk<CartItem>().apply {
            every { id } returns ID
            every { categoryName } returns CATEGORY_NAME
            every { name } returns NAME
            every { quantity } returns QUANTITY
        }
        val cartItems = listOf(cartItem)

        val result = sut.map(cartItems, CART_NAME, RECEIPT_PATH)
        val expectedResult = listOf(
            HistoryCartItem(
                id = ID,
                categoryName = CATEGORY_NAME,
                name = NAME,
                quantity = QUANTITY,
            ),
        )

        assertThat(result.historyCartItems).isEqualTo(expectedResult)
    }
}
