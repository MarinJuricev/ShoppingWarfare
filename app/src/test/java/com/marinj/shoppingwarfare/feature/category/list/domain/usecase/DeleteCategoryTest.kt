package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import arrow.core.right
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessCategoryRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteCategoryTest {

    private val categoryRepository: CategoryRepository = FakeSuccessCategoryRepository()

    private lateinit var sut: DeleteCategory

    @Before
    fun setUp() {
        sut = DeleteCategoryImpl(categoryRepository)
    }

    @Test
    fun `invoke SHOULD return result repository`() = runTest {
        val actualResult = sut(ID)

        assertThat(actualResult).isEqualTo(Unit.right())
    }
}

private const val ID = "id"
