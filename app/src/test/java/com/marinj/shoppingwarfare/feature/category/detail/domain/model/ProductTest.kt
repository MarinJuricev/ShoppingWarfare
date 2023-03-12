package com.marinj.shoppingwarfare.feature.category.detail.domain.model

import arrow.core.left
import arrow.core.right
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import org.junit.Test

internal class ProductTest {

    @Test
    fun `of SHOULD return Left WHEN title is null`() {
        val expectedResult = ErrorMessage("Name can not be empty got: ").left()

        val result = Product.of(
            id = "",
            categoryId = "",
            categoryName = "",
            name = "",
        )

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `of SHOULD return Left WHEN categoryName is empty`() {
        val expectedResult = ErrorMessage("CategoryName can not be empty got: ").left()

        val result = Product.of(
            id = "",
            categoryId = "",
            categoryName = "",
            name = NAME,
        )

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `of SHOULD return Left WHEN categoryId is empty`() {
        val expectedResult = ErrorMessage("CategoryId can not be empty got: ").left()

        val result = Product.of(
            id = "",
            categoryId = "",
            categoryName = CATEGORY_NAME,
            name = NAME,
        )

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `of SHOULD return Left WHEN name is null`() {
        val expectedResult = ErrorMessage("Name can not be empty got: null").left()

        val result = Product.of(
            id = ID,
            categoryId = CATEGORY_ID,
            categoryName = CATEGORY_NAME,
            name = null,
        )

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `of SHOULD return Right WHEN validation passes`() {
        val expectedResult = Product(
            id = ID,
            categoryId = CATEGORY_ID,
            categoryName = CATEGORY_NAME,
            name = NAME,
        ).right()

        val result = Product.of(
            id = ID,
            categoryId = CATEGORY_ID,
            categoryName = CATEGORY_NAME,
            name = NAME,
        )

        assertThat(result).isEqualTo(expectedResult)
    }
}

private const val NAME = "name"

private const val ID = "id"
private const val CATEGORY_ID = "id"
private const val CATEGORY_NAME = "categoryName"
