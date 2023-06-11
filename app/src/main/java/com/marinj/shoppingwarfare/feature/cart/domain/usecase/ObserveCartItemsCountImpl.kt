package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCartItemsCountImpl @Inject constructor(
    private val cartRepository: CartRepository,
) : ObserveCartItemsCount {

    override operator fun invoke(): Flow<Int?> =
        cartRepository.observeCartItemsCount()
}
