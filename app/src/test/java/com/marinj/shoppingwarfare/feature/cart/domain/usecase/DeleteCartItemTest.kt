package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import arrow.core.right
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.cart.FakeSuccessCartRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DeleteCartItemTest {

    @Test
    fun `invoke SHOULD return result from cartRepository deleteCartItemById`() = runTest {
        val sut = DeleteCartItemImpl(FakeSuccessCartRepository())
        val expectedResult = Unit.right()

        val actualResult = sut(ID)

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}

private const val ID = "id"