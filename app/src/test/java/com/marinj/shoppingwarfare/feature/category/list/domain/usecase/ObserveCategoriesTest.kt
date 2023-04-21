package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessCategoryRepository
import com.marinj.shoppingwarfare.fixtures.category.buildCategory
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ObserveCategoriesTest {

    private val repositoryCategories = listOf(
        buildCategory(providedCategoryId = CATEGORY_ID, providedTitle = TITLE),
    )
    private val categoryRepository = FakeSuccessCategoryRepository(
        repositoryCategories,
    )

    private lateinit var sut: ObserveCategories

    @Before
    fun setUp() {
        sut = ObserveCategoriesImpl(
            categoryRepository,
        )
    }

    @Test
    fun `invoke should return result from repository`() = runTest {
        sut().test {
            assertThat(awaitItem()).isEqualTo(repositoryCategories)
            awaitComplete()
        }
    }
}

private const val CATEGORY_ID = "categoryId"
private const val TITLE = "title"
