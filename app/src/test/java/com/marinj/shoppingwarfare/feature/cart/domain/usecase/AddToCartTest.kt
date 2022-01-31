package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

private const val ID = "id"
private const val NAME = "cartName"
private const val CATEGORY_NAME = "fruits"
private const val QUANTITY = 1
private const val UPDATED_QUANTITY = 2

class AddToCartTest {

    private val cartRepository: CartRepository = mockk()

    private lateinit var sut: AddToCart

    @Before
    fun setUp() {
        sut = AddToCart(
            cartRepository,
        )
    }

    @Test
    fun `invoke should return result from repository when getCartItemById returns Left`() =
        runTest {
            val cartItem = mockk<CartItem>().apply {
                every { id } returns ID
            }
            val repositoryResult = Unit.buildRight()
            coEvery {
                cartRepository.getCartItemById(ID)
            } coAnswers { Failure.Unknown.buildLeft() }
            coEvery {
                cartRepository.upsertCartItem(cartItem)
            } coAnswers { repositoryResult }

            val actualResult = sut(cartItem)

            assertThat(actualResult).isEqualTo(repositoryResult)
        }

    @Test
    fun `invoke should update the existing cartItem quantity by 1 when getCartItemById returns Right and return the repository result`() =
        runTest {
            val existingCartItem = CartItem(
                id = ID,
                categoryName = NAME,
                name = CATEGORY_NAME,
                quantity = QUANTITY,
                isInBasket = false
            )
            val updatedCartItem = existingCartItem.copy(quantity = UPDATED_QUANTITY)
            val repositoryResult = Unit.buildRight()
            coEvery {
                cartRepository.getCartItemById(ID)
            } coAnswers { existingCartItem.buildRight() }
            coEvery {
                cartRepository.upsertCartItem(updatedCartItem)
            } coAnswers { repositoryResult }

            val actualResult = sut(existingCartItem)

            assertThat(actualResult).isEqualTo(repositoryResult)
        }
}
