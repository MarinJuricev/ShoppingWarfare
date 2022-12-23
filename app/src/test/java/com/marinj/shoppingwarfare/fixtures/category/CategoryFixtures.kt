package com.marinj.shoppingwarfare.fixtures.category

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.list.data.datasource.local.CategoryDao
import com.marinj.shoppingwarfare.feature.category.list.data.datasource.network.CategoryApi
import com.marinj.shoppingwarfare.feature.category.list.data.model.LocalCategory
import com.marinj.shoppingwarfare.feature.category.list.data.model.RemoteCategory
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun buildRemoteCategory(
    providedCategoryId: String = "",
    providedTitle: String = "",
    providedBackgroundColor: Int = 0,
    providedTitleColor: Int = 0,
) = RemoteCategory(
    categoryId = providedCategoryId,
    title = providedTitle,
    backgroundColor = providedBackgroundColor,
    titleColor = providedTitleColor,
)

fun buildLocalCategory(
    providedCategoryId: String = "",
    providedTitle: String = "",
    providedBackgroundColor: Int = 0,
    providedTitleColor: Int = 0,
) = LocalCategory(
    categoryId = providedCategoryId,
    title = providedTitle,
    backgroundColor = providedBackgroundColor,
    titleColor = providedTitleColor,
)

fun buildCategory(
    providedId: String = "",
    providedTitle: String = "",
    providedBackgroundColor: Int = 0,
    providedTitleColor: Int = 0,
) = Category(
    id = providedId,
    title = providedTitle,
    backgroundColor = providedBackgroundColor,
    titleColor = providedTitleColor,
)

class FakeSuccessCategoryDao : CategoryDao {
    override fun observeCategories(): Flow<List<LocalCategory>> = flow {
        emit(listOf(buildLocalCategory()))
    }

    override suspend fun upsertCategory(
        entity: LocalCategory,
    ): Long = 1L

    override suspend fun deleteCategoryById(id: String) = Unit
}

class FakeFailureCategoryDao : CategoryDao {
    override fun observeCategories(): Flow<List<LocalCategory>> = flow {
        throw Throwable()
    }

    override suspend fun upsertCategory(
        entity: LocalCategory,
    ): Long = 0L

    override suspend fun deleteCategoryById(id: String) = Unit
}

class FakeSuccessCategoryApi : CategoryApi {
    override fun observeCategoryItems(): Flow<List<RemoteCategory>> =
        flow {
            emit(listOf(buildRemoteCategory()))
        }

    override suspend fun addCategoryItem(categoryItem: RemoteCategory): Either<Failure, Unit> =
        Unit.buildRight()

    override suspend fun deleteCategoryItemById(categoryId: String): Either<Failure, Unit> =
        Unit.buildRight()

}

class FakeFailureCategoryApi : CategoryApi {
    override fun observeCategoryItems(): Flow<List<RemoteCategory>> =
        flow {
            throw Throwable()
        }

    override suspend fun addCategoryItem(categoryItem: RemoteCategory) =
        Failure.Unknown.buildLeft()

    override suspend fun deleteCategoryItemById(categoryId: String) =
        Failure.Unknown.buildLeft()

}