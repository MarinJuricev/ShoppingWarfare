package com.marinj.shoppingwarfare.feature.categorydetail.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.categorydetail.domain.repository.CategoryDetailRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val PRODUCT_ID = "productId"

@ExperimentalCoroutinesApi
class DeleteProductTest {

    private val categoryDetailRepository: CategoryDetailRepository = mockk()

    private lateinit var sut: DeleteProduct

    @Before
    fun setUp() {
        sut = DeleteProduct(
            categoryDetailRepository,
        )
    }

    @Test
    fun `invoke should return result from categoryDetailRepository`() = runBlockingTest {
        val repositoryResult = Unit.buildRight()

        coEvery {
            categoryDetailRepository.deleteCategoryProductById(PRODUCT_ID)
        } coAnswers { repositoryResult }

        val actualResult = sut(PRODUCT_ID)

        assertThat(actualResult).isEqualTo(repositoryResult)
    }
}
