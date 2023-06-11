package com.marinj.shoppingwarfare.feature.cart.presentation.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.cart.buildCartItem
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem.Content
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem.Header
import org.junit.Before
import org.junit.Test

class UiCartItemToCartItemMapperTest {

    private lateinit var sut: UiCartItemToCartItemMapper

    @Before
    fun setUp() {
        sut = UiCartItemToCartItemMapper()
    }

    @Test
    fun `map should return list of cartItems`() {
        val origin = listOf(
            Header(id = CATEGORY_NAME, categoryName = CATEGORY_NAME),
            Content(
                id = ID,
                name = NAME,
                categoryName = CATEGORY_NAME,
                quantity = QUANTITY,
                isInBasket = IS_IN_BASKET,
            ),
        )
        val expectedResult = listOf(
            buildCartItem(
                providedId = ID,
                providedCategoryName = CATEGORY_NAME,
                providedName = NAME,
                providedQuantity = QUANTITY.toUInt(),
                providedIsInBasket = IS_IN_BASKET,
            ),
        )

        val result = sut.map(origin)

        assertThat(result).isEqualTo(expectedResult)
    }
}

private const val CATEGORY_NAME = "categoryName"
private const val ID = "id"
private const val NAME = "name`"
private const val QUANTITY = 5
private const val IS_IN_BASKET = false
