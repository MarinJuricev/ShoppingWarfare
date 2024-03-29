package com.marinj.shoppingwarfare.feature.category.detail.presentation.viewmodel

import app.cash.turbine.test
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.fixture.FakeNavigator
import com.marinj.shoppingwarfare.core.navigation.NavigationEvent
import com.marinj.shoppingwarfare.core.navigation.Navigator
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.AddToCart
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.CreateProduct
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.DeleteProduct
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.ObserveProducts
import com.marinj.shoppingwarfare.feature.category.detail.presentation.mapper.ProductToCartItemMapper
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductEvent
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductEvent.OnCreateProduct
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductEvent.OnGetProducts
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductEvent.OnProductClicked
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductEvent.OnProductDelete
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductEvent.RestoreProductDeletion
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductViewEffect.AddedToCart
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductViewEffect.Error
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductViewEffect.ProductDeleted
import com.marinj.shoppingwarfare.fixtures.category.FakeFailureAddToCart
import com.marinj.shoppingwarfare.fixtures.category.FakeFailureCreateProduct
import com.marinj.shoppingwarfare.fixtures.category.FakeFailureDeleteProduct
import com.marinj.shoppingwarfare.fixtures.category.FakeFailureObserveProducts
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessAddToCart
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessCreateProduct
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessDeleteProduct
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessObserveProducts
import com.marinj.shoppingwarfare.fixtures.category.buildProduct
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineRule::class)
class ProductViewModelTest {

    private lateinit var sut: ProductViewModel

    @Test
    fun `should update products in viewState when OnGetCategoryProducts is provided and emits products`() =
        runTest {
            val product = buildProduct()
            val productList = listOf(product)

            sut = buildSut(providedObserveProducts = FakeSuccessObserveProducts(productList))

            sut.viewState.test {
                val initialViewState = awaitItem()
                initialViewState.products.shouldBeEmpty()
                initialViewState.isLoading.shouldBeTrue()

                sut.onEvent(OnGetProducts(CATEGORY_ID))

                val viewState = awaitItem()
                viewState.products shouldBe productList
                viewState.isLoading.shouldBeFalse()
            }
        }

    @Test
    fun `should update viewEffect with Error when OnGetCategoryProducts is provided and emits an exception`() =
        runTest {
            sut = buildSut(FakeFailureObserveProducts)

            sut.viewState.test {
                val initialViewState = awaitItem()
                initialViewState.products.shouldBeEmpty()
                initialViewState.isLoading.shouldBeTrue()

                sut.onEvent(OnGetProducts(CATEGORY_ID))

                val viewState = awaitItem()

                viewState.isLoading.shouldBeFalse()
                sut.viewEffect.test {
                    awaitItem() shouldBe Error("Failed to fetch category items, try again later.")
                }
            }
        }

    @Test
    fun `should log productCreated when OnCreateCategoryProduct is provided and CreateProduct returns Right`() =
        runTest {
            sut = buildSut()
            val event = OnCreateProduct(
                categoryId = CATEGORY_ID,
                productName = PRODUCT_NAME,
                categoryName = CATEGORY_NAME,
            )

            sut.onEvent(event)

            sut.viewEffect.test {
                expectNoEvents()
            }
        }

    @Test
    fun `should update viewEffect when OnCreateCategoryProduct is provided and CreateProduct returns Left`() =
        runTest {
            sut = buildSut(providedCreateProduct = FakeFailureCreateProduct)
            val event = OnCreateProduct(
                categoryId = CATEGORY_ID,
                productName = PRODUCT_NAME,
                categoryName = CATEGORY_NAME,
            )

            sut.onEvent(event)

            sut.viewEffect.test {
                awaitItem() shouldBe Error("Could not create $PRODUCT_NAME, try again later.")
            }
        }

    @Test
    fun `should update viewEffect when OnProductDelete is provided and DeleteProduct returns Right`() =
        runTest {
            val product = buildProduct(providedProductId = PRODUCT_ID)
            val event = OnProductDelete(product)
            sut = buildSut()

            sut.onEvent(event)

            sut.viewEffect.test {
                awaitItem() shouldBe ProductDeleted(product)
            }
        }

