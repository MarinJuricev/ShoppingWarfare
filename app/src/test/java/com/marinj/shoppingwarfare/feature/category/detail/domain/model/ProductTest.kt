package com.marinj.shoppingwarfare.feature.category.detail.domain.model

import arrow.core.left
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product.Companion.Product
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.junit.Test

internal class ProductTest {

    @Test
    fun `Product SHOULD return Left WHEN title is null`() {
        val expectedResult = ErrorMessage("id can not be null or empty").left()

        val result = Product(
            id = "",
            categoryId = "",
            categoryName = "",
            name = "",
        )

        result shouldBe expectedResult
    }

    @Test
    fun `Product SHOULD return Left WHEN categoryName is empty`() {
        val expectedResult = ErrorMessage("categoryName can not be null or empty").left()

        val result = Product(
            id = ID,
            categoryId = CATEGORY_ID,
            categoryName = "",
            name = NAME,
        )

        result shouldBe expectedResult
    }

    @Test
    fun `Product SHOULD return Left WHEN categoryId is empty`() {
        val expectedResult = ErrorMessage("categoryId can not be null or empty").left()

        val result = Product(
            id = ID,
            categoryId = "",
            categoryName = CATEGORY_NAME,
            name = NAME,
        )

        result shouldBe expectedResult
    }

    @Test
    fun `Product SHOULD return Left WHEN name is null`() {
        val expectedResult = ErrorMessage("name can not be null or empty").left()

        val result = Product(
            id = ID,
            categoryId = CATEGORY_ID,
            categoryName = CATEGORY_NAME,
            name = null,
        )

        result shouldBe expectedResult
    }

    @Test
    fun `Product SHOULD return Right WHEN validation passes`() {
        val result = Product(
            id = ID,
            categoryId = CATEGORY_ID,
            categoryName = CATEGORY_NAME,
            name = NAME,
        )

        result.isRight().shouldBeTrue()
    }
}

private const val NAME = "name"
private const val ID = "id"
private const val CATEGORY_ID = "id"
private const val CATEGORY_NAME = "categoryName"
