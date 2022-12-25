package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessCategoryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteCategoryTest {

    private val categoryRepository: CategoryRepository = FakeSuccessCategoryRepository()

    private lateinit var sut: DeleteCategory

    @Before
    fun setUp() {
        sut = DeleteCategory(categoryRepository)
    }

    @Test
    fun `invoke SHOULD return result repository`() = runTest {
        val actualResult = sut(ID)

        assertThat(actualResult).isEqualTo(Unit.buildRight())
    }
}

private const val ID = "id"