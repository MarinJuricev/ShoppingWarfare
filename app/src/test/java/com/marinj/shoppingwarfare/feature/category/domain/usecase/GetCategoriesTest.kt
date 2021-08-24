package com.marinj.shoppingwarfare.feature.category.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.*
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.domain.repository.CategoryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class GetCategoriesTest {

    private val categoryRepository: CategoryRepository = mockk()

    private lateinit var sut: GetCategories

    @Before
    fun setUp() {
        sut = GetCategories(
            categoryRepository,
        )
    }

    @Test
    fun `invoke should return result from categoryRepository observeCategories`() = runBlockingTest {
        val categories = listOf(mockk<Category>())
        val repositoryFlow = flow { emit(categories) }
        coEvery {
            categoryRepository.observeCategories()
        } coAnswers { repositoryFlow }

        sut().test {
            assertThat(awaitItem()).isEqualTo(categories)
            awaitComplete()
        }
    }
}
