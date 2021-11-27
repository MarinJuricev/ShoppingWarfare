package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Either.Left
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.cart.domain.mapper.CartDataToHistoryItemMapper
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import com.marinj.shoppingwarfare.feature.history.list.domain.repository.HistoryRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class CheckoutCart @Inject constructor(
    private val validateCartName: ValidateCartName,
    private val cartRepository: CartRepository,
    private val historyRepository: HistoryRepository,
    private val cartItemsToHistoryItemMapper: CartDataToHistoryItemMapper,
) {

    suspend operator fun invoke(
        cartItems: List<CartItem>,
        cartName: String,
        receiptPath: String?,
    ): Either<Failure, Unit> {
        validateCartName(cartName).apply {
            if (this is Left) {
                return this
            }
        }

        val result = coroutineScope {
            val historyResult = async {
                val historyItem = cartItemsToHistoryItemMapper.map(cartItems, cartName, receiptPath)
                historyRepository.upsertHistoryItem(historyItem)
            }
            val cartResult = async { cartRepository.dropCurrentCart() }

            historyResult.await() to cartResult.await()
        }

        // We only care about the historyResult, but we want to await the cart result so that the
        // database gets dropped in a parallel manner
        return result.first
    }
}
