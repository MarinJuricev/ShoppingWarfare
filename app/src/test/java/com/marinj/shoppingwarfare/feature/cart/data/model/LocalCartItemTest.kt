package com.marinj.shoppingwarfare.feature.cart.data.model

import com.google.common.truth.Truth.*
import com.marinj.shoppingwarfare.feature.cart.buildLocalCartItem
import com.marinj.shoppingwarfare.feature.cart.data.mapper.CATEGORY_NAME
import com.marinj.shoppingwarfare.feature.cart.data.mapper.IS_IN_BASKET
import com.marinj.shoppingwarfare.feature.cart.data.mapper.NAME
import com.marinj.shoppingwarfare.feature.cart.data.mapper.QUANTITY
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
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

        assertThat(actualResult.quantity).isEqualTo(QUANTITY)
    }

    @Test
    fun `map SHOULD map isInQuantity`() {
        val localCartItem = mockk<LocalCartItem>(relaxed = true).apply {
            every { isInBasket } returns IS_IN_BASKET
        }

        val actualResult = sut.map(localCartItem)

        assertThat(actualResult.isInBasket).isEqualTo(IS_IN_BASKET)
    }
}

private const val ID = "id"
private const val CATEGORY_NAME = "categoryName"
private const val NAME = "name"
private const val QUANTITY = 1u