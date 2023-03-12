package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

private const val PRODUCT_ID = "productId"

class DeleteProductTest {

    private val categoryDetailRepository: ProductRepository = mockk()

    private lateinit var sut: DeleteProduct

    @Before
    fun setUp() {
        sut = DeleteProduct(
            categoryDetailRepository,
        )
    }

    @Test
    fun `invoke should return result from categoryDetailRepository`() = runTest {
        val repositoryResult = Unit.right()

        coEvery {
            categoryDetailRepository.deleteProductById(PRODUCT_ID)
        } coAnswers { repositoryResult }

        val actualResult = sut(PRODUCT_ID)

        assertThat(actualResult).isEqualTo(repositoryResult)
    }
}
