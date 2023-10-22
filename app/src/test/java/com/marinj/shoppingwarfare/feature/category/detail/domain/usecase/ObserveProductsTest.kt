package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import app.cash.turbine.test
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessProductRepository
import com.marinj.shoppingwarfare.fixtures.category.buildProduct
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ObserveProductsTest {

    private val products = listOf(
        buildProduct(
            providedCategoryName = CATEGORY_NAME,
            providedName = PRODUCT_NAME,
        ),
    )
    private val productRepository: ProductRepository = FakeSuccessProductRepository(products)

    private lateinit var sut: ObserveProductsImpl

    @Before
    fun setUp() {
        sut = ObserveProductsImpl(
            productRepository,
        )
    }

    @Test
    fun `invoke SHOULD return result from repository`() = runTest {
        sut(CATEGORY_ID).test {
            awaitItem() shouldBe products
            awaitComplete()
        }
    }
}

private const val CATEGORY_NAME = "categoryName"
private const val PRODUCT_NAME = "name"
private const val CATEGORY_ID = "id"
