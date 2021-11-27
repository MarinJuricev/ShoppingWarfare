package com.marinj.shoppingwarfare.feature.cart.domain.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryCartItem
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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

private val uuidGenerator = { UUID }
private val timeStampGenerator = { TIMESTAMP }

@ExperimentalCoroutinesApi
class CartDataToHistoryItemMapperTest {

    private lateinit var sut: CartDataToHistoryItemMapper

    @Before
    fun setUp() {
        sut = CartDataToHistoryItemMapper(
            uuidGenerator = uuidGenerator,
            timeStampGenerator = timeStampGenerator,
        )
    }

    @Test
    fun `map should map id`() = runBlockingTest {
        val cartItems: List<CartItem> = emptyList()

        val result = sut.map(cartItems, CART_NAME, RECEIPT_PATH)

        assertThat(result.id).isEqualTo(UUID)
    }

    @Test
    fun `map should map receiptPath`() = runBlockingTest {
        val cartItems: List<CartItem> = emptyList()

        val result = sut.map(cartItems, CART_NAME, RECEIPT_PATH)

        assertThat(result.receiptPath).isEqualTo(RECEIPT_PATH)
    }

    @Test
    fun `map should map cartName`() = runBlockingTest {
        val cartItems: List<CartItem> = emptyList()

        val result = sut.map(cartItems, CART_NAME, RECEIPT_PATH)

        assertThat(result.cartName).isEqualTo(CART_NAME)
    }

    @Test
    fun `map should map timeStamp`() = runBlockingTest {
        val cartItems: List<CartItem> = emptyList()

        val result = sut.map(cartItems, CART_NAME, RECEIPT_PATH)

        assertThat(result.timestamp).isEqualTo(TIMESTAMP)
    }

    @Test
    fun `map should map historyCartItems`() = runBlockingTest {
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
            )
        )

        assertThat(result.historyCartItems).isEqualTo(expectedResult)
    }
}
