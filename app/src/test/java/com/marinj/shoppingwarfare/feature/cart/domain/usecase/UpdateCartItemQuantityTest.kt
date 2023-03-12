package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import arrow.core.left
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

private const val ID = "id"
private const val UPDATED_QUANTITY = 5

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
    fun `invoke SHOULD trigger updateCartItemQuantity and return result from cartRepository`() =
        runTest {
            val repositoryResult = Failure.Unknown.left()
            coEvery {
                cartRepository.updateCartItemQuantity(ID, UPDATED_QUANTITY)
            } coAnswers { repositoryResult }

            val actualResult = sut(ID, UPDATED_QUANTITY)

            assertThat(actualResult).isEqualTo(repositoryResult)
        }
}
