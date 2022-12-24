package com.marinj.shoppingwarfare.feature.category.createcategory.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.Unknown
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import javax.inject.Inject

class CreateCategory @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val uuidGenerator: () -> String,
) {

    suspend operator fun invoke(
        title: String?,
        categoryColor: Int?,
        titleColor: Int?,
    ): Either<Failure, Unit> = Category.of(
        id = uuidGenerator(),
        title = title,
        categoryColor = categoryColor,
        titleColor = titleColor,
    )?.let {
        categoryRepository.upsertCategory(it)
    } ?: Unknown.buildLeft()
}
