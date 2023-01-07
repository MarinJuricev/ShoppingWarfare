package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

private const val CATEGORY_ID = "id"

class ObserveProductsTest {

    private val categoryDetailRepository: ProductRepository = mockk()

    private lateinit var sut: ObserveProducts

    @Before
    fun setUp() {
        sut = ObserveProducts(
            categoryDetailRepository,
        )
    }

    @Test
    fun `invoke should return result from categoryDetailRepository observeCategoryItems`() =
        runTest {
            val categoryItem = mockk<Product>()
            val listOfCategoryItems = listOf(categoryItem)
            val categoryItemFlow = flow {
                emit(listOfCategoryItems)
            }
            coEvery {
                categoryDetailRepository.observeProducts(CATEGORY_ID)
            } coAnswers { categoryItemFlow }

            sut(CATEGORY_ID).test {
                assertThat(awaitItem()).isEqualTo(listOfCategoryItems)
                awaitComplete()
            }
        }
}
