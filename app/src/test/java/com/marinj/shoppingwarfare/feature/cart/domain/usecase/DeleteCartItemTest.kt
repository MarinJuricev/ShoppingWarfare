package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.google.common.truth.Truth
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DeleteCartItemTest {

    private val cartRepository: CartRepository = mockk()

    private lateinit var sut: DeleteCartItem

    @Before
    fun setUp() {
        sut = DeleteCartItem(
            cartRepository,
        )
    }

    @Test
    fun `invoke should return result from cartRepository deleteCartItemById`() = runBlockingTest {
        val id = "id"
        val failure = Failure.Unknown.buildLeft()
        coEvery {
            cartRepository.deleteCartItemById(id)
        } coAnswers { failure }

        val actualResult = sut(id)

        Truth.assertThat(actualResult).isEqualTo(failure)
    }
}