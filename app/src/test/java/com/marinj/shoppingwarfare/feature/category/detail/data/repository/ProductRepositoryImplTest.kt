package com.marinj.shoppingwarfare.feature.category.detail.data.repository

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.detail.data.datasource.local.ProductDao
import com.marinj.shoppingwarfare.feature.category.detail.data.datasource.network.ProductApi
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ProductRepositoryImplTest {

    private val productDao: ProductDao = mockk()
    private val productApi: ProductApi = mockk()

    private lateinit var sut: ProductRepository

    @Before
    fun setUp() {
        sut = ProductRepositoryImpl(
            productApi,
            productDao,
        )
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

    @Test
    fun `deleteCategoryProductById should return Right`() = runTest {
        coEvery {
            productDao.deleteProductById(PRODUCT_ID)
        } coAnswers { Unit }

        val actualResult = sut.deleteProductById(PRODUCT_ID)
        val expectedResult = Unit.buildRight()

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}

private const val PRODUCT_ID = "productId"
