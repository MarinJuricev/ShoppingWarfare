package com.marinj.shoppingwarfare.feature.categorydetail.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import javax.inject.Inject

class ValidateProduct @Inject constructor() {

    operator fun invoke(
        title: String?
    ): Either<Failure, Unit> {
        return when {
            title.isNullOrEmpty() -> ErrorMessage("Title can't be empty").buildLeft()
            else -> Unit.buildRight()
        }
    }
}
