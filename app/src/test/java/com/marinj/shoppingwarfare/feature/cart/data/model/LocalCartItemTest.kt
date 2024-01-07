package com.marinj.shoppingwarfare.feature.cart.data.model

import com.marinj.shoppingwarfare.feature.cart.buildCartItem
import com.marinj.shoppingwarfare.feature.cart.buildLocalCartItem
import com.marinj.shoppingwarfare.feature.cart.data.mapper.toLocal
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class LocalCartItemTest {
    @Test
    fun `toDomain SHOULD map id`() {
        val sut = buildLocalCartItem(providedId = ID)

        val actualResult = sut.toDomain()

        actualResult.getOrNull()?.id?.value shouldBe ID
    }

    @Test
    fun `toDomain SHOULD map categoryName`() {
        val sut = buildLocalCartItem(providedCategoryName = CATEGORY_NAME)

        val actualResult = sut.toDomain()

        actualResult.getOrNull()?.categoryName?.value shouldBe CATEGORY_NAME
    }

    @Test
    fun `toDomain SHOULD map name`() {
        val sut = buildLocalCartItem(providedName = NAME)

        val actualResult = sut.toDomain()

        actualResult.getOrNull()?.name?.value shouldBe NAME
    }

    @Test
    fun `toDomain SHOULd map quantity`() {
        val sut = buildLocalCartItem(providedQuantity = QUANTITY)

        val actualResult = sut.toDomain()

        actualResult.getOrNull()?.quantity shouldBe QUANTITY
    }

    @Test
    fun `toDomain SHOULD map isInBasket to default value WHEN not provided`() {
        val sut = buildLocalCartItem()

        val actualResult = sut.toDomain()

        actualResult.getOrNull()?.isInBasket?.shouldBeFalse()
    }

    @Test
    fun `toDomain SHOULD map isInBasket with provided value`() {
        val sut = buildLocalCartItem(providedIsInBasket = IS_IN_BASKET)

        val actualResult = sut.toDomain()

        actualResult.getOrNull()?.isInBasket?.shouldBeTrue()
    }

    @Test
    fun `toLocal SHOULD map id`() {
        val sut = buildCartItem(providedId = ID)

        val actualResult = sut.toLocal()

        actualResult.cartItemId shouldBe ID
    }

    @Test
    fun `toLocal SHOULD map name`() {
        val sut = buildCartItem(providedName = NAME)

        val actualResult = sut.toLocal()

        actualResult.name shouldBe NAME
    }

    @Test
    fun `toLocal SHOULD map quantity`() {
        val sut = buildCartItem(providedQuantity = QUANTITY)

        val actualResult = sut.toLocal()

        actualResult.quantity shouldBe QUANTITY.toInt()
    }

    @Test
    fun `toLocal SHOULD map isInBasket`() {
        val sut = buildCartItem(providedIsInBasket = IS_IN_BASKET)

        val actualResult = sut.toLocal()

        actualResult.isInBasket shouldBe IS_IN_BASKET
    }

    @Test
    fun `toLocal SHOULD map isInBasket with default value WHEN it is not provided`() {
        val sut = buildCartItem()

        val actualResult = sut.toLocal()

        actualResult.isInBasket.shouldBeFalse()
    }
}

private const val ID = "id"
private const val CATEGORY_NAME = "categoryName"
private const val NAME = "name"
private const val QUANTITY = 1u
private const val IS_IN_BASKET = true
