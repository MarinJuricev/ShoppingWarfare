package com.marinj.shoppingwarfare.feature.category.detail.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure.Unknown
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import com.marinj.shoppingwarfare.feature.category.detail.presentation.navigation.CATEGORY_ID_PARAM
import com.marinj.shoppingwarfare.fixtures.category.FakeFailureProductApi
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessProductApi
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessProductDao
import com.marinj.shoppingwarfare.fixtures.category.buildLocalCategoryProducts
import com.marinj.shoppingwarfare.fixtures.category.buildLocalProduct
import com.marinj.shoppingwarfare.fixtures.category.buildProduct
import com.marinj.shoppingwarfare.fixtures.category.buildRemoteProduct
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ProductRepositoryImplTest {

    private lateinit var sut: ProductRepository

    @Test
    fun `observeProducts SHOULD return products from local source`() = runTest {
        val remoteProducts = listOf(buildRemoteProduct())
        val localCategoryProducts = listOf(
            buildLocalCategoryProducts(
                providedLocalProductList = listOf(
                    buildLocalProduct(
                        providedCategoryName = CATEGORY_NAME,
                        providedName = PRODUCT_NAME,
                        providedCategoryId = CATEGORY_ID,
                    ),
                ),
            ),
        )
        val expectedResult = listOf(
            buildProduct(
                providedCategoryName = CATEGORY_NAME,
                providedName = PRODUCT_NAME,
            ),
        )
        sut = ProductRepositoryImpl(
            productApi = FakeSuccessProductApi(remoteProducts),
            productDao = FakeSuccessProductDao(localCategoryProducts),
        )

        sut.observeProducts(PRODUCT_ID).test {
            assertThat(awaitItem()).isEqualTo(expectedResult)
            awaitComplete()
        }
    }

    @Test
    fun `upsertProduct SHOULD return Right when productApi returns Right`() = runTest {
        val product = buildProduct(
            providedCategoryName = CATEGORY_NAME,
            providedName = PRODUCT_NAME,
        )
        val remoteProducts = listOf(
            buildRemoteProduct(
                providedCategoryName = CATEGORY_NAME,
                providedName = PRODUCT_NAME,
                providedCategoryId = CATEGORY_ID,
            ),
        )
        val productApi = FakeSuccessProductApi()
        val sut = ProductRepositoryImpl(
            productApi = productApi,
            productDao = FakeSuccessProductDao(),
        )

        val result = sut.upsertProduct(product)

        assertThat(result).isEqualTo(Unit.buildRight())
        assertThat(productApi.remoteProducts).isEqualTo(remoteProducts)
    }

    @Test
    fun `upsertProduct SHOULD return Left when productApi returns Left`() = runTest {
        val product = buildProduct(
            providedCategoryName = CATEGORY_NAME,
            providedName = PRODUCT_NAME,
        )
        val sut = ProductRepositoryImpl(
            productApi = FakeFailureProductApi,
            productDao = FakeSuccessProductDao(),
        )

        val result = sut.upsertProduct(product)

        assertThat(result).isEqualTo(Unknown.buildLeft())
    }

    @Test
    fun `deleteProductById SHOULD return Right when productApi returns Right`() = runTest {
        val sut = ProductRepositoryImpl(
            productApi = FakeSuccessProductApi(),
            productDao = FakeSuccessProductDao(),
        )

        val result = sut.deleteProductById(PRODUCT_ID)

        assertThat(result).isEqualTo(Unit.buildRight())
    }

    @Test
    fun `deleteProductById SHOULD return Left when productApi returns Left`() = runTest {
        val sut = ProductRepositoryImpl(
            productApi = FakeFailureProductApi,
            productDao = FakeSuccessProductDao(),
        )

        val result = sut.deleteProductById(PRODUCT_ID)

        assertThat(result).isEqualTo(Unknown.buildLeft())
    }

    @Test
    fun `deleteProductById SHOULD return delete the item in productDao`() = runTest {
        val localProduct = buildLocalProduct(
            providedProductId = PRODUCT_ID,
            providedCategoryName = CATEGORY_NAME,
            providedName = PRODUCT_NAME,
        )
        val localCategoryProducts = listOf(
            buildLocalCategoryProducts(
                providedLocalProductList = listOf(localProduct),
            ),
        )
        val dao = FakeSuccessProductDao(localCategoryProducts)
        val sut = ProductRepositoryImpl(
            productApi = FakeSuccessProductApi(),
            productDao = dao,
        )

        dao.upsertProduct(localProduct)
        assertThat(dao.localProducts).isEqualTo(listOf(localProduct))
        sut.deleteProductById(PRODUCT_ID)
        assertThat(dao.localProducts).isEmpty()
    }
}

private const val PRODUCT_ID = "productId"
private const val CATEGORY_NAME = "categoryName"
private const val CATEGORY_ID = "categoryId"
private const val PRODUCT_NAME = "name"
