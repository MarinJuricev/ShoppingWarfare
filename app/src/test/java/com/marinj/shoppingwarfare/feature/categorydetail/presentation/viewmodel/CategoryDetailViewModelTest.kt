package com.marinj.shoppingwarfare.feature.categorydetail.presentation.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.AddToCart
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.Product
import com.marinj.shoppingwarfare.feature.categorydetail.domain.usecase.CreateProduct
import com.marinj.shoppingwarfare.feature.categorydetail.domain.usecase.DeleteProduct
import com.marinj.shoppingwarfare.feature.categorydetail.domain.usecase.ObserveCategoryProducts
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEffect
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEvent.OnGetCategoryProducts
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

private const val CATEGORY_ID = "categoryId"

@ExperimentalTime
@ExperimentalCoroutinesApi
class CategoryDetailViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val observeCategoryProducts: ObserveCategoryProducts = mockk()
    private val createProduct: CreateProduct = mockk()
    private val deleteProduct: DeleteProduct = mockk()
    private val productToCartItemMapper: Mapper<CartItem, Product> = mockk()
    private val addToCart: AddToCart = mockk()

    private lateinit var sut: CategoryDetailViewModel

    @Before
    fun setUp() {
        sut = CategoryDetailViewModel(
            observeCategoryProducts,
            createProduct,
            deleteProduct,
            productToCartItemMapper,
            addToCart,
        )
    }

    @Test
    fun `should update products in viewState when OnGetCategoryProducts is provided and emits products`() =
        runBlockingTest {
            val product = mockk<Product>()
            val productList = listOf(product)

            coEvery {
                observeCategoryProducts(CATEGORY_ID)
            } coAnswers { flow { emit(productList) } }

            sut.viewState.test {
                val initialViewState = awaitItem()
                assertThat(initialViewState.products).isEmpty()
                assertThat(initialViewState.isLoading).isTrue()

                sut.onEvent(OnGetCategoryProducts(CATEGORY_ID))

                val viewState = awaitItem()
                assertThat(viewState.products).isEqualTo(productList)
                assertThat(viewState.isLoading).isFalse()
            }
        }

    @Test
    fun `should update viewEffect with Error when OnGetCategoryProducts is provided and emits an exception`() =
        runBlockingTest {
            coEvery {
                observeCategoryProducts(CATEGORY_ID)
            } coAnswers { flow { throw Exception() } }

            sut.viewState.test {
                val initialViewState = awaitItem()
                assertThat(initialViewState.products).isEmpty()
                assertThat(initialViewState.isLoading).isTrue()

                sut.onEvent(OnGetCategoryProducts(CATEGORY_ID))

                val viewState = awaitItem()

                assertThat(viewState.isLoading).isFalse()
                sut.viewEffect.test {
                    assertThat(awaitItem()).isEqualTo(CategoryDetailEffect.Error("Failed to fetch category items, try again later."))
                }
            }
        }
}
