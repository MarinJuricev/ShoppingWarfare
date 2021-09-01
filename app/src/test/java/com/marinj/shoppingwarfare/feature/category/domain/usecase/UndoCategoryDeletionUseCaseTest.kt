package com.marinj.shoppingwarfare.feature.category.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.domain.repository.CategoryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UndoCategoryDeletionUseCaseTest {

    private val categoryRepository: CategoryRepository = mockk()
    private lateinit var sut: UndoCategoryDeletionUseCase

    @Before
    fun setUp() {
        sut = UndoCategoryDeletionUseCase(
            categoryRepository,
        )
    }

    @Test
    fun `invoke should return result categoryRepository upsertCategory`() = runBlockingTest {
        val category = mockk<Category>()
        val repositoryResult = Unit.buildRight()
        coEvery {
            categoryRepository.upsertCategory(category)
        } coAnswers { repositoryResult }

        val actualResult = sut(category)

        assertThat(actualResult).isEqualTo(repositoryResult)
    }
}
