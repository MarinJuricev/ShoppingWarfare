package com.marinj.shoppingwarfare.feature.cart.data.model

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.cart.buildCartItem
import com.marinj.shoppingwarfare.feature.cart.buildLocalCartItem
import org.junit.Test

class LocalCartItemTest {
    @Test
    fun `toDomain SHOULD map id`() {
        val sut = buildLocalCartItem(providedId = ID)

        val actualResult = sut.toDomain()

        assertThat(actualResult.getOrNull()?.id).isEqualTo(ID)
    }

    @Test
    fun `toDomain SHOULD map categoryName`() {
        val sut = buildLocalCartItem(providedCategoryName = CATEGORY_NAME)

        val actualResult = sut.toDomain()

        assertThat(actualResult.getOrNull()?.categoryName).isEqualTo(CATEGORY_NAME)
    }

    @Test
    fun `toDomain SHOULD map name`() {
        val sut = buildLocalCartItem(providedName = NAME)

        val actualResult = sut.toDomain()

        assertThat(actualResult.getOrNull()?.name).isEqualTo(NAME)
    }

    @Test
    fun `toDomain SHOULd map quantity`() {
        val sut = buildLocalCartItem(providedQuantity = QUANTITY)

        val actualResult = sut.toDomain()

        assertThat(actualResult.getOrNull()?.quantity).isEqualTo(QUANTITY)
    }

    @Test
    fun `toDomain SHOULD map isInBasket to default value WHEN not provided`() {
        val sut = buildLocalCartItem()

        val actualResult = sut.toDomain()

        assertThat(actualResult.getOrNull()?.isInBasket).isFalse()
    }

    @Test
    fun `toDomain SHOULD map isInBasket with provided value`() {
        val sut = buildLocalCartItem(providedIsInBasket = IS_IN_BASKET)

        val actualResult = sut.toDomain()

        assertThat(actualResult.getOrNull()?.isInBasket).isTrue()
    }

    @Test
    fun `toLocal SHOULD map id`() {
        val sut = buildCartItem(providedId = ID)

        val actualResult = sut.getOrNull()?.toLocal()

        assertThat(actualResult?.cartItemId).isEqualTo(ID)
    }

    @Test
    fun `toLocal SHOULD map name`() {
        val sut = buildCartItem(providedName = NAME)

        val actualResult = sut.getOrNull()?.toLocal()

        assertThat(actualResult?.name).isEqualTo(NAME)
    }

    @Test
    fun `toLocal SHOULD map quantity`() {
        val sut = buildCartItem(providedQuantity = QUANTITY)

        val actualResult = sut.getOrNull()?.toLocal()

        assertThat(actualResult?.quantity).isEqualTo(QUANTITY)
    }

    @Test
    fun `toLocal SHOULD map isInBasket`() {
        val sut = buildCartItem(providedIsInBasket = IS_IN_BASKET)

        val actualResult = sut.getOrNull()?.toLocal()

        assertThat(actualResult?.isInBasket).isEqualTo(IS_IN_BASKET)
    }

    @Test
    fun `toLocal SHOULD map isInBasket with default value WHEN it is not provided`() {
        val sut = buildCartItem()

        val actualResult = sut.getOrNull()?.toLocal()

        assertThat(actualResult?.isInBasket).isFalse()
    }
}

private const val ID = "id"
private const val CATEGORY_NAME = "categoryName"
private const val NAME = "name"
private const val QUANTITY = 1u
private const val IS_IN_BASKET = true
