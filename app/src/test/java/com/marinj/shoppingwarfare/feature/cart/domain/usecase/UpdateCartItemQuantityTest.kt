package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val ID = "id"
private const val CATEGORY_NAME = "categoryName"
private const val NAME = "name"
private const val INITIAL_QUANTITY = 1
private const val UPDATED_QUANTITY = 5

@ExperimentalCoroutinesApi
class UpdateCartItemQuantityTest {

    private val cartRepository: CartRepository = mockk()

    private lateinit var sut: UpdateCartItemQuantity

    @Before
    fun setUp() {
        sut = UpdateCartItemQuantity(
            cartRepository,
        )
    }

    @Test
    fun `invoke should trigger upsertCartItem with updatedCartItem and return result from cartRepository`() =
        runBlockingTest {
            val cartItemToUpdate = CartItem(
                id = ID,
                categoryName = CATEGORY_NAME,
                name = NAME,
                quantity = INITIAL_QUANTITY,
            )
            val repositoryResult = Failure.Unknown.buildLeft()
            val expectedResult = cartItemToUpdate.copy(
                quantity = UPDATED_QUANTITY,
            )
            coEvery {
                cartRepository.upsertCartItem(expectedResult)
            } coAnswers { repositoryResult }

            val actualResult = sut(cartItemToUpdate, UPDATED_QUANTITY)

            assertThat(actualResult).isEqualTo(repositoryResult)
        }
}
