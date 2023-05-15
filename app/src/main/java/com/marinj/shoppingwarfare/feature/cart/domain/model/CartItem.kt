package com.marinj.shoppingwarfare.feature.cart.domain.model

import arrow.core.Either
import arrow.core.raise.either
import com.marinj.shoppingwarfare.core.model.NonEmptyString
import com.marinj.shoppingwarfare.core.model.NonEmptyString.Companion.NonEmptyString
import com.marinj.shoppingwarfare.core.result.Failure

data class CartItem private constructor(
    val id: NonEmptyString,
    val categoryName: NonEmptyString,
    val name: NonEmptyString,
    val quantity: UInt = DEFAULT_QUANTITY,
    val isInBasket: Boolean = DEFAULT_IS_IN_BASKET,
) {
    companion object {
        fun CartItem(
            id: String,
            categoryName: String,
            name: String,
            quantity: UInt? = null,
            isInBasket: Boolean? = false,
        ): Either<Failure, CartItem> = either {
            val mappedId = NonEmptyString(valueToValidate = id, tag = "id").bind()
            val mappedCategoryName = NonEmptyString(valueToValidate = categoryName, tag = "categoryName").bind()
            val mappedName = NonEmptyString(valueToValidate = name, tag = "name").bind()

            CartItem(
                id = mappedId,
                categoryName = mappedCategoryName,
                name = mappedName,
                quantity = quantity ?: DEFAULT_QUANTITY,
                isInBasket = isInBasket ?: DEFAULT_IS_IN_BASKET,
            )
        }
    }
}

private const val DEFAULT_QUANTITY: UInt = 1u
private const val DEFAULT_IS_IN_BASKET = false
