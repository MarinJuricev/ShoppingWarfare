package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessCategoryRepository
import com.marinj.shoppingwarfare.fixtures.category.buildCategory
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UndoCategoryDeletionTest {

    private val categoryRepository: CategoryRepository = FakeSuccessCategoryRepository()
    private lateinit var sut: UndoCategoryDeletion

    @Before
    fun setUp() {
        sut = UndoCategoryDeletion(
            categoryRepository,
        )
    }

    @Test
    fun `invoke SHOULD return result from repository`() = runTest {
        val category = buildCategory()

        val actualResult = sut(category)

        assertThat(actualResult).isEqualTo(Unit.buildRight())
    }
}
