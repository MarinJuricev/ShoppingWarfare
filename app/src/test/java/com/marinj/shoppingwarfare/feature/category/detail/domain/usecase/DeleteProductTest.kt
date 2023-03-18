package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import arrow.core.left
import arrow.core.right
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure.Unknown
import com.marinj.shoppingwarfare.fixtures.category.FakeFailureProductRepository
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessProductRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DeleteProductTest {
    @Test
    fun `invoke should RETURN result from repository`() = runTest {
        val sut = DeleteProductImpl(
            productRepository = FakeSuccessProductRepository()
        )

        val actualResult = sut(PRODUCT_ID)

        assertThat(actualResult).isEqualTo(Unit.right())
    }

    @Test
    fun `invoke should RETURN failure from repository`() = runTest {
        val sut = DeleteProductImpl(
            productRepository = FakeFailureProductRepository
        )

        val actualResult = sut(PRODUCT_ID)

        assertThat(actualResult).isEqualTo(Unknown.left())
    }
}

private const val PRODUCT_ID = "productId"
