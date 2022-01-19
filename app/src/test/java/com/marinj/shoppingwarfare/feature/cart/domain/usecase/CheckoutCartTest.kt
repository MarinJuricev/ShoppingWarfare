package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.cart.domain.mapper.CartDataToHistoryItemMapper
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryItem
import com.marinj.shoppingwarfare.feature.history.list.domain.repository.HistoryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

private const val RECEIPT_PATH = "receiptPath"
private const val CART_NAME = "cartName"

@ExperimentalCoroutinesApi
class CheckoutCartTest {

    private val validateCartName: ValidateCartName = mockk()
    private val cartRepository: CartRepository = mockk()
    private val historyRepository: HistoryRepository = mockk()
    private val cartItemsToHistoryItemMapper: CartDataToHistoryItemMapper = mockk()

    private lateinit var sut: CheckoutCart

    @Before
    fun setUp() {
        sut = CheckoutCart(
            validateCartName = validateCartName,
            cartRepository = cartRepository,
            historyRepository = historyRepository,
            cartItemsToHistoryItemMapper = cartItemsToHistoryItemMapper,
        )
    }

    @Test
    fun `invoke should return result validateCartName when validateCartName returns Left`() =
        runTest {
            val cartItems = mockk<List<CartItem>>()
            val validateResult = Failure.Unknown.buildLeft()
            coEvery {
                validateCartName(CART_NAME)
            } coAnswers { validateResult }

            val result = sut(cartItems, CART_NAME, RECEIPT_PATH)

            assertThat(result).isEqualTo(validateResult)
        }

    @Test
    fun `invoke should return result from historyRepository updateHistoryItem when validateCartName returns Right`() =
        runTest {
            val cartItems = mockk<List<CartItem>>()
            val historyItem = mockk<HistoryItem>()
            val historyRepositoryResult = Unit.buildRight()
            coEvery {
                cartItemsToHistoryItemMapper.map(cartItems, CART_NAME, RECEIPT_PATH)
            } coAnswers { historyItem }
            coEvery {
                historyRepository.upsertHistoryItem(historyItem)
            } coAnswers { historyRepositoryResult }
            coEvery {
                cartRepository.dropCurrentCart()
            } coAnswers { Unit.buildRight() }
            coEvery {
                validateCartName(CART_NAME)
            } coAnswers { Unit.buildRight() }

            val result = sut(cartItems, CART_NAME, RECEIPT_PATH)

            assertThat(result).isEqualTo(historyRepositoryResult)
        }
}
