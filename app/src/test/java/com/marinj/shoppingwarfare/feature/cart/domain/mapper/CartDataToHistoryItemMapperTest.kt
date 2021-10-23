package com.marinj.shoppingwarfare.feature.cart.domain.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val CART_KEY = "cartKey"
private const val UUID = "42"
private const val TIMESTAMP = 100L
private const val ID = "id"
private const val CATEGORY_NAME = "categoryName"
private const val NAME = "name"
private const val QUANTITY = 5
private const val RECEIPT_PATH = "receiptPath"

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
        val cartData = emptyMap<String, List<CartItem>>()

        val result = sut.map(cartData, RECEIPT_PATH)

        assertThat(result.id).isEqualTo(UUID)
    }

    @Test
    fun `map should map receiptPath`() = runBlockingTest {
        val cartData = emptyMap<String, List<CartItem>>()

        val result = sut.map(cartData, RECEIPT_PATH)

        assertThat(result.receiptPath).isEqualTo(RECEIPT_PATH)
    }

    @Test
    fun `map should map timeStamp`() = runBlockingTest {
        val cartData = emptyMap<String, List<CartItem>>()

        val result = sut.map(cartData, RECEIPT_PATH)

        assertThat(result.timestamp).isEqualTo(TIMESTAMP)
    }

    @Test
    fun `map should map historyCartItems`() = runBlockingTest {
        val cartItems = mockk<CartItem>().apply {
            every { id } returns ID
            every { categoryName } returns CATEGORY_NAME
            every { name } returns NAME
            every { quantity } returns QUANTITY
        }
        val cartData = mapOf(CART_KEY to listOf(cartItems))

        val result = sut.map(cartData, RECEIPT_PATH)

        assertThat(result.timestamp).isEqualTo(TIMESTAMP)
    }
}