package com.marinj.shoppingwarfare.feature.createcategory.domain.validator

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import javax.inject.Inject
import kotlin.contracts.ExperimentalContracts

class CategoryValidator @Inject constructor() {

    fun validate(
        title: String?,
        categoryColor: Int?,
    ): Either<Failure, Unit> {
        return when {
            title.isNullOrEmpty() -> ErrorMessage("Title can't be empty").buildLeft()
            categoryColor == null -> ErrorMessage("Category color can't be empty").buildLeft()
            else -> Unit.buildRight()
        }
    }
}