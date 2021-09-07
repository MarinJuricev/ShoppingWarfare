package com.marinj.shoppingwarfare.feature.categorydetail.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.CategoryProduct
import com.marinj.shoppingwarfare.feature.categorydetail.domain.repository.CategoryDetailRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

private const val CATEGORY_ID = "id"

@ExperimentalTime
@ExperimentalCoroutinesApi
class ObserveCategoryProductsTest {

    private val categoryDetailRepository: CategoryDetailRepository = mockk()

    private lateinit var sut: ObserveCategoryProducts

    @Before
    fun setUp() {
        sut = ObserveCategoryProducts(
            categoryDetailRepository,
        )
    }

    @Test
    fun `invoke should return result from categoryDetailRepository observeCategoryItems`() = runBlockingTest {
        val categoryItem = mockk<CategoryProduct>()
        val listOfCategoryItems = listOf(categoryItem)
        val categoryItemFlow = flow {
            emit(listOfCategoryItems)
        }
        coEvery {
            categoryDetailRepository.observeCategoryProducts(CATEGORY_ID)
        } coAnswers { categoryItemFlow }

        sut(CATEGORY_ID).test {
            assertThat(awaitItem()).isEqualTo(listOfCategoryItems)
            awaitComplete()
        }
    }
}
