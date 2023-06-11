package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface ObserveCartItems {

    operator fun invoke(): Flow<List<CartItem>>
}
