package com.marinj.shoppingwarfare.feature.categorydetail.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.Product
import com.marinj.shoppingwarfare.feature.categorydetail.domain.repository.CategoryDetailRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val UUID = "id"
private const val CATEGORY_ID = "categoryId"
private const val CATEGORY_ITEM_TITLE = "title"

@ExperimentalCoroutinesApi
class CreateProductTest {

    private val validateProduct: ValidateProduct = mockk()
    private val uuidGenerator: () -> String = mockk()
    private val categoryDetailRepository: CategoryDetailRepository = mockk()

    private lateinit var sut: CreateProduct

    @Before
    fun setUp() {
        every { uuidGenerator() } answers { UUID }

        sut = CreateProduct(
            validateProduct,
            uuidGenerator,
            categoryDetailRepository,
        )
    }

    @Test
    fun `invoke should return Left when validateCategoryItem returns Left`() = runBlockingTest {
        val left = Failure.Unknown.buildLeft()
        coEvery {
            validateProduct(CATEGORY_ITEM_TITLE)
        } coAnswers { left }

        val actualResult = sut(CATEGORY_ID, CATEGORY_ITEM_TITLE)

        assertThat(actualResult).isEqualTo(left)
    }

    @Test
    fun `invoke should return Left when validateCategoryItem returns Right and CategoryDetailRepository returns Left`() =
        runBlockingTest {
            val repositoryLeft = Failure.Unknown.buildLeft()
            val validatorRight = Unit.buildRight()
            val categoryItem = Product(UUID, CATEGORY_ID, CATEGORY_ITEM_TITLE)
            coEvery {
                validateProduct(CATEGORY_ITEM_TITLE)
            } coAnswers { validatorRight }
            coEvery {
                categoryDetailRepository.upsertCategoryProduct(categoryItem)
            } coAnswers { repositoryLeft }

            val actualResult = sut(CATEGORY_ID, CATEGORY_ITEM_TITLE)

            assertThat(actualResult).isEqualTo(repositoryLeft)
        }

    @Test
    fun `invoke should return Right when validateCategoryItem returns Right and CategoryDetailRepository returns Right`() =
        runBlockingTest {
            val repositoryRight = Unit.buildRight()
            val validatorRight = Unit.buildRight()
            val categoryItem = Product(UUID, CATEGORY_ID, CATEGORY_ITEM_TITLE)
            coEvery {
                validateProduct(CATEGORY_ITEM_TITLE)
            } coAnswers { validatorRight }
            coEvery {
                categoryDetailRepository.upsertCategoryProduct(categoryItem)
            } coAnswers { repositoryRight }

            val actualResult = sut(CATEGORY_ID, CATEGORY_ITEM_TITLE)

            assertThat(actualResult).isEqualTo(repositoryRight)
        }
}
