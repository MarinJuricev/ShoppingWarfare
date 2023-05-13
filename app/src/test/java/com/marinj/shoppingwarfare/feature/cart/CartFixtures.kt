package com.marinj.shoppingwarfare.feature.cart

import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem

fun buildCartItem(
    providedId: String = ID,
    providedCategoryName: String = CATEGORY_NAME,
    providedName: String = NAME,
    providedQuantity: Int = QUANTITY,
    providedIsInBasket: Boolean = IS_IN_BASKET,
) = CartItem(
    id = providedId,
    categoryName = providedCategoryName,
    name = providedName,
    quantity = providedQuantity,
    isInBasket = providedIsInBasket,
)

private const val ID = "ID"
private const val CATEGORY_NAME = "CATEGORY_NAME"
private const val NAME = "NAME"
private const val QUANTITY = 1
private const val IS_IN_BASKET = false