package com.marinj.shoppingwarfare.feature.category.createcategory.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.map
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import javax.inject.Inject

class CreateCategory @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val uuidGenerator: () -> String,
) {

    suspend operator fun invoke(
        title: String?,
        backgroundColor: Int?,
        titleColor: Int?,
    ): Either<Failure, Unit> = Category.of(
        id = uuidGenerator(),
        title = title,
        backgroundColor = backgroundColor,
        titleColor = titleColor,
    ).map { category ->
        categoryRepository.upsertCategory(category)
    }
}
