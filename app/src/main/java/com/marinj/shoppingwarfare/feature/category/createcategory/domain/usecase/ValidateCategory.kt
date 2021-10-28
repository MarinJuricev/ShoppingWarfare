package com.marinj.shoppingwarfare.feature.category.createcategory.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import javax.inject.Inject

class ValidateCategory @Inject constructor() {

    operator fun invoke(
        title: String?,
        categoryColor: Int?,
        titleColor: Int?,
    ): Either<Failure, Unit> {
        return when {
            title.isNullOrEmpty() -> ErrorMessage("Title can't be empty").buildLeft()
            categoryColor == null -> ErrorMessage("Category color can't be empty").buildLeft()
            titleColor == null -> ErrorMessage("Title color can't be empty").buildLeft()
            else -> Unit.buildRight()
        }
    }
}
