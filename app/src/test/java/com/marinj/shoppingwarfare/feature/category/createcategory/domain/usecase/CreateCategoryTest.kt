package com.marinj.shoppingwarfare.feature.category.createcategory.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.createcategory.domain.repository.CreateCategoryRepository
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

private const val ID = "id"

class CreateCategoryTest {

    private val validateCategory: ValidateCategory = mockk()
    private val createCategoryRepository: CreateCategoryRepository = mockk()
    private val uuidGenerator = { ID }

    private lateinit var sut: CreateCategory

    @Before
    fun setUp() {
        sut = CreateCategory(
            validateCategory,
            createCategoryRepository,
            uuidGenerator,
        )
    }

    @Test
    fun `invoke should return result from validator when validator returns Left`() =
        runTest {
            val title = "title"
            val categoryColor = 1
            val titleColor = 2
            val failure = Failure.Unknown.buildLeft()
            coEvery {
                validateCategory(title, categoryColor, titleColor)
            } coAnswers { failure }

            val actualResult = sut(title, categoryColor, titleColor)

            assertThat(actualResult).isEqualTo(failure)
        }

    @Test
    fun `invoke should return result from repository when validator returns Right`() =
        runTest {
            val title = "title"
            val categoryColor = 1
            val titleColor = 2
            val success = Unit.buildRight()
            coEvery {
                validateCategory(title, categoryColor, titleColor)
            } coAnswers { success }
            coEvery {
                createCategoryRepository.createCategory(
                    Category(
                        ID,
                        title,
                        categoryColor,
                        titleColor,
                    ),
                )
            } coAnswers { success }

            val actualResult = sut(title, categoryColor, titleColor)

            assertThat(actualResult).isEqualTo(success)
        }
}
