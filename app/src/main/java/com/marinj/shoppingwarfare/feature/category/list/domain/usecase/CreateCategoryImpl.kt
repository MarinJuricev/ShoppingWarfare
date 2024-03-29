package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category.Companion.Category
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import javax.inject.Inject

class CreateCategoryImpl @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val uuidGenerator: () -> String,
) : CreateCategory {

    override suspend fun invoke(
        title: String?,
        backgroundColor: Int?,
        titleColor: Int?,
    ): Either<Failure, Unit> = Category(
        id = uuidGenerator(),
        title = title,
        backgroundColor = backgroundColor,
        titleColor = titleColor,
    ).map { category ->
        categoryRepository.upsertCategory(category)
    }
}