    @Test
    fun `should update viewEffect when OnProductDelete is provided and DeleteProduct returns Left`() =
        runTest {
            sut = buildSut(providedDeleteProduct = FakeFailureDeleteProduct)
            val product = buildProduct(
                providedProductId = PRODUCT_ID,
                providedName = PRODUCT_NAME,
            )
            val event = OnProductDelete(product)

            sut.onEvent(event)

            sut.viewEffect.test {
                awaitItem() shouldBe Error("Could not delete NonEmptyString(value=product), try again later.")
            }
        }

    @Test
    fun `should log when RestoreProductDeletion is provided and CreateProduct returns Right`() =
        runTest {
            sut = buildSut()
            val product = buildProduct(
                providedProductId = PRODUCT_ID,
                providedName = PRODUCT_NAME,
                providedCategoryId = CATEGORY_ID,
                providedCategoryName = CATEGORY_NAME,
            )
            val event = RestoreProductDeletion(product)

            sut.onEvent(event)

            sut.viewEffect.test {
                expectNoEvents()
            }
        }

    @Test
    fun `should update viewEffect when RestoreProductDeletion is provided and CreateProduct returns Left`() =
        runTest {
            sut = buildSut(providedCreateProduct = FakeFailureCreateProduct)
            val product = buildProduct(
                providedProductId = PRODUCT_ID,
                providedName = PRODUCT_NAME,
                providedCategoryId = CATEGORY_ID,
                providedCategoryName = CATEGORY_NAME,
            )
            val event = RestoreProductDeletion(product)

            sut.onEvent(event)

            sut.viewEffect.test {
                awaitItem() shouldBe Error("Could not create $PRODUCT_NAME, try again later.")
            }
        }

    @Test
    fun `should update viewEffect when OnProductClicked is provided and AddToCart returns Right`() =
        runTest {
            sut = buildSut()
            val product = buildProduct()
            val event = OnProductClicked(product)

            sut.onEvent(event)

            sut.viewEffect.test {
                awaitItem() shouldBe AddedToCart(product)
            }
        }

    @Test
    fun `should update viewEffect when OnProductClicked is provided and AddToCart returns Left`() =
        runTest {
            sut = buildSut(providedAddToCart = FakeFailureAddToCart)
            val product = buildProduct(providedName = PRODUCT_NAME)
            val event = OnProductClicked(product)

            sut.onEvent(event)

            sut.viewEffect.test {
                awaitItem() shouldBe Error("Could not add NonEmptyString(value=product) to Cart, try again later.")
            }
        }

    @Test
    fun `SHOULD emit navigateUp WHEN OnBackClicked is provided`() = runTest {
        val event = ProductEvent.OnBackClicked
        val navigator = FakeNavigator
        val sut = buildSut(
            providedNavigator = navigator,
        )

        navigator.receivedEvents.test {
            sut.onEvent(event)
            awaitItem() shouldBe NavigationEvent.NavigateUp
        }
    }
}

private fun buildSut(
    providedObserveProducts: ObserveProducts = FakeSuccessObserveProducts(),
    providedCreateProduct: CreateProduct = FakeSuccessCreateProduct,
    providedDeleteProduct: DeleteProduct = FakeSuccessDeleteProduct,
    providedAddToCart: AddToCart = FakeSuccessAddToCart,
    providedNavigator: Navigator = FakeNavigator,
): ProductViewModel = ProductViewModel(
    observeProducts = providedObserveProducts,
    createProduct = providedCreateProduct,
    deleteProduct = providedDeleteProduct,
    addToCart = providedAddToCart,
    productToCartItemMapper = ProductToCartItemMapper(),
    navigator = providedNavigator,
)

private const val CATEGORY_ID = "categoryId"
private const val CATEGORY_NAME = "fruits"
private const val PRODUCT_NAME = "product"
private const val PRODUCT_ID = "productId"
