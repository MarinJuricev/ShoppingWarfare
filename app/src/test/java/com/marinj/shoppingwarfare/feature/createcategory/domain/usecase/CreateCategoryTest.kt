package com.marinj.shoppingwarfare.feature.createcategory.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import com.marinj.shoppingwarfare.feature.createcategory.domain.repository.CreateCategoryRepository
import com.marinj.shoppingwarfare.feature.createcategory.domain.validator.CategoryValidator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val ID = "id"

@ExperimentalCoroutinesApi
class CreateCategoryTest {

    private val categoryValidator: CategoryValidator = mockk()
    private val createCategoryRepository: CreateCategoryRepository = mockk()
    private val uuidGenerator = { ID }

    private lateinit var sut: CreateCategory

    @Before
    fun setUp() {
        sut = CreateCategory(
            categoryValidator,
            createCategoryRepository,
            uuidGenerator,
        )
    }

    @Test
    fun `invoke should return result from validator when validator returns Left`() =
        runBlockingTest {
            val title = "title"
            val categoryColor = 1
            val titleColor = 2
            val failure = Failure.Unknown.buildLeft()
            coEvery {
                categoryValidator.validate(title, categoryColor, titleColor)
            } coAnswers { failure }

            val actualResult = sut(title, categoryColor, titleColor)

            assertThat(actualResult).isEqualTo(failure)
        }

    @Test
    fun `invoke should return result from repository when validator returns Right`() =
        runBlockingTest {
            val title = "title"
            val categoryColor = 1
            val titleColor = 2
            val success = Unit.buildRight()
            coEvery {
                categoryValidator.validate(title, categoryColor, titleColor)
            } coAnswers { success }
            coEvery {
                createCategoryRepository.createCategory(Category(ID, title, categoryColor, titleColor))
            } coAnswers { success }

            val actualResult = sut(title, categoryColor, titleColor)

            assertThat(actualResult).isEqualTo(success)
        }
}
