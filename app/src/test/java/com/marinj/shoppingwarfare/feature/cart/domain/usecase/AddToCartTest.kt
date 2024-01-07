package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import arrow.core.right
import com.marinj.shoppingwarfare.feature.cart.FakeSuccessCartRepository
import com.marinj.shoppingwarfare.feature.cart.buildCartItem
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class AddToCartTest {

    @Test
    fun `invoke SHOULD return result from repository when getCartItemById returns Left`() =
        runTest {
            val cartItem = buildCartItem(providedId = ID)
            val cartItems = listOf(cartItem)
            val repositoryResult = Unit.right()
            val sut = AddToCartImpl(FakeSuccessCartRepository(cartItems))

            val actualResult = sut(cartItem)

            actualResult shouldBe repositoryResult
        }

    @Test
    fun `invoke SHOULD update the existing cartItem quantity by 1 when getCartItemById returns Right and return the repository result`() =
        runTest {
            val existingCartItem = buildCartItem(
                providedId = ID,
                providedQuantity = QUANTITY.toUInt(),
            )
            val repositoryResult = Unit.right()
            val sut = AddToCartImpl(FakeSuccessCartRepository())

            val actualResult = sut(existingCartItem)

            actualResult shouldBe repositoryResult
        }
}

private const val ID = "id"
private const val QUANTITY = 1
