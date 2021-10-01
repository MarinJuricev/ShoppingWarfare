package com.marinj.shoppingwarfare.feature.cart.presentation.mapper

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import javax.inject.Inject

class CartItemsToCartDataMapper @Inject constructor() :
    Mapper<Map<String, List<CartItem>>, List<CartItem>> {

    override suspend fun map(origin: List<CartItem>): Map<String, List<CartItem>> {
        return origin.groupBy { cartItem -> cartItem.categoryName }
    }
}
