package com.marinj.shoppingwarfare.feature.category.list.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure.Unknown
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import com.marinj.shoppingwarfare.fixtures.category.FakeFailureCategoryApi
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessCategoryApi
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessCategoryDao
import com.marinj.shoppingwarfare.fixtures.category.buildCategory
import com.marinj.shoppingwarfare.fixtures.category.buildLocalCategory
import com.marinj.shoppingwarfare.fixtures.category.buildRemoteCategory
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CategoryRepositoryImplTest {

    private lateinit var sut: CategoryRepository

    @Test
    fun `observeCategories SHOULD return categories from local source`() = runTest {
        val remoteCategoryList = listOf(
            buildRemoteCategory(providedCategoryId = CATEGORY_ID),
        )
        val localCategoryList = listOf(
            buildLocalCategory(
                providedCategoryId = CATEGORY_ID,
                providedTitle = TITLE,
            ),
        )
        val expectedResult = listOf(
            buildCategory(
                providedId = CATEGORY_ID,
                providedTitle = TITLE,
            ),
        )
        sut = CategoryRepositoryImpl(
            categoryDao = FakeSuccessCategoryDao(localCategoryList),
            categoryApi = FakeSuccessCategoryApi(remoteCategoryList),
        )

        sut.observeCategories().test {
            assertThat(awaitItem()).isEqualTo(expectedResult)
            awaitComplete()
        }
    }

    @Test
    fun `observeCategories SHOULD return data from local service`() = runTest {
        val remoteCategoryList = listOf(
            buildRemoteCategory(
                providedCategoryId = CATEGORY_ID,
                providedTitle = TITLE,
            ),
        )
        val localCategoryList = listOf(
            buildLocalCategory(
                providedCategoryId = CATEGORY_ID,
                providedTitle = TITLE,
            ),
        )
        val expectedResult = listOf(
            buildCategory(
                providedId = CATEGORY_ID,
                providedTitle = TITLE,
            ),
        )
        val dao = FakeSuccessCategoryDao(localCategoryList)
        sut = CategoryRepositoryImpl(
            categoryDao = dao,
            categoryApi = FakeSuccessCategoryApi(remoteCategoryList),
        )

        sut.observeCategories().test {
            assertThat(awaitItem()).isEqualTo(expectedResult)
            awaitComplete()
        }
    }

    @Test
    fun `upsertCategory SHOULD return Left WHEN categoryApi returns Left`() = runTest {
        val category = buildCategory(providedId = CATEGORY_ID)
        sut = CategoryRepositoryImpl(
            categoryDao = FakeSuccessCategoryDao(),
            categoryApi = FakeFailureCategoryApi(),
        )

        val result = sut.upsertCategory(category)

        assertThat(result).isEqualTo(Unknown.buildLeft())
    }

    @Test
    fun `upsertCategory SHOULD return Right WHEN categoryApi returns Right`() = runTest {
        val category = buildCategory(providedId = CATEGORY_ID)
        sut = CategoryRepositoryImpl(
            categoryDao = FakeSuccessCategoryDao(),
            categoryApi = FakeSuccessCategoryApi(),
        )

        val result = sut.upsertCategory(category)

        assertThat(result).isEqualTo(Unit.buildRight())
    }

    @Test
    fun `deleteCategoryById SHOULD return Right WHEN categoryApi returns Right`() = runTest {
        val dao = FakeSuccessCategoryDao().also {
            it.upsertCategory(buildLocalCategory(providedCategoryId = CATEGORY_ID))
        }
        val api = FakeSuccessCategoryApi().also {
            it.addCategoryItem(buildRemoteCategory(providedCategoryId = CATEGORY_ID))
        }
        sut = CategoryRepositoryImpl(
            categoryDao = dao,
            categoryApi = api,
        )

        val result = sut.deleteCategoryById(CATEGORY_ID)

        assertThat(result).isEqualTo(Unit.buildRight())
        assertThat(dao.localCategories).isEmpty()
        assertThat(api.remoteCategories).isEmpty()
    }
}

private const val CATEGORY_ID = "categoryId"
private const val TITLE = "title"
