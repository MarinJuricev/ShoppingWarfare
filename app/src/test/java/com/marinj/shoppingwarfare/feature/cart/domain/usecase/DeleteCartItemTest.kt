package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import arrow.core.right
import com.marinj.shoppingwarfare.feature.cart.FakeSuccessCartRepository
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class DeleteCartItemTest {

    @Test
    fun `invoke SHOULD return result from cartRepository deleteCartItemById`() = runTest {
        val sut = DeleteCartItemImpl(FakeSuccessCartRepository())
        val expectedResult = Unit.right()

        val actualResult = sut(ID)

        actualResult shouldBe expectedResult
    }
}

private const val ID = "id"
