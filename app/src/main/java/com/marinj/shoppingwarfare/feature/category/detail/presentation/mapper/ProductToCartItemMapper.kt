package com.marinj.shoppingwarfare.feature.category.detail.presentation.mapper

import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem.Companion.DEFAULT_QUANTITY
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import javax.inject.Inject

class ProductToCartItemMapper @Inject constructor() {

    fun map(origin: Product): CartItem {
        return with(origin) {
            CartItem(
                id = id,
                name = name,
                categoryName = categoryName,
                quantity = DEFAULT_QUANTITY
            )
        }
    }
}
