package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryItem
import com.marinj.shoppingwarfare.feature.history.domain.repository.HistoryRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class CheckoutCart @Inject constructor(
    private val cartRepository: CartRepository,
    private val historyRepository: HistoryRepository,
    private val cartItemsToHistoryItemMapper: Mapper<HistoryItem, List<CartItem>>
) {

    suspend operator fun invoke(currentCartItems: List<CartItem>) {
        val result = coroutineScope {
            val historyResult = async {
                val historyItem = cartItemsToHistoryItemMapper.map(currentCartItems)
                historyRepository.upsertHistoryItem(historyItem)
            }
            val cartResult = async { cartRepository.dropCurrentCart() }

            historyResult.await() to cartResult.await()
        }

        // We only care about the historyResult, but we want to await the cart result so that the
        // database gets dropped in a parallel manner
        when (result.first) {
            is Either.Left -> TODO()
            is Either.Right -> TODO()
        }
    }
}
