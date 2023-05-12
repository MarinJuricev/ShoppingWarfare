package com.marinj.shoppingwarfare.feature.category.detail.presentation.viewmodel

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.result.Failure.Unknown
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.AddToCart
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.CreateProductImpl
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.DeleteProductImpl
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.ObserveProducts
import com.marinj.shoppingwarfare.feature.category.detail.presentation.mapper.ProductToCartItemMapper
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductEvent.OnCreateProduct
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductEvent.OnGetProducts
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductEvent.OnProductClicked
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductEvent.OnProductDelete
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductEvent.RestoreProductDeletion
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductViewEffect.AddedToCart
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductViewEffect.Error
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductViewEffect.ProductDeleted
import com.marinj.shoppingwarfare.fixtures.category.buildProduct
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val observeProducts: ObserveProducts = mockk()
    private val createProduct: CreateProductImpl = mockk()
    private val deleteProduct: DeleteProductImpl = mockk()
    private val productToCartItemMapper: ProductToCartItemMapper = mockk()
    private val addToCart: AddToCart = mockk()

    private lateinit var sut: ProductViewModel

    @Before
    fun setUp() {
        sut = ProductViewModel(
            observeProducts,
            createProduct,
            deleteProduct,
            productToCartItemMapper,
            addToCart,
        )
    }

    @Test
    fun `should update products in viewState when OnGetCategoryProducts is provided and emits products`() =
        runTest {
            val product = mockk<Product>()
            val productList = listOf(product)

            coEvery {
                observeProducts(CATEGORY_ID)
            } coAnswers { flow { emit(productList) } }

            sut.viewState.test {
                val initialViewState = awaitItem()
                assertThat(initialViewState.products).isEmpty()
                assertThat(initialViewState.isLoading).isTrue()

                sut.onEvent(OnGetProducts(CATEGORY_ID))

                val viewState = awaitItem()
                assertThat(viewState.products).isEqualTo(productList)
                assertThat(viewState.isLoading).isFalse()
            }
        }

    @Test
    fun `should update viewEffect with Error when OnGetCategoryProducts is provided and emits an exception`() =
        runTest {
            coEvery {
                observeProducts(CATEGORY_ID)
            } coAnswers { flow { throw Exception() } }

            sut.viewState.test {
                val initialViewState = awaitItem()
                assertThat(initialViewState.products).isEmpty()
                assertThat(initialViewState.isLoading).isTrue()

                sut.onEvent(OnGetProducts(CATEGORY_ID))

                val viewState = awaitItem()

                assertThat(viewState.isLoading).isFalse()
                sut.viewEffect.test {
                    assertThat(awaitItem()).isEqualTo(Error("Failed to fetch category items, try again later."))
                }
            }
        }

    @Test
    fun `should log productCreated when OnCreateCategoryProduct is provided and CreateProduct returns Right`() =
        runTest {
            val event = OnCreateProduct(
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
            } coAnswers { Unit.right() }

            sut.onEvent(event)

            sut.viewEffect.test {
                expectNoEvents()
            }
        }

    @Test
    fun `should update viewEffect when OnCreateCategoryProduct is provided and CreateProduct returns Left`() =
        runTest {
            val event = OnCreateProduct(
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
            } coAnswers { Unknown.left() }

            sut.onEvent(event)

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(Error("Could not create $PRODUCT_NAME, try again later."))
            }
        }

    @Test
    fun `should update viewEffect when OnProductDelete is provided and DeleteProduct returns Right`() =
        runTest {
            val product = buildProduct(providedProductId = PRODUCT_ID)
            val event = OnProductDelete(product)
            coEvery {
                deleteProduct(PRODUCT_ID)
            } coAnswers { Unit.right() }

            sut.onEvent(event)

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(ProductDeleted(product))
            }
        }

    @Test
    fun `should update viewEffect when OnProductDelete is provided and DeleteProduct returns Left`() =
        runTest {
            val product = buildProduct(
                providedProductId = PRODUCT_ID,
                providedName = PRODUCT_NAME,
            )
            val event = OnProductDelete(product)
            coEvery {
                deleteProduct(PRODUCT_ID)
            } coAnswers { Unknown.left() }

            sut.onEvent(event)

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(Error("Could not delete $PRODUCT_NAME, try again later."))
            }
        }

    @Test
    fun `should log when RestoreProductDeletion is provided and CreateProduct returns Right`() =
        runTest {
            val product = buildProduct(
                providedProductId = PRODUCT_ID,
                providedName = PRODUCT_NAME,
                providedCategoryId = CATEGORY_ID,
                providedCategoryName = CATEGORY_NAME,
            )
            val event = RestoreProductDeletion(product)
            coEvery {
                createProduct(
                    categoryId = CATEGORY_ID,
                    categoryName = CATEGORY_NAME,
                    productName = PRODUCT_NAME,
                )
            } coAnswers { Unit.right() }

            sut.onEvent(event)

            sut.viewEffect.test {
                expectNoEvents()
            }
        }

    @Test
    fun `should update viewEffect when RestoreProductDeletion is provided and CreateProduct returns Left`() =
        runTest {
            val product = buildProduct(
                providedProductId = PRODUCT_ID,
                providedName = PRODUCT_NAME,
                providedCategoryId = CATEGORY_ID,
                providedCategoryName = CATEGORY_NAME,
            )
            val event = RestoreProductDeletion(product)
            coEvery {
                createProduct(
                    categoryId = CATEGORY_ID,
                    categoryName = CATEGORY_NAME,
                    productName = PRODUCT_NAME,
                )
            } coAnswers { Unknown.left() }

            sut.onEvent(event)

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(Error("Could not create $PRODUCT_NAME, try again later."))
            }
        }

    @Test
    fun `should update viewEffect when OnProductClicked is provided and AddToCart returns Right`() =
        runTest {
            val product = mockk<Product>()
            val cartItem = mockk<CartItem>()
            val event = OnProductClicked(product)
            coEvery {
                addToCart(cartItem)
            } coAnswers { Unit.right() }
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
        runTest {
            val product = buildProduct(providedName = PRODUCT_NAME)
            val cartItem = mockk<CartItem>()
            val event = OnProductClicked(product)
            coEvery {
                addToCart(cartItem)
            } coAnswers { Unknown.left() }
            coEvery {
                productToCartItemMapper.map(product)
            } coAnswers { cartItem }

            sut.onEvent(event)

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(Error("Could not add $PRODUCT_NAME to Cart, try again later."))
            }
        }
}

private const val CATEGORY_ID = "categoryId"
private const val CATEGORY_NAME = "fruits"
private const val PRODUCT_NAME = "product"
private const val PRODUCT_ID = "productId"
