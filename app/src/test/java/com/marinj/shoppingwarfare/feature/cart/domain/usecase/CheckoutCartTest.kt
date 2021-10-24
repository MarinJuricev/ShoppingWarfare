package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.cart.domain.mapper.CartDataToHistoryItemMapper
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryItem
import com.marinj.shoppingwarfare.feature.history.domain.repository.HistoryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val RECEIPT_PATH = "receiptPath"

@ExperimentalCoroutinesApi
class CheckoutCartTest {

    private val cartRepository: CartRepository = mockk()
    private val historyRepository: HistoryRepository = mockk()
    private val cartItemsToHistoryItemMapper: CartDataToHistoryItemMapper = mockk()

    private lateinit var sut: CheckoutCart

    @Before
    fun setUp() {
        sut = CheckoutCart(
            cartRepository,
            historyRepository,
            cartItemsToHistoryItemMapper,
        )
    }

    @Test
    fun `invoke should return result from historyRepository updateHistoryItem`() = runBlockingTest {
        val cartData = mockk<Map<String, List<CartItem>>>()
        val historyItem = mockk<HistoryItem>()
        val historyRepositoryResult = Unit.buildRight()
        coEvery {
            cartItemsToHistoryItemMapper.map(cartData, RECEIPT_PATH)
        } coAnswers { historyItem }
        coEvery {
            historyRepository.upsertHistoryItem(historyItem)
        } coAnswers { historyRepositoryResult }
        coEvery {
            cartRepository.dropCurrentCart()
        } coAnswers { Unit.buildRight() }

        val result = sut(cartData, RECEIPT_PATH)

        assertThat(result).isEqualTo(historyRepositoryResult)
    }
}
