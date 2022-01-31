package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteCategoryTest {

    private val categoryRepository: CategoryRepository = mockk()

    private lateinit var sut: DeleteCategory

    @Before
    fun setUp() {
        sut = DeleteCategory((categoryRepository))
    }

    @Test
    fun `invoke should return result categoryRepository deleteCategoryById`() = runTest {
        val id = "id"
        val failure = Failure.Unknown.buildLeft()
        coEvery {
            categoryRepository.deleteCategoryById(id)
        } coAnswers { failure }

        val actualResult = sut(id)

        assertThat(actualResult).isEqualTo(failure)
    }
}
