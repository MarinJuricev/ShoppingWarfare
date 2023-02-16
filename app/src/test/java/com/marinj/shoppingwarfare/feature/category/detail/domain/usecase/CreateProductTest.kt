package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import com.google.common.truth.Truth
import com.google.common.truth.Truth.*
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessProductRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CreateProductTest {

    private val uuidGenerator: () -> String = { UUID }
    private val productRepository: ProductRepository = FakeSuccessProductRepository()

    private lateinit var sut: CreateProduct

    @Before
    fun setUp() {
        sut = CreateProduct(
            uuidGenerator,
            productRepository,
        )
    }

    @Test
    fun `invoke SHOULD return Left WHEN empty categoryId is provided`() = runTest {
        val expectedResult  = Failure.ErrorMessage("categoryId can not be empty got: ").buildLeft()

        val result = sut(
            categoryId = "",
            categoryName = CATEGORY_NAME,
            productName = PRODUCT_NAME,
        )

        assertThat(result).isEqualTo(expectedResult)
    }
}

private const val UUID = "id"
private const val CATEGORY_ID = "categoryId"
private const val CATEGORY_NAME = "categoryName"
private const val PRODUCT_NAME = "productName"
