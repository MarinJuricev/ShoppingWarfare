package com.marinj.shoppingwarfare.feature.category.detail.data.repository

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure.Unknown
import com.marinj.shoppingwarfare.feature.category.detail.data.mapper.toDomain
import com.marinj.shoppingwarfare.fixtures.category.FakeFailureProductApi
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessProductApi
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessProductDao
import com.marinj.shoppingwarfare.fixtures.category.buildLocalProduct
import com.marinj.shoppingwarfare.fixtures.category.buildProduct
import com.marinj.shoppingwarfare.fixtures.category.buildRemoteProduct
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class ProductRepositoryImplTest {

    @Test
    fun `observeProducts SHOULD return products from local source`() = runTest {
        val remoteProducts = listOf(buildRemoteProduct())
        val localProducts = listOf(
            buildLocalProduct(
                providedCategoryName = CATEGORY_NAME,
                providedName = PRODUCT_NAME,
                providedCategoryId = CATEGORY_ID,
                providedProductId = PRODUCT_ID,
            ),
        )
        val expectedResult = listOf(
            buildProduct(
                providedCategoryName = CATEGORY_NAME,
                providedName = PRODUCT_NAME,
            ),
        )
        val sut = ProductRepositoryImpl(
            productApi = FakeSuccessProductApi(remoteProducts),
            productDao = FakeSuccessProductDao(localProducts),
        )

        sut.observeProducts(PRODUCT_ID).test {
            awaitItem() shouldBe expectedResult
            awaitComplete()
        }
    }

    @Test
    fun `upsertProduct SHOULD return Right when productApi returns Right`() = runTest {
        val product = buildProduct(
            providedCategoryName = CATEGORY_NAME,
            providedName = PRODUCT_NAME,
            providedCategoryId = CATEGORY_ID,
        )
        val sut = ProductRepositoryImpl(
            productApi = FakeSuccessProductApi(),
            productDao = FakeSuccessProductDao(),
        )

        val result = sut.upsertProduct(product)

        result shouldBe Unit.right()
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

        result shouldBe Unknown.left()
    }

    @Test
    fun `deleteProductById SHOULD return Right when productApi returns Right`() = runTest {
        val product = buildProduct()
        val sut = ProductRepositoryImpl(
            productApi = FakeSuccessProductApi(),
            productDao = FakeSuccessProductDao(),
        )

        val result = sut.deleteProduct(product)

        result shouldBe Unit.right()
    }

    @Test
    fun `deleteProductById SHOULD return Left when productApi returns Left`() = runTest {
        val product = buildProduct()
        val sut = ProductRepositoryImpl(
            productApi = FakeFailureProductApi,
            productDao = FakeSuccessProductDao(),
        )

        val result = sut.deleteProduct(product)

        result shouldBe Unknown.left()
    }

    @Test
    fun `deleteProductById SHOULD return delete the item in productDao`() = runTest {
        val localProduct = buildLocalProduct(
            providedProductId = PRODUCT_ID,
            providedCategoryName = CATEGORY_NAME,
            providedName = PRODUCT_NAME,
            providedCategoryId = CATEGORY_ID,
        )
        val localProducts = listOf(localProduct)
        val dao = FakeSuccessProductDao(localProducts)
        val sut = ProductRepositoryImpl(
            productApi = FakeSuccessProductApi(),
            productDao = dao,
        )

        dao.upsertProduct(localProduct)
        dao.localProducts shouldBe listOf(localProduct)
        sut.deleteProduct(localProduct.toDomain().getOrNull()!!)
        dao.localProducts.shouldBeEmpty()
    }
}

private const val PRODUCT_ID = "productId"
private const val CATEGORY_NAME = "categoryName"
private const val CATEGORY_ID = "categoryId"
private const val PRODUCT_NAME = "name"
