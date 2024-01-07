package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import arrow.core.right
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessCategoryRepository
import com.marinj.shoppingwarfare.fixtures.category.buildCategory
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UndoCategoryDeletionTest {

    private val categoryRepository: CategoryRepository = FakeSuccessCategoryRepository()
    private lateinit var sut: UndoCategoryDeletion

    @BeforeEach
    fun setUp() {
        sut = UndoCategoryDeletionImpl(
            categoryRepository,
        )
    }

    @Test
    fun `invoke SHOULD return result from repository`() = runTest {
        val category = buildCategory()

        val actualResult = sut(category)

        actualResult shouldBe Unit.right()
    }
}
