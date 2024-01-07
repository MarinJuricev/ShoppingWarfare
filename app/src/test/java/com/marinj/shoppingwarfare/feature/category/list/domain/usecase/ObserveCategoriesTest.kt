package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import app.cash.turbine.test
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessCategoryRepository
import com.marinj.shoppingwarfare.fixtures.category.buildCategory
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ObserveCategoriesTest {

    private val repositoryCategories = listOf(
        buildCategory(providedCategoryId = CATEGORY_ID, providedTitle = TITLE),
    )
    private val categoryRepository = FakeSuccessCategoryRepository(
        repositoryCategories,
    )

    private lateinit var sut: ObserveCategories

    @BeforeEach
    fun setUp() {
        sut = ObserveCategoriesImpl(
            categoryRepository,
        )
    }

    @Test
    fun `invoke should return result from repository`() = runTest {
        sut().test {
            awaitItem() shouldBe repositoryCategories
            awaitComplete()
        }
    }
}

private const val CATEGORY_ID = "categoryId"
private const val TITLE = "title"
