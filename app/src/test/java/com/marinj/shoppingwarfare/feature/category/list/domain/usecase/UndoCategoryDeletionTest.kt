package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UndoCategoryDeletionTest {

    private val categoryRepository: CategoryRepository = mockk()
    private lateinit var sut: UndoCategoryDeletion

    @Before
    fun setUp() {
        sut = UndoCategoryDeletion(
            categoryRepository,
        )
    }

    @Test
    fun `invoke should return result categoryRepository upsertCategory`() = runTest {
        val category = mockk<Category>()
        val repositoryResult = Unit.buildRight()
        coEvery {
            categoryRepository.upsertCategory(category)
        } coAnswers { repositoryResult }

        val actualResult = sut(category)

        assertThat(actualResult).isEqualTo(repositoryResult)
    }
}
