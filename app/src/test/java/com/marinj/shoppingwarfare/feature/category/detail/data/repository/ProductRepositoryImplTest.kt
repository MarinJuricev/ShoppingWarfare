package com.marinj.shoppingwarfare.feature.category.detail.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
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

//    @Test
//    fun `observeCategoryProducts should return products`() = runTest {
//        val localCategoryProducts = mockk<LocalCategoryProducts>()
//        val listOfLocalCategoryProducts = listOf(localCategoryProducts)
//        val product = mockk<Product>()
//        val listOfProducts = listOf(product)
//        coEvery {
//            productDao.observeProductsForGivenCategoryId(CATEGORY_ID)
//        } coAnswers {
//            flow {
//                emit(listOfLocalCategoryProducts)
//            }
//        }
//
//        sut.observeProducts(CATEGORY_ID).test {
//            assertThat(awaitItem()).isEqualTo(listOfProducts)
//            awaitComplete()
//        }
//    }

//    @Test
//    fun `upsertCategoryProduct should return Left when productDao returns 0L`() = runTest {
//        val product = mockk<Product>()
//        val localProduct = mockk<LocalProduct>()
//        coEvery {
//            productDao.upsertProduct(localProduct)
//        } coAnswers { 0L }
//
//        val actualResult = sut.upsertProduct(product)
//        val expectedResult = ErrorMessage("Error while adding new category product").buildLeft()
//
//        assertThat(actualResult).isEqualTo(expectedResult)
//    }

//    @Test
//    fun `upsertCategoryProduct should return Right when productDao returns result other than 0L`() =
//        runTest {
//            val product = mockk<Product>()
//            val localProduct = mockk<LocalProduct>()
//            coEvery {
//                productDao.upsertProduct(localProduct)
//            } coAnswers { 1L }
//
//            val actualResult = sut.upsertProduct(product)
//            val expectedResult = Unit.buildRight()
//
//            assertThat(actualResult).isEqualTo(expectedResult)
//        }

//    @Test
//    fun `deleteCategoryProductById should return Right`() = runTest {
//        coEvery {
//            productDao.deleteProductById(PRODUCT_ID)
//        } coAnswers { Unit }
//
//        val actualResult = sut.deleteProductById(PRODUCT_ID)
//        val expectedResult = Unit.buildRight()
//
//        assertThat(actualResult).isEqualTo(expectedResult)
//    }
}

private const val PRODUCT_ID = "productId"
private const val CATEGORY_NAME = "categoryName"
private const val PRODUCT_NAME = "name"
