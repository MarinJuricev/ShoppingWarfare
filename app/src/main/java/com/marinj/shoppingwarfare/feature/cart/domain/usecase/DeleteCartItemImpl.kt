package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import javax.inject.Inject

class DeleteCartItemImpl @Inject constructor(
    private val cartRepository: CartRepository,
) : DeleteCartItem {

    override suspend operator fun invoke(cartItemId: String) =
        cartRepository.deleteCartItemById(cartItemId)
}
