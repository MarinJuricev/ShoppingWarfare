package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import com.marinj.shoppingwarfare.core.model.NonEmptyString.Companion.NonEmptyString
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.feature.cart.domain.mapper.CartDataToHistoryItemMapper
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import com.marinj.shoppingwarfare.feature.history.list.domain.repository.HistoryRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class CheckoutCartImpl @Inject constructor(
    private val cartRepository: CartRepository,
    private val historyRepository: HistoryRepository,
    private val cartItemsToHistoryItemMapper: CartDataToHistoryItemMapper,
) : CheckoutCart {

    override suspend operator fun invoke(
        cartItems: List<CartItem>,
        cartName: String,
        receiptPath: String?,
    ): Either<Failure, Unit> = either {
        val validatedCartName = NonEmptyString(valueToValidate = cartName, "cartName").bind()

        val (_, cartResult) = coroutineScope {
            val historyResult = async {
                val historyItem = cartItemsToHistoryItemMapper.map(
                    cartItems = cartItems,
                    cartName = validatedCartName.value,
                    receiptPath = receiptPath,
                ).getOrNull() ?: return@async ErrorMessage("Failed to map cart items to history item").left()

                historyRepository.upsertHistoryItem(historyItem)
            }
            val cartResult = async { cartRepository.dropCurrentCart() }

            awaitAll(historyResult, cartResult)
        }

        return cartResult
    }
}
