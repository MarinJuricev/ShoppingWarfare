package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import com.marinj.shoppingwarfare.fixtures.category.buildProduct
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

private const val CATEGORY_ID = "id"

class ObserveProductsTest {

    private val productRepository: ProductRepository = mockk()

    private lateinit var sut: ObserveProducts

    @Before
    fun setUp() {
        sut = ObserveProducts(
            productRepository,
        )
    }

    @Test
    fun `invoke should return result from categoryDetailRepository observeCategoryItems`() =
        runTest {
            val product = buildProduct(
                providedCategoryName = CATEGORY_NAME,
                providedName = PRODUCT_NAME,
            )
            val products = listOf(product)
            val categoryItemFlow = flow {
                emit(products)
            }
            coEvery {
                productRepository.observeProducts(CATEGORY_ID)
            } coAnswers { categoryItemFlow }

            sut(CATEGORY_ID).test {
                assertThat(awaitItem()).isEqualTo(products)
                awaitComplete()
            }
        }
}

private const val CATEGORY_NAME = "categoryName"
private const val PRODUCT_NAME = "name"
