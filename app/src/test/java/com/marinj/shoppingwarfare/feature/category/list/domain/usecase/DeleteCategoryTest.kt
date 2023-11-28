package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import arrow.core.right
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessCategoryRepository
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteCategoryTest {

    private val categoryRepository: CategoryRepository = FakeSuccessCategoryRepository()

    private lateinit var sut: DeleteCategory

    @BeforeEach
    fun setUp() {
        sut = DeleteCategoryImpl(categoryRepository)
    }

    @Test
    fun `invoke SHOULD return result repository`() = runTest {
        val actualResult = sut(ID)

        actualResult shouldBe Unit.right()
    }
}

private const val ID = "id"
