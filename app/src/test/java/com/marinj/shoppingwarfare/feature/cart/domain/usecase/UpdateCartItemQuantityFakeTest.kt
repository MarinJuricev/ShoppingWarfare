package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import arrow.core.Either
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class UpdateCartItemQuantityFakeTest {

    private val cartRepository: CartRepository = FakeCartRepository()

    private val sut = UpdateCartItemQuantityImpl(
        cartRepository,
    )

    @Test
    fun `invoke SHOULD trigger updateCartItemQuantity and return result from cartRepository`() =
        runTest {
            val actualResult = sut(ID, UPDATED_QUANTITY)

            actualResult shouldBe Unit.right()
        }

    private inner class FakeCartRepository : CartRepository {

        override suspend fun updateCartItemQuantity(
            cartItemId: String,
            newQuantity: Int,
        ): Either<Failure, Unit> = Unit.right()

        override fun observeCartItems(): Flow<List<CartItem>> {
            TODO("Not yet implemented")
        }

        override fun observeCartItemsCount(): Flow<Int?> {
            TODO("Not yet implemented")
        }

        override suspend fun updateCartItemIsInBasket(cartItemId: String, updatedIsInBasket: Boolean): Either<Failure, Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun upsertCartItem(cartItem: CartItem): Either<Failure, Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteCartItemById(id: String): Either<Failure, Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun getCartItemById(id: String): Either<Failure, CartItem> {
            TODO("Not yet implemented")
        }

        override suspend fun dropCurrentCart(): Either<Failure, Unit> {
            TODO("Not yet implemented")
        }
    }
}

private const val ID = "id"
private const val UPDATED_QUANTITY = 5
