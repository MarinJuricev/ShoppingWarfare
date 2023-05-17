package com.marinj.shoppingwarfare.feature.cart.domain.model

import arrow.core.left
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem.Companion.CartItem
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CartItemTest {

    @Test
    fun `CartItem SHOULD return Left WHEN title is empty`() = runTest{
        val expectedResult = ErrorMessage("id can not be null or empty").left()

        val result = CartItem(
            id = "",
            categoryName = "",
            name = "",
            quantity = null,
            isInBasket = null,
        )

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `CartItem SHOULD return Left WHEN categoryId is empty`() {
        val expectedResult = ErrorMessage("categoryId can not be null or empty").left()

        val result = CartItem(
            id = ID,
            categoryName = "",
            name = "",
            quantity = null,
            isInBasket = null,
        )

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `CartItem SHOULD return Left WHEN name is empty`() {
        val expectedResult = ErrorMessage("name can not be null or empty").left()

        val result = CartItem(
            id = ID,
            categoryName = CATEGORY_NAME,
            name = "",
            quantity = null,
            isInBasket = null,
        )

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `CartItem SHOULD return Right with default quantity and isInBasket WHEN validation passes`() {
        val result = CartItem(
            id = ID,
            categoryName = CATEGORY_NAME,
            name = NAME,
        )

        assertThat(
            result.map {
                assertThat(it.id).isEqualTo(ID)
                assertThat(it.categoryName).isEqualTo(CATEGORY_NAME)
                assertThat(it.name).isEqualTo(NAME)
                assertThat(it.quantity).isEqualTo(1u)
                assertThat(it.isInBasket).isFalse()
            }.isRight(),
        ).isTrue()
    }

    @Test
    fun `CartItem SHOULD return Right WHEN validation passes`() {
        val result = CartItem(
            id = ID,
            categoryName = CATEGORY_NAME,
            name = NAME,
            quantity = QUANTITY,
            isInBasket = IS_IN_BASKET,
        )

        assertThat(
            result.map {
                assertThat(it.id).isEqualTo(ID)
                assertThat(it.categoryName).isEqualTo(CATEGORY_NAME)
                assertThat(it.name).isEqualTo(NAME)
                assertThat(it.quantity).isEqualTo(QUANTITY)
                assertThat(it.isInBasket).isTrue()
            }.isRight(),
        ).isTrue()
    }
}

private const val ID = "id"
private const val CATEGORY_NAME = "categoryName"
private const val NAME = "name"
private const val QUANTITY = 15u
private const val IS_IN_BASKET = true