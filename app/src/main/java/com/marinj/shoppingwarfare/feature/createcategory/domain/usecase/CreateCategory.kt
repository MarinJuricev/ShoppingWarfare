package com.marinj.shoppingwarfare.feature.createcategory.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import com.marinj.shoppingwarfare.feature.createcategory.domain.repository.CreateCategoryRepository
import com.marinj.shoppingwarfare.feature.createcategory.domain.validator.CategoryValidator
import javax.inject.Inject

class CreateCategory @Inject constructor(
    private val categoryValidator: CategoryValidator,
    private val createCategoryRepository: CreateCategoryRepository,
) {

    suspend operator fun invoke(
        title: String?,
        categoryColor: Int?,
        titleColor: Int?,
    ): Either<Failure, Unit> {
        return when (val result = categoryValidator.validate(title, categoryColor, titleColor)) {
            is Either.Left -> result
            is Either.Right -> createCategoryRepository.createCategory(
                Category(
                    title!!,
                    categoryColor!!,
                    titleColor!!,
                )
            )
        }
    }
}
