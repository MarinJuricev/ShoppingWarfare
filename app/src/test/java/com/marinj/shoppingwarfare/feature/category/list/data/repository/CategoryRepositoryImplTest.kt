package com.marinj.shoppingwarfare.feature.category.list.data.repository

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure.Unknown
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import com.marinj.shoppingwarfare.fixtures.category.FakeFailureCategoryApi
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessCategoryApi
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessCategoryDao
import com.marinj.shoppingwarfare.fixtures.category.buildCategory
import com.marinj.shoppingwarfare.fixtures.category.buildLocalCategory
import com.marinj.shoppingwarfare.fixtures.category.buildRemoteCategory
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
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
                providedCategoryId = CATEGORY_ID,
                providedTitle = TITLE,
            ),
        )
        sut = CategoryRepositoryImpl(
            categoryDao = FakeSuccessCategoryDao(localCategoryList),
            categoryApi = FakeSuccessCategoryApi(remoteCategoryList),
        )

        sut.observeCategories().test {
            val item = awaitItem()

            item shouldBe expectedResult
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
                providedCategoryId = CATEGORY_ID,
                providedTitle = TITLE,
            ),
        )
        val dao = FakeSuccessCategoryDao(localCategoryList)
        sut = CategoryRepositoryImpl(
            categoryDao = dao,
            categoryApi = FakeSuccessCategoryApi(remoteCategoryList),
        )

        sut.observeCategories().test {
            awaitItem() shouldBe expectedResult
            awaitComplete()
        }
    }

    @Test
    fun `upsertCategory SHOULD return Left WHEN categoryApi returns Left`() = runTest {
        val category = buildCategory(providedCategoryId = CATEGORY_ID)
        sut = CategoryRepositoryImpl(
            categoryDao = FakeSuccessCategoryDao(),
            categoryApi = FakeFailureCategoryApi,
        )

        val result = sut.upsertCategory(category)

        result shouldBe Unknown.left()
    }

    @Test
    fun `upsertCategory SHOULD return Right WHEN categoryApi returns Right`() = runTest {
        val category = buildCategory(providedCategoryId = CATEGORY_ID)
        sut = CategoryRepositoryImpl(
            categoryDao = FakeSuccessCategoryDao(),
            categoryApi = FakeSuccessCategoryApi(),
        )

        val result = sut.upsertCategory(category)

        result shouldBe Unit.right()
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

        result shouldBe Unit.right()
        dao.localCategories.shouldBeEmpty()
        api.remoteCategories.shouldBeEmpty()
    }
}

private const val CATEGORY_ID = "categoryId"
private const val TITLE = "title"
