package com.marinj.shoppingwarfare.feature.category.createcategory.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.CreateCategory
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.CreateCategoryImpl
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessCategoryRepository
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

        assertThat(actualResult).isEqualTo(repositoryResult)
    }

    @Test
    fun `invoke SHOULD return Left WHEN category factory returns not null`() = runTest {
        val title = null
        val categoryColor = 1
        val titleColor = 2
        val expectedResult = ErrorMessage("Title can not be empty or null got: null").left()

        val actualResult = sut(title, categoryColor, titleColor)

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}

private const val ID = "id"
