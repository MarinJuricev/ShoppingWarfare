package com.marinj.shoppingwarfare.feature.categorydetail.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.categorydetail.data.datasource.ProductDao
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalCategoryProducts
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalProduct
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.Product
import com.marinj.shoppingwarfare.feature.categorydetail.domain.repository.CategoryDetailRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

private const val CATEGORY_ID = "categoryId"
private const val PRODUCT_ID = "productId"

@ExperimentalTime
@ExperimentalCoroutinesApi
class CategoryDetailRepositoryImplTest {

    private val productDao: ProductDao = mockk()
    private val domainToLocalProductMapper: Mapper<LocalProduct, Product> = mockk()
    private val localCategoryProductsListToDomainProductMapper: Mapper<List<Product>, List<LocalCategoryProducts>> =
        mockk()

    private lateinit var sut: CategoryDetailRepository

    @Before
    fun setUp() {
        sut = CategoryDetailRepositoryImpl(
            productDao,
            domainToLocalProductMapper,
            localCategoryProductsListToDomainProductMapper,
        )
    }

    @Test
    fun `observeCategoryProducts should return products`() = runBlockingTest {
        val localCategoryProducts = mockk<LocalCategoryProducts>()
        val listOfLocalCategoryProducts = listOf(localCategoryProducts)
        val product = mockk<Product>()
        val listOfProducts = listOf(product)
        coEvery {
            productDao.observeProductsForGivenCategoryId(CATEGORY_ID)
        } coAnswers {
            flow {
                emit(listOfLocalCategoryProducts)
            }
        }
        coEvery {
            localCategoryProductsListToDomainProductMapper.map(listOfLocalCategoryProducts)
        } coAnswers { listOfProducts }

        sut.observeCategoryProducts(CATEGORY_ID).test {
            assertThat(awaitItem()).isEqualTo(listOfProducts)
            awaitComplete()
        }
    }

    @Test
    fun `upsertCategoryProduct should return Left when productDao returns 0L`() = runBlockingTest {
        val product = mockk<Product>()
        val localProduct = mockk<LocalProduct>()
        coEvery {
            domainToLocalProductMapper.map(product)
        } coAnswers { localProduct }
        coEvery {
            productDao.upsertProduct(localProduct)
        } coAnswers { 0L }

        val actualResult = sut.upsertCategoryProduct(product)
        val expectedResult = ErrorMessage("Error while adding new category product").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `upsertCategoryProduct should return Right when productDao returns result other than 0L`() =
        runBlockingTest {
            val product = mockk<Product>()
            val localProduct = mockk<LocalProduct>()
            coEvery {
                domainToLocalProductMapper.map(product)
            } coAnswers { localProduct }
            coEvery {
                productDao.upsertProduct(localProduct)
            } coAnswers { 1L }

            val actualResult = sut.upsertCategoryProduct(product)
            val expectedResult = Unit.buildRight()

            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun `deleteCategoryProductById should return Right`() = runBlockingTest {
        coEvery {
            productDao.deleteProductById(PRODUCT_ID)
        } coAnswers { Unit }

        val actualResult = sut.deleteCategoryProductById(PRODUCT_ID)
        val expectedResult = Unit.buildRight()

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}
