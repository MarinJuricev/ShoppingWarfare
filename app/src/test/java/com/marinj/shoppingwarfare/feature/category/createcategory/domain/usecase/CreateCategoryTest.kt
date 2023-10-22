package com.marinj.shoppingwarfare.feature.category.createcategory.domain.usecase

import arrow.core.left
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.CreateCategory
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.CreateCategoryImpl
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessCategoryRepository
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CreateCategoryTest {

    private val categoryRepository = FakeSuccessCategoryRepository()
    private val uuidGenerator = { ID }

    private lateinit var sut: CreateCategory

    @Before
    fun setUp() {
        sut = CreateCategoryImpl(
            categoryRepository,
            uuidGenerator,
        )
    }

    @Test
    fun `invoke SHOULD return result from repository WHEN category factory returns not null`() = runTest {
        val title = "title"
        val categoryColor = 1
        val titleColor = 2
        val repositoryResult = Unit.right()

        val actualResult = sut(title, categoryColor, titleColor)

        actualResult shouldBe repositoryResult
    }

    @Test
    fun `invoke SHOULD return Left WHEN category factory returns not null`() = runTest {
        val title = null
        val categoryColor = 1
        val titleColor = 2
        val expectedResult = ErrorMessage("title can not be null or empty").left()

        val actualResult = sut(title, categoryColor, titleColor)

        actualResult shouldBe expectedResult
    }
}

private const val ID = "id"
