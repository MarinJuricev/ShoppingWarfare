package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCartItemsCount @Inject constructor(
    private val cartRepository: CartRepository,
) {

    operator fun invoke(): Flow<Int> =
        cartRepository.observeCartItemsCount()
}
