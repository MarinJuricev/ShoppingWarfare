package com.marinj.shoppingwarfare.fixtures.category

import androidx.compose.ui.graphics.Color
import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.Unknown
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.core.result.takeRightOrNull
import com.marinj.shoppingwarfare.feature.category.list.data.datasource.local.CategoryDao
import com.marinj.shoppingwarfare.feature.category.list.data.datasource.network.CategoryApi
import com.marinj.shoppingwarfare.feature.category.list.data.model.LocalCategory
import com.marinj.shoppingwarfare.feature.category.list.data.model.RemoteCategory
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.UiCategory
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
    providedTitle: String = TITLE,
    providedBackgroundColor: Int = 0,
    providedTitleColor: Int = 0,
) = Category.of(
    id = providedId,
    title = providedTitle,
    backgroundColor = providedBackgroundColor,
    titleColor = providedTitleColor,
).takeRightOrNull()!!

fun buildUiCategory(
    providedCategoryId: String = "",
    providedTitle: String = "",
    providedBackgroundColor: Int = 0,
    providedTitleColor: Int = 0,
) = UiCategory(
    id = providedCategoryId,
    title = providedTitle,
    backgroundColor = Color(providedBackgroundColor),
    titleColor = Color(providedTitleColor),
)

class FakeSuccessCategoryDao(
    private val categoryListToReturn: List<LocalCategory> = listOf(buildLocalCategory()),
) : CategoryDao {

    val localCategories = mutableListOf<LocalCategory>()

    override fun observeCategories() = flow {
        emit(categoryListToReturn)
    }

    override suspend fun upsertCategory(
        entity: LocalCategory,
    ): Long {
        localCategories.add(entity)
        return 1L
    }

    override suspend fun deleteCategoryById(id: String) {
        localCategories.removeIf { it.categoryId == id }
    }
}

class FakeSuccessCategoryApi(
    private val categoryListToReturn: List<RemoteCategory> = listOf(buildRemoteCategory()),
) : CategoryApi {

    val remoteCategories = mutableListOf<RemoteCategory>()

    override fun observeCategories() = flow {
        emit(categoryListToReturn)
    }

    override suspend fun addCategoryItem(categoryItem: RemoteCategory): Either<Failure, Unit> {
        remoteCategories.add(categoryItem)
        return Unit.buildRight()
    }

    override suspend fun deleteCategoryItemById(categoryId: String): Either<Failure, Unit> {
        remoteCategories.removeIf { it.categoryId == categoryId }
        return Unit.buildRight()
    }
}

class FakeFailureCategoryApi : CategoryApi {
    override fun observeCategories() = flow<List<RemoteCategory>> {
        throw Throwable()
    }

    override suspend fun addCategoryItem(categoryItem: RemoteCategory) =
        Unknown.buildLeft()

    override suspend fun deleteCategoryItemById(categoryId: String) =
        Unknown.buildLeft()
}

class FakeSuccessCategoryRepository(
    private val categoryListToReturn: List<Category> = listOf(buildCategory()),
) : CategoryRepository {
    override fun observeCategories() = flow {
        emit(categoryListToReturn)
    }

    override suspend fun upsertCategory(category: Category) =
        Unit.buildRight()

    override suspend fun deleteCategoryById(id: String): Either<Failure, Unit> =
        Unit.buildRight()
}

private const val TITLE = "title"
