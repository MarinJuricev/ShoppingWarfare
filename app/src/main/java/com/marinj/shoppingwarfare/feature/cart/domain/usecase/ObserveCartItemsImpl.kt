package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCartItemsImpl @Inject constructor(
    private val cartRepository: CartRepository,
) : ObserveCartItems {

    override operator fun invoke(): Flow<List<CartItem>> =
        cartRepository.observeCartItems()
}
