package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import kotlinx.coroutines.flow.Flow

interface ObserveCartItemsCount {
    operator fun invoke(): Flow<Int?>
}