package com.marinj.shoppingwarfare.feature.cart.domain.model

import arrow.core.left
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem.Companion.CartItem
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CartItemTest {

    @Test
    fun `CartItem SHOULD return Left WHEN id is empty`() = runTest {
        val expectedResult = ErrorMessage("id can not be null or empty").left()

        val result = CartItem(
            id = "",
            categoryName = "",
            name = "",
            quantity = null,
            isInBasket = null,
        )

        result shouldBe expectedResult
    }

    @Test
    fun `CartItem SHOULD return Left WHEN categoryNAME is empty`() {
        val expectedResult = ErrorMessage("categoryName can not be null or empty").left()

        val result = CartItem(
            id = ID,
            categoryName = "",
            name = "",
            quantity = null,
            isInBasket = null,
        )

        result shouldBe expectedResult
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

        result shouldBe expectedResult
    }

    @Test
    fun `CartItem SHOULD return Right with default quantity and isInBasket WHEN validation passes`() {
        val result = CartItem(
            id = ID,
            categoryName = CATEGORY_NAME,
            name = NAME,
        )

        result.map {
            it.id.value shouldBe ID
            it.categoryName.value shouldBe CATEGORY_NAME
            it.name.value shouldBe NAME
            it.quantity shouldBe 1u
            it.isInBasket.shouldBeFalse()
        }.isRight()
            .shouldBeTrue()
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

        result.map {
            it.id.value shouldBe ID
            it.categoryName.value shouldBe CATEGORY_NAME
            it.name.value shouldBe NAME
            it.quantity shouldBe QUANTITY
            it.isInBasket.shouldBeTrue()
        }.isRight()
            .shouldBeTrue()
    }
}

private const val ID = "id"
private const val CATEGORY_NAME = "categoryName"
private const val NAME = "name"
private const val QUANTITY = 15u
private const val IS_IN_BASKET = true
