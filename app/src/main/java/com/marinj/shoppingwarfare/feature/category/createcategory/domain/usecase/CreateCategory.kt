package com.marinj.shoppingwarfare.feature.category.createcategory.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.createcategory.domain.repository.CreateCategoryRepository
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import javax.inject.Inject

class CreateCategory @Inject constructor(
    private val validateCategory: ValidateCategory,
    private val createCategoryRepository: CreateCategoryRepository,
    private val uuidGenerator: () -> String,
) {

    suspend operator fun invoke(
        title: String?,
        categoryColor: Int?,
        titleColor: Int?,
    ): Either<Failure, Unit> {
        return when (val result = validateCategory(title, categoryColor, titleColor)) {
            is Either.Left -> result
            is Either.Right -> createCategoryRepository.createCategory(
                Category(
                    uuidGenerator(),
                    title!!,
                    categoryColor!!,
                    titleColor!!,
                )
            )
        }
    }
}
