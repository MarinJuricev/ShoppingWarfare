package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.Failure.Unknown
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.fixtures.category.FakeFailureProductRepository
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessProductRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CreateProductTest {

    private val uuidGenerator: () -> String = { UUID }

    @Test
    fun `invoke SHOULD return Left WHEN empty categoryId is provided`() = runTest {
        val expectedResult = ErrorMessage("CategoryId can not be empty got: ").left()
        val sut = CreateProduct(
            uuidGenerator,
            FakeSuccessProductRepository(),
        )

        val result = sut(
            categoryId = "",
            categoryName = CATEGORY_NAME,
            productName = PRODUCT_NAME,
        )

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `invoke SHOULD return Right WHEN product is successfully created and repository returns Right`() = runTest {
        val expectedResult = Unit.right()
        val sut = CreateProduct(
            uuidGenerator,
            FakeSuccessProductRepository(),
        )

        val result = sut(
            categoryId = CATEGORY_ID,
            categoryName = CATEGORY_NAME,
            productName = PRODUCT_NAME,
        )

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `invoke SHOULD return Left WHEN product is successfully created and repository returns Left`() = runTest {
        val expectedResult = Unknown.left()
        val sut = CreateProduct(
            uuidGenerator,
            FakeFailureProductRepository,
        )

        val result = sut(
            categoryId = CATEGORY_ID,
            categoryName = CATEGORY_NAME,
            productName = PRODUCT_NAME,
        )

        assertThat(result).isEqualTo(expectedResult)
    }
}

private const val UUID = "id"
private const val CATEGORY_NAME = "categoryName"
private const val CATEGORY_ID = "categoryId"
private const val PRODUCT_NAME = "productName"
