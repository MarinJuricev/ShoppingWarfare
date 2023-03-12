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
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.DeleteCategory
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.ObserveCategories
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.UndoCategoryDeletion
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.UiCategory
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
    private val categoryListToEmit: List<RemoteCategory> = listOf(buildRemoteCategory()),
) : CategoryApi {

    val remoteCategories = mutableListOf<RemoteCategory>()

    override fun observeCategories() = flow {
        emit(categoryListToEmit)
    }

    override suspend fun addCategoryItem(categoryItem: RemoteCategory): Either<Failure, Unit> {
        remoteCategories.add(categoryItem)
        return Unit.right()
    }

    override suspend fun deleteCategoryItemById(categoryId: String): Either<Failure, Unit> {
        remoteCategories.removeIf { it.categoryId == categoryId }
        return Unit.right()
    }
}

object FakeFailureCategoryApi : CategoryApi {
    override fun observeCategories() = flow<List<RemoteCategory>> {
        throw Throwable()
    }

    override suspend fun addCategoryItem(categoryItem: RemoteCategory) =
        Unknown.left()

    override suspend fun deleteCategoryItemById(categoryId: String) =
        Unknown.left()
}

class FakeSuccessCategoryRepository(
    private val categoryListToObserve: List<Category> = listOf(buildCategory()),
) : CategoryRepository {
    override fun observeCategories() = flow {
        emit(categoryListToObserve)
    }

    override suspend fun upsertCategory(category: Category) =
        Unit.right()

    override suspend fun deleteCategoryById(id: String): Either<Failure, Unit> =
        Unit.right()
}

class FakeSuccessObserveCategories(
    private val categoryListToReturn: List<Category> = listOf(buildCategory()),
) : ObserveCategories {
    override fun invoke(): Flow<List<Category>> = flow {
        emit(categoryListToReturn)
    }
}

object FakeFailureObserveCategories : ObserveCategories {
    override fun invoke(): Flow<List<Category>> = flow {
        throw Throwable()
    }
}

object FakeSuccessDeleteCategory : DeleteCategory {
    override suspend fun invoke(
        categoryId: String,
    ): Either<Failure, Unit> = Unit.right()
}

object FakeFailureDeleteCategory : DeleteCategory {
    override suspend fun invoke(
        categoryId: String,
    ): Either<Failure, Unit> = Unknown.left()
}

object FakeSuccessUndoCategoryDeletion : UndoCategoryDeletion {
    override suspend fun invoke(
        category: Category,
    ): Either<Failure, Unit> = Unit.right()
}

object FakeFailureUndoCategoryDeletion : UndoCategoryDeletion {
    override suspend fun invoke(
        category: Category,
    ): Either<Failure, Unit> = Unknown.left()
}

private const val TITLE = "title"
