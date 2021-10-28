package com.marinj.shoppingwarfare.feature.category.detail.presentation.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.result.Failure.Unknown
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.AddToCart
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.CreateProduct
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.DeleteProduct
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.ObserveCategoryProducts
import com.marinj.shoppingwarfare.feature.category.detail.presentation.mapper.ProductToCartItemMapper
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEffect.AddedToCart
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEffect.Error
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEffect.ProductDeleted
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEvent.OnCreateCategoryProduct
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEvent.OnGetCategoryProducts
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEvent.OnProductClicked
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEvent.OnProductDelete
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEvent.RestoreProductDeletion
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

private const val CATEGORY_ID = "categoryId"
private const val CATEGORY_NAME = "fruits"
private const val PRODUCT_NAME = "product"
private const val PRODUCT_ID = "productId"

@ExperimentalTime
@ExperimentalCoroutinesApi
class CategoryDetailViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val observeCategoryProducts: ObserveCategoryProducts = mockk()
    private val createProduct: CreateProduct = mockk()
    private val deleteProduct: DeleteProduct = mockk()
    private val productToCartItemMapper: ProductToCartItemMapper = mockk()
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
                    assertThat(awaitItem()).isEqualTo(Error("Failed to fetch category items, try again later."))
                }
            }
        }

    @Test
    fun `should log productCreated when OnCreateCategoryProduct is provided and CreateProduct returns Right`() =
        runBlockingTest {
            val event = OnCreateCategoryProduct(
                categoryId = CATEGORY_ID,
                productName = PRODUCT_NAME,
                categoryName = CATEGORY_NAME,
            )
            coEvery {
                createProduct(
                    categoryId = CATEGORY_ID,
                    categoryName = CATEGORY_NAME,
                    productName = PRODUCT_NAME,
                )
            } coAnswers { Unit.buildRight() }

            sut.onEvent(event)

            sut.viewEffect.test {
                expectNoEvents()
            }
        }

    @Test
    fun `should update viewEffect when OnCreateCategoryProduct is provided and CreateProduct returns Left`() =
        runBlockingTest {
            val event = OnCreateCategoryProduct(
                categoryId = CATEGORY_ID,
                productName = PRODUCT_NAME,
                categoryName = CATEGORY_NAME,
            )
            coEvery {
                createProduct(
                    categoryId = CATEGORY_ID,
                    categoryName = CATEGORY_NAME,
                    productName = PRODUCT_NAME,
                )
            } coAnswers { Unknown.buildLeft() }

            sut.onEvent(event)

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(Error("Could not create $PRODUCT_NAME, try again later."))
            }
        }

    @Test
    fun `should update viewEffect when OnProductDelete is provided and DeleteProduct returns Right`() =
        runBlockingTest {
            val product = mockk<Product>().apply {
                every { id } answers { PRODUCT_ID }
            }
            val event = OnProductDelete(product)
            coEvery {
                deleteProduct(PRODUCT_ID)
            } coAnswers { Unit.buildRight() }

            sut.onEvent(event)

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(ProductDeleted(product))
            }
        }

    @Test
    fun `should update viewEffect when OnProductDelete is provided and DeleteProduct returns Left`() =
        runBlockingTest {
            val product = mockk<Product>().apply {
                every { id } answers { PRODUCT_ID }
                every { name } answers { PRODUCT_NAME }
            }
            val event = OnProductDelete(product)
            coEvery {
                deleteProduct(PRODUCT_ID)
            } coAnswers { Unknown.buildLeft() }

            sut.onEvent(event)

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(Error("Could not delete $PRODUCT_NAME, try again later."))
            }
        }

    @Test
    fun `should log when RestoreProductDeletion is provided and CreateProduct returns Right`() =
        runBlockingTest {
            val product = mockk<Product>().apply {
                every { id } answers { PRODUCT_ID }
                every { categoryId } answers { CATEGORY_ID }
                every { categoryName } answers { CATEGORY_NAME }
                every { name } answers { PRODUCT_NAME }
            }
            val event = RestoreProductDeletion(product)
            coEvery {
                createProduct(
                    categoryId = CATEGORY_ID,
                    categoryName = CATEGORY_NAME,
                    productName = PRODUCT_NAME,
                )
            } coAnswers { Unit.buildRight() }

            sut.onEvent(event)

            sut.viewEffect.test {
                expectNoEvents()
            }
        }

    @Test
    fun `should update viewEffect when RestoreProductDeletion is provided and CreateProduct returns Left`() =
        runBlockingTest {
            val product = mockk<Product>().apply {
                every { id } answers { PRODUCT_ID }
                every { categoryId } answers { CATEGORY_ID }
                every { categoryName } answers { CATEGORY_NAME }
                every { name } answers { PRODUCT_NAME }
            }
            val event = RestoreProductDeletion(product)
            coEvery {
                createProduct(
                    categoryId = CATEGORY_ID,
                    categoryName = CATEGORY_NAME,
                    productName = PRODUCT_NAME,
                )
            } coAnswers { Unknown.buildLeft() }

            sut.onEvent(event)

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(Error("Could not create $PRODUCT_NAME, try again later."))
            }
        }

    @Test
    fun `should update viewEffect when OnProductClicked is provided and AddToCart returns Right`() =
        runBlockingTest {
            val product = mockk<Product>()
            val cartItem = mockk<CartItem>()
            val event = OnProductClicked(product)
            coEvery {
                addToCart(cartItem)
            } coAnswers { Unit.buildRight() }
            coEvery {
                productToCartItemMapper.map(product)
            } coAnswers { cartItem }

            sut.onEvent(event)

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(AddedToCart(product))
            }
        }

    @Test
    fun `should update viewEffect when OnProductClicked is provided and AddToCart returns Left`() =
        runBlockingTest {
            val product = mockk<Product>().apply {
                every { name } answers { PRODUCT_NAME }
            }
            val cartItem = mockk<CartItem>()
            val event = OnProductClicked(product)
            coEvery {
                addToCart(cartItem)
            } coAnswers { Unknown.buildLeft() }
            coEvery {
                productToCartItemMapper.map(product)
            } coAnswers { cartItem }

            sut.onEvent(event)

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(Error("Could not add $PRODUCT_NAME to Cart, try again later."))
            }
        }
}
