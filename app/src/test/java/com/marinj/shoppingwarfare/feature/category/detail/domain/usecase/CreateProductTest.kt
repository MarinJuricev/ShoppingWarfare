package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

private const val UUID = "id"
private const val CATEGORY_ID = "categoryId"
private const val CATEGORY_NAME = "fruits"
private const val CATEGORY_ITEM_TITLE = "title"

class CreateProductTest {

    private val validateProduct: ValidateProduct = mockk()
    private val uuidGenerator: () -> String = mockk()
    private val productRepository: ProductRepository = mockk()

    private lateinit var sut: CreateProduct

    @Before
    fun setUp() {
        every { uuidGenerator() } answers { UUID }

        sut = CreateProduct(
            validateProduct,
            uuidGenerator,
            productRepository,
        )
    }

    @Test
    fun `invoke should return Left when validateCategoryItem returns Left`() = runTest {
        val left = Failure.Unknown.buildLeft()
        coEvery {
            validateProduct(CATEGORY_ITEM_TITLE)
        } coAnswers { left }

        val actualResult = sut(CATEGORY_ID, CATEGORY_NAME, CATEGORY_ITEM_TITLE)

        assertThat(actualResult).isEqualTo(left)
    }

    @Test
    fun `invoke should return Left when validateCategoryItem returns Right and CategoryDetailRepository returns Left`() =
        runTest {
            val repositoryLeft = Failure.Unknown.buildLeft()
            val validatorRight = Unit.buildRight()
            val categoryItem = Product(
                id = UUID,
                categoryId = CATEGORY_ID,
                categoryName = CATEGORY_NAME,
                name = CATEGORY_ITEM_TITLE,
            )
            coEvery {
                validateProduct(CATEGORY_ITEM_TITLE)
            } coAnswers { validatorRight }
            coEvery {
                productRepository.upsertProduct(categoryItem)
            } coAnswers { repositoryLeft }

            val actualResult = sut(
                categoryId = CATEGORY_ID,
                categoryName = CATEGORY_NAME,
                productName = CATEGORY_ITEM_TITLE,
            )

            assertThat(actualResult).isEqualTo(repositoryLeft)
        }

    @Test
    fun `invoke should return Right when validateCategoryItem returns Right and CategoryDetailRepository returns Right`() =
        runTest {
            val repositoryRight = Unit.buildRight()
            val validatorRight = Unit.buildRight()
            val categoryItem = Product(
                id = UUID,
                categoryId = CATEGORY_ID,
                categoryName = CATEGORY_NAME,
                name = CATEGORY_ITEM_TITLE
            )
            coEvery {
                validateProduct(CATEGORY_ITEM_TITLE)
            } coAnswers { validatorRight }
            coEvery {
                productRepository.upsertProduct(categoryItem)
            } coAnswers { repositoryRight }

            val actualResult = sut(
                categoryId = CATEGORY_ID,
                categoryName = CATEGORY_NAME,
                productName = CATEGORY_ITEM_TITLE,
            )

            assertThat(actualResult).isEqualTo(repositoryRight)
        }
}
